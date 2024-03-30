package com.flipperdevices.deeplink.impl.parser.delegates

import com.eygraber.uri.Uri
import com.flipperdevices.core.di.AppGraph
import com.flipperdevices.core.log.LogTagProvider
import com.flipperdevices.deeplink.api.DeepLinkParserDelegate
import com.flipperdevices.deeplink.impl.utils.Constants
import com.flipperdevices.deeplink.model.DeepLinkParserDelegatePriority
import com.flipperdevices.deeplink.model.Deeplink
import com.flipperdevices.deeplink.model.DeeplinkContent
import com.flipperdevices.keyparser.api.KeyParser
import com.squareup.anvil.annotations.ContributesMultibinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.URLDecoder
import javax.inject.Inject

private const val SCHEME_FLIPPERKEY = "flipperkey"

@ContributesMultibinding(AppGraph::class, DeepLinkParserDelegate::class)
class DeepLinkFlipperFormatSharing @Inject constructor(
    private val parser: KeyParser
) : DeepLinkParserDelegate, LogTagProvider {
    override val TAG = "DeepLinkFlipperFormatSharing"

    override fun getPriority(uri: Uri): DeepLinkParserDelegatePriority? {
        if (uri.scheme == SCHEME_FLIPPERKEY) {
            DeepLinkParserDelegatePriority.HIGH
        }

        if (Constants.SUPPORTED_HOSTS.contains(uri.host)) {
            return DeepLinkParserDelegatePriority.HIGH
        }

        return null
    }

    override suspend fun fromUri(url: Uri): Deeplink? {
        var actualUrl = url
        if (actualUrl.scheme == SCHEME_FLIPPERKEY) {
            val query = actualUrl.encodedQuery
            val decodedQuery = withContext(Dispatchers.IO) {
                URLDecoder.decode(query, "UTF-8")
            }
            actualUrl = Uri.parse(decodedQuery)
        }

        val (path, content) = parser.parseUri(actualUrl) ?: return null
        return Deeplink.RootLevel.SaveKey.FlipperKey(
            path,
            DeeplinkContent.FFFContent(path.nameWithExtension, content)
        )
    }
}
