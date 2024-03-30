package com.flipperdevices.deeplink.impl.parser.delegates

import com.eygraber.uri.Uri
import com.flipperdevices.core.di.AppGraph
import com.flipperdevices.core.log.LogTagProvider
import com.flipperdevices.deeplink.api.DeepLinkParserDelegate
import com.flipperdevices.deeplink.model.DeepLinkParserDelegatePriority
import com.flipperdevices.deeplink.model.Deeplink
import com.squareup.anvil.annotations.ContributesMultibinding
import javax.inject.Inject

private val SUPPORTED_HOSTS = listOf("lab.flipper.net", "my.flipp.dev")
private const val QUERY_URL = "url"
private const val QUERY_VERSION = "version"
private const val QUERY_CHANNEL = "channel"

@ContributesMultibinding(AppGraph::class, DeepLinkParserDelegate::class)
class DeepLinkWebUpdater @Inject constructor() : DeepLinkParserDelegate, LogTagProvider {
    override val TAG = "DeepLinkWebUpdater"

    override fun getPriority(uri: Uri): DeepLinkParserDelegatePriority? {
        if (SUPPORTED_HOSTS.contains(uri.host)) {
            return DeepLinkParserDelegatePriority.HIGH
        }
        return null
    }

    override suspend fun fromUri(uri: Uri): Deeplink? {
        val link = uri.getQueryParameter(QUERY_URL) ?: return null
        val version = uri.getQueryParameter(QUERY_VERSION) ?: ""
        val channel = uri.getQueryParameter(QUERY_CHANNEL) ?: ""
        return Deeplink.BottomBar.DeviceTab.WebUpdate(link, "$channel $version")
    }
}
