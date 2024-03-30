plugins {
    id("flipper.multiplatform-lib")
    id("kotlin-parcelize")
}

android.namespace = "com.flipperdevices.bridge.api"

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.components.bridge.pbutils)
            implementation(projects.components.core.log)
            implementation(projects.components.core.ktx)
            implementation(projects.components.core.data)

            implementation(libs.kotlin.coroutines)
            implementation(libs.kotlin.immutable.collections)
            implementation(libs.ble.common)
            implementation(libs.ble.scan)
        }

        commonTest.dependencies {
            implementation(libs.junit)
            implementation(libs.kotlin.coroutines.test)
            implementation(libs.roboelectric)
            implementation(libs.ktx.testing)
            implementation(libs.mockk)
        }
    }
}
