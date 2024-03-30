import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

plugins {
    id("com.squareup.anvil")
}

anvil {
    generateDaggerFactories.set(true)
}

pluginManager.withPlugin("kotlin-kapt") {
    anvil {
        generateDaggerFactories.set(false)
    }
}

pluginManager.withPlugin("kotlin-android") {
    dependencies {
        "implementation"(libs.dagger)
        "implementation"(libs.anvil.utils.annotations)
    }
}

pluginManager.withPlugin("org.jetbrains.kotlin.multiplatform") {
    the<KotlinMultiplatformExtension>().apply {
        sourceSets {
            val desktopMain by getting
            val jvmCommonMain by getting

            jvmCommonMain.dependencies {
                implementation(libs.dagger)
                implementation(libs.anvil.utils.annotations)
            }
        }
    }
}

dependencies {
    "anvil"(libs.anvil.utils.compiler)
}