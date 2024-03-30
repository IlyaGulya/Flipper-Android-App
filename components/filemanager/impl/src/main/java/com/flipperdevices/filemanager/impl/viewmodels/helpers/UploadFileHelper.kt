package com.flipperdevices.filemanager.impl.viewmodels.helpers

import android.content.ContentResolver
import android.content.Intent
import android.net.Uri
import com.flipperdevices.bridge.api.manager.FlipperRequestApi
import com.flipperdevices.bridge.api.model.FlipperRequest
import com.flipperdevices.bridge.api.model.FlipperRequestPriority
import com.flipperdevices.bridge.api.model.wrapToRequest
import com.flipperdevices.bridge.protobuf.streamToCommandFlow
import com.flipperdevices.core.log.LogTagProvider
import com.flipperdevices.core.log.info
import com.flipperdevices.deeplink.model.DeeplinkContent
import com.flipperdevices.deeplink.model.DeeplinkContent.InternalStorageFile
import com.flipperdevices.protobuf.main
import com.flipperdevices.protobuf.storage.file
import com.flipperdevices.protobuf.storage.writeRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import java.io.InputStream

class UploadFileHelper(private val contentResolver: ContentResolver) : LogTagProvider {
    override val TAG = "UploadFileHelper"

    /**
     * @param onUpdateIncrement if null process completed
     */
    suspend fun uploadFile(
        requestApi: FlipperRequestApi,
        deeplinkContent: DeeplinkContent,
        filePath: String,
        onUpdateIncrement: (Long?) -> Unit
    ) = withContext(Dispatchers.Default) {
        deeplinkContent.openStream(contentResolver).use { fileStream ->
            val stream = fileStream ?: return@use
            val requestFlow = streamToCommandFlow(
                stream,
                deeplinkContent.length()
            ) { chunkData ->
                storageWriteRequest = writeRequest {
                    path = filePath
                    file = file { data = chunkData }
                }
            }.map { message ->
                FlipperRequest(
                    data = message,
                    priority = FlipperRequestPriority.FOREGROUND,
                    onSendCallback = {
                        onUpdateIncrement(message.storageWriteRequest.file.data.size().toLong())
                    }
                )
            }
            val response = requestApi.request(requestFlow, onCancel = { id ->
                requestApi.request(
                    main {
                        commandId = id
                        hasNext = false
                        storageWriteRequest = writeRequest {
                            path = filePath
                        }
                    }.wrapToRequest(FlipperRequestPriority.RIGHT_NOW)
                ).collect()
                onUpdateIncrement(null)
            })
            info { "File send with response $response" }
            UploadFileResult(
                cleanupAction = {
                    deeplinkContent.cleanup(contentResolver)
                }
            )
        }
    }
}

fun DeeplinkContent.openStream(contentResolver: ContentResolver): InputStream? {
    return when (this) {
        is DeeplinkContent.ExternalUri -> contentResolver.openInputStream(Uri.parse(uriString))
        is InternalStorageFile -> file.inputStream()
        is DeeplinkContent.FFFContent -> flipperFileFormat.openStream()
        is DeeplinkContent.FFFCryptoContent -> null
    }
}

fun DeeplinkContent.cleanup(contentResolver: ContentResolver) {
    when (this) {
        is DeeplinkContent.ExternalUri ->
            contentResolver.releasePersistableUriPermission(
                Uri.parse(uriString),
                Intent.FLAG_GRANT_READ_URI_PERMISSION,
            )

        is InternalStorageFile -> file.delete()
        is DeeplinkContent.FFFContent -> {} // Nothing
        is DeeplinkContent.FFFCryptoContent -> {} // Nothing
    }
}

data class UploadFileResult(
    val cleanupAction: () -> Unit,
)
