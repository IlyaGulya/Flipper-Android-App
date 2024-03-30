plugins {
    id("flipper.multiplatform-lib")
}

android.namespace = "com.flipperdevices.bottombar.api"

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.components.deeplink.api)
            implementation(projects.components.core.ui.decompose)

            implementation(libs.decompose)
        }

        commonTest.dependencies {
            implementation(projects.components.core.test)
            implementation(libs.junit)
            implementation(libs.mockito.kotlin)
        }
    }
}
