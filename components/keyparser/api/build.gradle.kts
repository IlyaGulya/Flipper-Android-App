plugins {
    id("flipper.multiplatform-lib")
}

android.namespace = "com.flipperdevices.keyparser.api"

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.components.bridge.dao.api)

            implementation(projects.components.core.ktx)

            implementation(libs.uriKmp)

            implementation(libs.kotlin.immutable.collections)
        }
    }
}
