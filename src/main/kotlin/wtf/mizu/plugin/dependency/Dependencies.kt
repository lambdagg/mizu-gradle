package wtf.mizu.plugin.dependency

import org.gradle.api.Project
import org.gradle.api.plugins.JavaLibraryPlugin
import org.gradle.api.plugins.JavaPlugin
import java.net.URI

fun Project.repository(name: String, url: String) {
    repositories.maven { repo ->
        repo.name = name
        repo.url = URI(url)
    }
}

fun Project.repository(
    name: String,
    url: String,
    user: String,
    password: String
) {
    repositories.maven { repo ->
        repo.name = name
        repo.url = URI(url)

        repo.credentials {
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
    val domainObjectProvider = this.configurations.register(id) {
        it.setVisible(true).defaultDependencies { deps ->
            deps.add(dependencies.create("$group:$id:$version"))
        }
    }

    project.plugins.withType(JavaLibraryPlugin::class.java) { _ ->
        // Adds the library as a dependency
        project.configurations.named(configuration)
            .configure { it.extendsFrom(domainObjectProvider.get()) }
    }
}
