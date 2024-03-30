package com.flipperdevices.deeplink.impl.parser.delegates

import android.net.Uri as AndroidUri
import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import com.eygraber.uri.Uri
import com.eygraber.uri.toAndroidUri
import com.flipperdevices.core.di.AppGraph
import com.flipperdevices.core.ktx.jre.filename
import com.flipperdevices.core.ktx.jre.length
import com.flipperdevices.deeplink.api.DeepLinkParserDelegate
import com.flipperdevices.deeplink.model.DeepLinkParserDelegatePriority
import com.flipperdevices.deeplink.model.Deeplink
import com.flipperdevices.deeplink.model.DeeplinkContent
import com.squareup.anvil.annotations.ContributesMultibinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

@ContributesMultibinding(AppGraph::class, DeepLinkParserDelegate::class)
class DeepLinkFileUriGrantPermission @Inject constructor(
    private val context: Context,
) : DeepLinkParserDelegate {
    override fun getPriority(uri: Uri): DeepLinkParserDelegatePriority {
        return DeepLinkParserDelegatePriority.DEFAULT
    }

    override suspend fun fromUri(uri: Uri): Deeplink? {
        val androidUri = uri.toAndroidUri()
        val contentResolver = context.contentResolver

        // We need persistable permission for read file on next activities
        val permissionGranted = runCatching {
            contentResolver.takePersistableUriPermission(
                androidUri,
                Intent.FLAG_GRANT_READ_URI_PERMISSION
            )
        }.isSuccess

        if (!permissionGranted) {
            return null
        }

        return Deeplink.RootLevel.SaveKey.ExternalContent(
            content = buildExternalUri(
                contentResolver,
                androidUri,
            )
        )
    }

    private suspend fun buildExternalUri(
        contentResolver: ContentResolver,
        uri: AndroidUri
    ): DeeplinkContent = withContext(Dispatchers.IO) {
        return@withContext DeeplinkContent.ExternalUri(
            filename = uri.filename(contentResolver),
            size = uri.length(contentResolver),
            uriString = uri.toString()
        )
    }
}
