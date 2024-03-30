plugins {
    id("flipper.multiplatform-lib")
    id("kotlinx-serialization")
}

android.namespace = "com.flipperdevices.deeplink.api"

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.components.bridge.dao.api)
            implementation(projects.components.core.ktx)

            implementation(libs.kotlin.serialization.json)

            implementation(libs.uriKmp)
            implementation(libs.annotations)
        }
    }
}
