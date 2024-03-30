plugins {
    id("flipper.multiplatform-lib")
    id("kotlinx-serialization")
}

android.namespace = "com.flipperdevices.faphub.dao.api"

kotlin.sourceSets.commonMain {
    dependencies {
        implementation(projects.components.core.data)
        implementation(projects.components.core.progress)
        implementation(projects.components.core.preference)

        implementation(libs.kotlin.serialization.json)
        implementation(libs.kotlin.immutable.collections)

        implementation(projects.components.faphub.target.api)

        implementation(libs.annotations)
    }
}