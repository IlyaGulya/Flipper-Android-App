plugins {
    id("flipper.multiplatform-lib")
}

android.namespace = "com.flipperdevices.screenstreaming.api"

kotlin.sourceSets.commonMain {
    dependencies {
        implementation(projects.components.core.ui.decompose)
        implementation(libs.decompose)
    }
}
