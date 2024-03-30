package com.flipperdevices.deeplink.api

import android.content.Intent
import com.eygraber.uri.toUri
import com.flipperdevices.deeplink.model.Deeplink


/**
 * Can be called only from permission owner for intent
 */
@Throws(SecurityException::class)
suspend fun DeepLinkParser.fromIntent(intent: Intent): Deeplink? {
    val androidUri = intent.data ?: return null

    return fromUri(androidUri.toUri())
}