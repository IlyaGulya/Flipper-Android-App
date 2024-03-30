package com.flipperdevices.deeplink.impl.parser.delegates

import android.content.ContentResolver
import android.content.Context
import com.eygraber.uri.Uri
import com.eygraber.uri.toAndroidUri
import com.flipperdevices.core.di.AppGraph
import com.flipperdevices.core.ktx.jre.filename
import com.flipperdevices.core.log.LogTagProvider
import com.flipperdevices.core.log.error
import com.flipperdevices.deeplink.api.DeepLinkParserDelegate
import com.flipperdevices.deeplink.model.DeepLinkParserDelegatePriority
import com.flipperdevices.deeplink.model.Deeplink
import com.flipperdevices.deeplink.model.DeeplinkContent
import com.squareup.anvil.annotations.ContributesMultibinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import javax.inject.Inject

@ContributesMultibinding(AppGraph::class, DeepLinkParserDelegate::class)
class DeepLinkFileUriCopy @Inject constructor(
    private val context: Context,
) : DeepLinkParserDelegate, LogTagProvider {
    override val TAG = "DeepLinkFileUriCopy"

    override fun getPriority(uri: Uri): DeepLinkParserDelegatePriority {
        return DeepLinkParserDelegatePriority.LOW
    }

    // Fallback if DeepLinkFileUriGrantPermission failed: copy from uri to tmp file
    override suspend fun fromUri(uri: Uri): Deeplink? {
        val contentResolver = context.contentResolver

        return Deeplink.RootLevel.SaveKey.ExternalContent(
            content = buildInternalFile(
                contentResolver,
                context.cacheDir,
                uri
            )
        )
    }

    private suspend fun buildInternalFile(
        contentResolver: ContentResolver,
        cacheDir: File,
        uri: Uri
    ): DeeplinkContent? = withContext(Dispatchers.IO) {
        val androidUri = uri.toAndroidUri()
        val filename =
            androidUri.filename(contentResolver) ?: System.currentTimeMillis()
                .toString()
        val temporaryFile = File(cacheDir, filename)
        if (temporaryFile.exists()) {
            temporaryFile.delete()
        }
        val exception = runCatching {
            contentResolver.openInputStream(androidUri).use { inputStream ->
                temporaryFile.outputStream().use { outputStream ->
                    inputStream?.copyTo(outputStream)
                }
            }
        }.exceptionOrNull()

        if (exception != null) {
            error(exception) { "Error while copy uri $uri to internal file $temporaryFile" }
            return@withContext null
        }

        return@withContext DeeplinkContent.InternalStorageFile(temporaryFile.absolutePath)
    }
}