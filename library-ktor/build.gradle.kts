import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.android.kotlin.multiplatform.library)
    alias(libs.plugins.vanniktech.mavenPublish)
}

group = "io.github.debugkmpinspector"
version = providers.gradleProperty("libraryVersion").getOrElse("1.0.0-SNAPSHOT")

/**
 * Ktor client plugin that records every call into the inspector's Network panel, on every
 * platform the client runs on. Its own artifact, so apps that use OkHttp never pull Ktor in.
 */
kotlin {
    jvm()
    androidLibrary {
        namespace = "com.mohsiniqbalcui.kmpinspector.ktor"
        compileSdk = libs.versions.android.compileSdk.get().toInt()
        minSdk = libs.versions.android.minSdk.get().toInt()
        compilerOptions { jvmTarget = JvmTarget.JVM_11 }
        withHostTestBuilder {}.configure {}
    }
    iosArm64()
    iosSimulatorArm64()

    sourceSets {
        commonMain.dependencies {
            api(project(":library"))
            implementation(libs.ktor.client.core)
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
            implementation(libs.ktor.client.mock)
            implementation(libs.kotlinx.coroutines.test)
        }
    }
}

mavenPublishing {
    publishToMavenCentral()
    if (providers.gradleProperty("signingInMemoryKey").isPresent) {
        signAllPublications()
    }
    coordinates(group.toString(), "kmp-inspector-ktor", version.toString())
    pom {
        name = "KmpInspector Ktor plugin"
        description = "Records Ktor client calls into the KmpInspector Network panel on Android, iOS and desktop."
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
