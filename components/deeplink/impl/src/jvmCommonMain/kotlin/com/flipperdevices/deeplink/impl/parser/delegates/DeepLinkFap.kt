package com.flipperdevices.deeplink.impl.parser.delegates

import com.eygraber.uri.Uri
import com.flipperdevices.core.di.AppGraph
import com.flipperdevices.core.log.LogTagProvider
import com.flipperdevices.deeplink.api.DeepLinkParserDelegate
import com.flipperdevices.deeplink.model.DeepLinkParserDelegatePriority
import com.flipperdevices.deeplink.model.Deeplink
import com.squareup.anvil.annotations.ContributesMultibinding
import javax.inject.Inject

private val SUPPORTED_HOSTS = listOf("lab.flipper.net")
private const val PATH = "apps"

@ContributesMultibinding(AppGraph::class, DeepLinkParserDelegate::class)
class DeepLinkFap @Inject constructor() : DeepLinkParserDelegate, LogTagProvider {
    override val TAG = "DeepLinkFap"

    override fun getPriority(url: Uri): DeepLinkParserDelegatePriority? {
        if (!SUPPORTED_HOSTS.contains(url.host)) {
            return null
        }

        val pathSegment = url.pathSegments

        if (pathSegment.size == 2 && pathSegment.first() == PATH) {
            return DeepLinkParserDelegatePriority.HIGH
        }

        return null
    }

    override suspend fun fromUri(url: Uri): Deeplink? {
        if (!SUPPORTED_HOSTS.contains(url.host)) {
            return null
        }
        val pathSegment = url.pathSegments
        val fapId = pathSegment.getOrNull(1) ?: return null
        return Deeplink.BottomBar.HubTab.FapHub.Fap(fapId)
    }
}
