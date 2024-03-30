package com.flipperdevices.bridge.dao.api.model

import kotlinx.serialization.Serializable

@Serializable
data class WidgetData(
    val widgetId: Int,
    val flipperKeyPath: FlipperKeyPath?,
    val widgetType: WidgetType
)

enum class WidgetType {
    SIMPLE
}
