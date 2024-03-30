plugins {
    id("flipper.multiplatform-lib")
}

android.namespace = "com.flipperdevices.faphub.target.api"

kotlin.sourceSets.commonMain {
    dependencies {
        implementation(projects.components.core.data)

        implementation(libs.kotlin.coroutines)
    }
}