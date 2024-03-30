package com.flipperdevices.deeplink.impl.parser.delegates

import com.eygraber.uri.Uri
import com.flipperdevices.core.di.AppGraph
import com.flipperdevices.core.log.LogTagProvider
import com.flipperdevices.deeplink.api.DeepLinkParserDelegate
import com.flipperdevices.deeplink.impl.utils.Constants
import com.flipperdevices.deeplink.model.DeepLinkParserDelegatePriority
import com.flipperdevices.deeplink.model.Deeplink
import com.squareup.anvil.annotations.ContributesMultibinding
import javax.inject.Inject

private val PATHS = listOf("o", "mfkey32")

@ContributesMultibinding(AppGraph::class, DeepLinkParserDelegate::class)
class DeepLinkMfKey @Inject constructor() : DeepLinkParserDelegate, LogTagProvider {
    override val TAG = "DeepLinkFap"

    override fun getPriority(uri: Uri): DeepLinkParserDelegatePriority? {
        val pathSegments = uri.pathSegments

        return when {
            !Constants.SUPPORTED_HOSTS.contains(uri.host) -> null
            pathSegments == PATHS -> DeepLinkParserDelegatePriority.HIGH
            else -> null
        }
    }

    override suspend fun fromUri(uri: Uri) = Deeplink.BottomBar.HubTab.OpenMfKey
}
