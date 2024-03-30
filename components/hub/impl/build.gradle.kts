plugins {
    id("flipper.multiplatform-compose")
    id("flipper.anvil")
    id("kotlinx-serialization")
}

android.namespace = "com.flipperdevices.hub.impl"

kotlin {
    sourceSets {
        jvmCommonMain.dependencies {
            implementation(projects.components.hub.api)
            implementation(projects.components.faphub.maincard.api)
            implementation(projects.components.faphub.main.api)
            implementation(projects.components.analytics.metric.api)

            implementation(projects.components.core.di)
            implementation(projects.components.core.preference)
//            implementation(projects.components.core.ui.res)
//            implementation(projects.components.core.ui.ktx)
            implementation(projects.components.core.ui.theme)
            implementation(projects.components.core.ui.lifecycle)
            implementation(projects.components.core.ui.decompose)

//            implementation(libs.appcompat)
            implementation(projects.components.nfc.attack.api)
            implementation(projects.components.screenstreaming.api)
            implementation(projects.components.deeplink.api)
            implementation(projects.components.bottombar.api)
            implementation(projects.components.rootscreen.api)
            implementation(projects.components.faphub.installedtab.api)

            // Compose
//            implementation(libs.lifecycle.compose)

            implementation(libs.bundles.decompose)
            implementation(libs.kotlin.immutable.collections)
        }
    }
}

dependencies {



}
