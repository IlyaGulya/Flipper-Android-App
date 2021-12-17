plugins {
    androidCompose
    id("com.squareup.anvil")
    kotlin("kapt")
}

dependencies {
    implementation(project(":components:connection:api"))

    implementation(project(":components:core:di"))
    implementation(project(":components:core:ui"))

    implementation(project(":components:bridge:api"))
    implementation(project(":components:bridge:service:api"))
    implementation(Libs.NORDIC_BLE_KTX)

    // Compose
    implementation(libs.compose.ui)
    implementation(libs.compose.tooling)
    implementation(libs.compose.foundation)
    implementation(libs.compose.material)

    // Dagger deps
    implementation(libs.dagger)
    kapt(libs.dagger.kapt)

    implementation(Libs.LIFECYCLE_RUNTIME_KTX)
    implementation(Libs.LIFECYCLE_VIEWMODEL_KTX)
    implementation(Libs.LIFECYCLE_COMPOSE)
    implementation(Libs.FRAGMENT_KTX)
}