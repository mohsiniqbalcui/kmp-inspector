import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.android.kotlin.multiplatform.library)
    alias(libs.plugins.vanniktech.mavenPublish)
}

group = "io.github.debugkmpinspector"
version = providers.gradleProperty("libraryVersion").getOrElse("1.0.0-SNAPSHOT")

/**
 * No-op twin of :library-ktor for release builds: the same plugin and config names, installs
 * nothing and records nothing.
 */
kotlin {
    jvm()
    androidLibrary {
        namespace = "com.mohsiniqbalcui.kmpinspector.ktor.noop"
        compileSdk = libs.versions.android.compileSdk.get().toInt()
        minSdk = libs.versions.android.minSdk.get().toInt()
        compilerOptions { jvmTarget = JvmTarget.JVM_11 }
    }
    iosArm64()
    iosSimulatorArm64()

    sourceSets {
        commonMain.dependencies {
            api(project(":library-noop"))
            api(libs.ktor.client.core)
        }
    }
}

mavenPublishing {
    publishToMavenCentral()
    if (providers.gradleProperty("signingInMemoryKey").isPresent) {
        signAllPublications()
    }
    coordinates(group.toString(), "kmp-inspector-ktor-no-op", version.toString())
    pom {
        name = "KmpInspector Ktor plugin no-op"
        description = "Empty implementation of the KmpInspector Ktor plugin for release builds: same API, records nothing."
        inceptionYear = "2026"
        url = "https://github.com/mohsiniqbalcui/kmp-inspector/"
        licenses {
            license {
                name = "The Apache License, Version 2.0"
                url = "https://www.apache.org/licenses/LICENSE-2.0.txt"
                distribution = "https://www.apache.org/licenses/LICENSE-2.0.txt"
            }
        }
        developers {
            developer {
                id = "mohsiniqbalcui"
                name = "Mohsin Iqbal"
                url = "https://github.com/mohsiniqbalcui/"
            }
        }
        scm {
            url = "https://github.com/mohsiniqbalcui/kmp-inspector/"
            connection = "scm:git:git://github.com/mohsiniqbalcui/kmp-inspector.git"
            developerConnection = "scm:git:ssh://git@github.com/mohsiniqbalcui/kmp-inspector.git"
        }
    }
}
