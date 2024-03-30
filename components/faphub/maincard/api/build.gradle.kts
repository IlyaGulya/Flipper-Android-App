plugins {
    id("flipper.multiplatform-lib")
}

android.namespace = "com.flipperdevices.faphub.maincard.api"

kotlin {
    sourceSets {
        jvmCommonMain.dependencies {
            implementation(libs.decompose)
        }
    }
}