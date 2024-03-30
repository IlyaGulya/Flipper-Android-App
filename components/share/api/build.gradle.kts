plugins {
    id("flipper.multiplatform-compose")
}

android.namespace = "com.flipperdevices.share.api"

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.components.deeplink.api)
            implementation(projects.components.bridge.dao.api)
            implementation(projects.components.core.ui.decompose)

            implementation(libs.decompose)
        }
    }
}

//dependencies {
//
//    implementation(libs.compose.ui)
//}
