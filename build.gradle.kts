plugins {
    id("java")
    id("org.jetbrains.kotlin.jvm") version "1.9.22"
    id("org.jetbrains.intellij") version "1.17.2"
}

group = "me.tpgc"
version = "1.1"

repositories {
    mavenCentral()
}

dependencies {
    //implementation("com.intellij.cidr.lang:clangd:19.0.0") // adjust version as needed
}

// Configure Gradle IntelliJ Plugin
// Read more: https://plugins.jetbrains.com/docs/intellij/tools-gradle-intellij-plugin.html
intellij {
    version.set("2024.1")
    type.set("CL") // Target IDE Platform

    plugins.set(listOf(
        "com.intellij.clion",
        "com.intellij.cidr.base",
        "com.intellij.cidr.lang.clangd",
        "com.intellij.cidr.lang",
        "com.intellij.cidr.compiler.custom",
        "com.intellij.cidr.lang.clangdBridge",
        "com.intellij.cidr.markdown",
        "com.intellij.clion-compdb",
        "org.jetbrains.plugins.cidr-intelliLang"
    ))
}

tasks {
    // Set the JVM compatibility versions
    withType<JavaCompile> {
        sourceCompatibility = "17"
        targetCompatibility = "17"
    }
    withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        kotlinOptions.jvmTarget = "17"
    }

    patchPluginXml {
        sinceBuild.set("232")
        untilBuild.set("241.*")
    }

    buildSearchableOptions {
        enabled = false
    }

    signPlugin {
        certificateChain.set(System.getenv("CERTIFICATE_CHAIN"))
        privateKey.set(System.getenv("PRIVATE_KEY"))
        password.set(System.getenv("PRIVATE_KEY_PASSWORD"))
    }

    publishPlugin {
        token.set(System.getenv("PUBLISH_TOKEN"))
    }
}
