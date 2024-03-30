package com.flipperdevices.deeplink.api

import com.eygraber.uri.Uri
import com.flipperdevices.deeplink.model.DeepLinkParserDelegatePriority
import com.flipperdevices.deeplink.model.Deeplink

interface DeepLinkParserDelegate {
    fun getPriority(uri: Uri): DeepLinkParserDelegatePriority?
    suspend fun fromUri(uri: Uri): Deeplink?
}
