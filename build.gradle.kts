import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.jetbrains.kotlin.jvm") version Version.Plugin.KOTLIN
    id("org.ajoberstar.grgit") version Version.Plugin.GRGIT
    id("java-gradle-plugin")
    `maven-publish`
}

group   = Project.GROUP
version = Project.VERSION.replace("x", grgit.log().size.toString())

repositories {
    mavenCentral()
}

gradlePlugin {
    plugins {
        create(Project.NAME) {
            id = Project.ID
            implementationClass = "wtf.mizu.plugin.MizuPlugin"
        }
    }
}

tasks.withType<KotlinCompile>().all {
    kotlinOptions.jvmTarget = "16"
}

publishing {
    println(version)
    repositories {
        maven("https://maven.mizu.wtf/releases") {
            name = "mizu"
            credentials {
                username = project.properties["MIZU_USERNAME"] as String
                password = project.properties["MIZU_TOKEN"] as String
            }
        }
    }
}