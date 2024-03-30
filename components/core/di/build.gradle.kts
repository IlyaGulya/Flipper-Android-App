plugins {
    id("flipper.multiplatform-lib")
}

android.namespace = "com.flipperdevices.core.di"

kotlin {
    sourceSets {
        jvmCommonMain.dependencies {
            implementation(libs.dagger)
        }
    }
}