package com.flipperdevices.deeplink.impl.parser

import com.eygraber.uri.Uri
import com.flipperdevices.core.di.AppGraph
import com.flipperdevices.core.log.LogTagProvider
import com.flipperdevices.core.log.error
import com.flipperdevices.core.log.info
import com.flipperdevices.core.log.warn
import com.flipperdevices.deeplink.api.DeepLinkParser
import com.flipperdevices.deeplink.api.DeepLinkParserDelegate
import com.flipperdevices.deeplink.model.Deeplink
import com.squareup.anvil.annotations.ContributesBinding
import javax.inject.Inject

@ContributesBinding(AppGraph::class, DeepLinkParser::class)
class DeepLinkParserImpl @Inject constructor(
    private val delegates: MutableSet<DeepLinkParserDelegate>
) : DeepLinkParser, LogTagProvider {
    override val TAG = "DeepLinkParser"

    override suspend fun fromUri(uri: Uri): Deeplink? {
        info { "Try parse uri with scheme: ${uri.scheme}, uri: $uri" }

        val sortedDelegates =
            delegates
                .mapNotNull { delegate ->
                    val priority = delegate.getPriority(uri) ?: return@mapNotNull null
                    delegate to priority
                }
                .sortedByDescending { (_, priority) -> priority }
                .map { (delegate, _) -> delegate }

        for (delegate in sortedDelegates) {
            try {
                info { "Try ${delegate.javaClass}..." }
                val deeplink = delegate.fromUri(uri)
                if (deeplink != null) {
                    info { "Parsed deeplink: $deeplink. " }
                    return deeplink
                }
            } catch (e: Throwable) {
                error(e) { "Exception while try open $uri" }
            }
        }
        warn { "Failed parse uri $uri" }
        return null
    }
}
