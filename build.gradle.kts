plugins {
    id("org.jetbrains.kotlin.jvm") version Version.Plugin.KOTLIN
    id("org.ajoberstar.grgit") version Version.Plugin.GRGIT
    id("java-gradle-plugin")
    `maven-publish`
}

group   = Project.GROUP
version = Project.VERSION

println(grgit.status().isClean)

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

val mizuUser: String by properties
val mizuToken: String by properties

publishing {
    repositories {
        maven("https://maven.mizu.wtf/releases") {
            name = "mizu"
            credentials {
                username = mizuUser
                password = mizuToken
            }
        }
    }
}