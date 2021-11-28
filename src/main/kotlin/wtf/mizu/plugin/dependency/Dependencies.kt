@file:Suppress("NOTHING_TO_INLINE")
package wtf.mizu.plugin.dependency

import org.gradle.api.Project
import org.gradle.api.plugins.JavaLibraryPlugin
import org.gradle.api.plugins.JavaPlugin
import java.net.URI

inline fun Project.repository(name: String, url: String) {
    repositories.maven { r ->
        r.name = name
        r.url  = URI(url)
    }
}

inline fun Project.dependsOn(group: String, id: String, version: String, implementation: Boolean = false) {
    val domainObjectProvider = this.configurations.register(id) { c -> c
        .setVisible(true)
        .defaultDependencies { deps ->
            deps.add(dependencies.create("$group:$id:$version"))
        }
    }

    project.plugins.withType(JavaLibraryPlugin::class.java) { pl ->
        // Adds the library as a dependency
        if(implementation) {
            project.configurations.named(JavaPlugin.IMPLEMENTATION_CONFIGURATION_NAME)
                .configure { c -> c.extendsFrom(domainObjectProvider.get()) }
        } else {
            project.configurations.named(JavaPlugin.COMPILE_ONLY_CONFIGURATION_NAME)
                .configure { c -> c.extendsFrom(domainObjectProvider.get()) }
        }
    }
}