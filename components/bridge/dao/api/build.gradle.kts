plugins {
    id("flipper.multiplatform-lib")
    id("kotlinx-serialization")
//    id("kotlin-parcelize")
}

android.namespace = "com.flipperdevices.bridge.dao.api"

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(libs.kotlin.coroutines)
            implementation(libs.kotlin.serialization.json)
            implementation(libs.kotlin.immutable.collections)
        }
        androidMain.dependencies {
            implementation(projects.components.core.ui.theme)
            implementation(projects.components.core.ui.res)
            implementation(projects.components.core.ktx)


            implementation(libs.compose.ui)
        }
    }
}
