package com.flipperdevices.keyparser.api

import com.flipperdevices.bridge.dao.api.model.FlipperFileFormat
import com.flipperdevices.bridge.dao.api.model.FlipperFilePath
import com.flipperdevices.bridge.dao.api.model.FlipperKey
import com.flipperdevices.bridge.dao.api.model.FlipperKeyCrypto
import com.flipperdevices.keyparser.api.model.FlipperKeyParsed
import io.ktor.http.Url

interface KeyParser {
    suspend fun parseKey(flipperKey: FlipperKey): FlipperKeyParsed

    suspend fun parseUri(uri: Url): Pair<FlipperFilePath, FlipperFileFormat>?

    suspend fun keyToUrl(flipperKey: FlipperKey): String

    fun cryptoKeyDataToUri(key: FlipperKeyCrypto): String

    fun parseUriToCryptoKeyData(uri: Url): FlipperKeyCrypto?
}
