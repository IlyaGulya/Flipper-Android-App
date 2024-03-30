plugins {
    id("flipper.multiplatform-compose")
    id("flipper.anvil")
}

android.namespace = "com.flipperdevices.core.ui.theme"

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.components.core.di)
//            implementation(projects.components.core.ui.res)
            implementation(projects.components.core.ui.lifecycle)
            implementation(projects.components.core.ktx)
            implementation(projects.components.core.preference)

//            implementation(libs.lifecycle.viewmodel.ktx)
//            implementation(libs.lifecycle.compose)
//            implementation(libs.appcompat)
        }
    }
}
