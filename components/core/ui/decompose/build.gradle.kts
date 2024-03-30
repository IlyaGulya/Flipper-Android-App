plugins {
    id("flipper.multiplatform-compose")
}

android.namespace = "com.flipperdevices.ui.decompose"

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(libs.bundles.decompose)
            implementation(libs.essenty.lifecycle.coroutines)
            implementation(libs.kotlin.serialization.json)
        }
    }
}

dependencies {
    implementation(projects.components.core.activityholder)

//    implementation(libs.compose.ui)
//    implementation(libs.compose.foundation)
//    implementation(libs.compose.activity)

//    implementation(libs.lifecycle.compose)

//    implementation(libs.lifecycle.runtime.ktx)
//    implementation(libs.lifecycle.viewmodel.ktx)

}
