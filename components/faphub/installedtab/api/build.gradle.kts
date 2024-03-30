plugins {
    id("flipper.multiplatform-lib")
}

android.namespace = "com.flipperdevices.faphub.installedtab.api"

kotlin.sourceSets.commonMain {
    dependencies {
        implementation(projects.components.faphub.dao.api)

        implementation(libs.decompose)
    }
}

