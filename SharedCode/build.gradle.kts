import com.android.build.gradle.internal.tasks.factory.dependsOn
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget

plugins {
    id( "com.android.library")
    kotlin("multiplatform")
}

android {
    compileSdkVersion(29)
    defaultConfig {
        minSdkVersion(19)
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
        }
    }
}

dependencies {
    commonTestImplementation("junit:junit:4.13")
    commonTestImplementation("org.jetbrains.kotlin:kotlin-test")
    commonTestImplementation("org.jetbrains.kotlin:kotlin-test-junit")
}

kotlin {

    val ktorVersion = "1.4.0"
    val coroutinesVersion = "1.3.9-native-mt"
    val serializationVersion = "1.0.0-RC"

    android()

    ios {
        binaries {
            framework {
                baseName = "SharedCode"
            }
        }
    }

    val commonMain by sourceSets.getting {
        dependencies {
            implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")
            implementation("org.jetbrains.kotlinx:kotlinx-serialization-core:$serializationVersion")
            implementation("io.ktor:ktor-client-core:$ktorVersion")
            implementation("io.ktor:ktor-client-serialization:$ktorVersion")
        }
    }

    val androidMain by sourceSets.getting {
        dependencies {
            implementation("io.ktor:ktor-client-android:$ktorVersion")
        }
    }

    val iosMain by sourceSets.getting {
        dependencies {
            implementation("io.ktor:ktor-client-ios:$ktorVersion")
        }
    }
}

val packForXcode by tasks.creating(Sync::class) {
    group = "build"
    val mode = System.getenv("CONFIGURATION") ?: "DEBUG"
    val sdkName = System.getenv("SDK_NAME") ?: "iphonesimulator"
    val targetName = "ios" + if (sdkName.startsWith("iphoneos")) "Arm64" else "X64"
    val framework = kotlin.targets.getByName<KotlinNativeTarget>(targetName).binaries.getFramework(mode)
    inputs.property("mode", mode)
    dependsOn(framework.linkTask)
    val targetDir = File(buildDir, "xcode-frameworks")
    from({ framework.outputDirectory })
    into(targetDir)
}

tasks.getByName("build").dependsOn(packForXcode)

tasks.register("dumpGeneratedSharedCodePath")  {
    val targetDir = File(buildDir, "bin")
    doLast {
        println("$targetDir")
    }
}.dependsOn("build")

tasks.register("iosSimulatorTest")  {
    val device = project.findProperty("iosDevice") as? String ?: "iPhone 8"
    dependsOn("iosX64Test")
    group = JavaBasePlugin.VERIFICATION_GROUP
    description = "Runs tests for target 'ios' on an iOS simulator"

    doLast {
        val  binary = (kotlin.targets["iosX64"] as KotlinNativeTarget).binaries.getTest("DEBUG").outputFile
        exec {
            commandLine("xcrun", "simctl", "spawn", "--standalone", device, binary.absolutePath)
        }
    }
}