plugins {
    id("flipper.multiplatform-lib")
    id("kotlinx-serialization")
}

android.namespace = "com.flipperdevices.rootscreen.api"

kotlin.sourceSets.commonMain {
    dependencies {
        implementation(projects.components.core.ui.decompose)

        implementation(projects.components.updater.api)
        implementation(projects.components.deeplink.api)
        implementation(projects.components.bridge.dao.api)

        implementation(libs.decompose)
    }
}