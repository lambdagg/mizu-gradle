plugins {
    id("org.jetbrains.kotlin.jvm") version Version.Plugin.KOTLIN
    id("org.ajoberstar.grgit") version Version.Plugin.GRGIT
    id("java-gradle-plugin")
    `maven-publish`
}

group   = Project.GROUP
version = Project.VERSION.replace("x", grgit.log().size.toString()) + if (grgit.status().isClean) "" else "-SNAPSHOT"

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

publishing {
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