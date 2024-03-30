plugins {
    id("flipper.multiplatform-lib")
    id("flipper.anvil")
    id("flipper.protobuf")
}

android.namespace = "com.flipperdevices.core.preference"

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.components.core.di)
            api(libs.datastore)
        }
    }
}