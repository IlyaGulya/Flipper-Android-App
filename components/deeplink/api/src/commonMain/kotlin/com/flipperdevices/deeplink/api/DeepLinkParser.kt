package com.flipperdevices.deeplink.api

import com.eygraber.uri.Uri
import com.flipperdevices.deeplink.model.Deeplink

/**
 * Helper for build deeplink object from Intent
 */
interface DeepLinkParser {
    /**
     * Can be called only from permission owner for uri
     */
    @Throws(SecurityException::class)
    suspend fun fromUri(uri: Uri): Deeplink?
}