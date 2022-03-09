package wtf.mizu.plugin.dependency

import org.gradle.api.Project
import org.gradle.api.plugins.JavaLibraryPlugin
import org.gradle.api.plugins.JavaPlugin
import java.net.URI

fun Project.repository(name: String, url: String) {
    repositories.maven { r ->
        r.name = name
        r.url  = URI(url)
    }
}

fun Project.repository(name: String, url: String, user: String, password: String) {
    repositories.maven { r ->
        r.name = name
        r.url  = URI(url)
        r.credentials {
            it.username = user
            it.password = password
        }
    }
}

fun Project.dependsOn(
    group: String,
    id: String,
    version: String,
    configuration: String = JavaPlugin.IMPLEMENTATION_CONFIGURATION_NAME
) {
    logger.info(id)
    val domainObjectProvider = this.configurations.register(id) {
        it.setVisible(true).defaultDependencies { deps ->
            deps.add(dependencies.create("$group:$id:$version"))
        }
    }

    project.plugins.withType(JavaLibraryPlugin::class.java) { _ ->
        // Adds the library as a dependency
        project.configurations.named(configuration)
            .configure { c -> c.extendsFrom(domainObjectProvider.get()) }
    }
}
