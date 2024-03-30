plugins {
    id("flipper.multiplatform-lib")
}

android.namespace = "com.flipperdevices.faphub.main.api"

kotlin {
    sourceSets {
        jvmCommonMain.dependencies {
            implementation(projects.components.core.ui.decompose)

            implementation(projects.components.deeplink.api)

            implementation(libs.decompose)
        }
    }
}