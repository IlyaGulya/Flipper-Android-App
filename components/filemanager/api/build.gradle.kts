plugins {
    id("flipper.multiplatform-lib")
}

android.namespace = "com.flipperdevices.filemanager.api"

dependencies {
    implementation(projects.components.deeplink.api)

    implementation(projects.components.core.ui.decompose)

    implementation(libs.compose.ui)
    implementation(libs.decompose)
}
