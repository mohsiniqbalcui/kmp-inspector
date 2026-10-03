import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.android.kotlin.multiplatform.library)
    alias(libs.plugins.vanniktech.mavenPublish)
}

group = "io.github.debugkmpinspector"
version = providers.gradleProperty("libraryVersion").getOrElse("1.0.0-SNAPSHOT")

/**
 * Room support for every platform Room runs on. On Android this delegates to the collector built
 * into the main library; on desktop and iOS it reads through Room's connection API, which is the
 * only path there.
 */
kotlin {
    jvm()
    androidLibrary {
        namespace = "com.mohsiniqbalcui.kmpinspector.room"
        compileSdk = libs.versions.android.compileSdk.get().toInt()
        minSdk = libs.versions.android.minSdk.get().toInt()
        compilerOptions { jvmTarget = JvmTarget.JVM_11 }
    }
    iosArm64()
    iosSimulatorArm64()

    sourceSets {
        commonMain.dependencies {
            api(project(":library"))
            implementation(libs.room.runtime)
            implementation(libs.kotlinx.coroutines.core)
        }
    }
}

mavenPublishing {
    publishToMavenCentral()
    if (providers.gradleProperty("signingInMemoryKey").isPresent) {
        signAllPublications()
    }
    coordinates(group.toString(), "kmp-inspector-room", version.toString())
    pom {
        name = "KmpInspector Room"
        description = "Shows a Room database in the KmpInspector Database panel on Android, iOS and desktop."
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
