package wtf.mizu.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.publish.PublishingExtension
import wtf.mizu.plugin.dependency.dependsOn
import wtf.mizu.plugin.dependency.repository
import wtf.mizu.plugin.extension.MizuExtension
import wtf.mizu.plugin.publishing.mizuPublishMavenRepository
import java.net.URI

class MizuPlugin: Plugin<Project> {

    override fun apply(project: Project) {
        val extension = project.extensions.create("mizu", MizuExtension::class.java)

        project.afterEvaluate { a ->
            // Adds the repositories of mizu
            project.repository("mizu-releases", "https://maven.mizu.wtf/releases")
            project.repository("mizu-snapshots", "https://maven.mizu.wtf/snapshots")

            if(project.properties.containsKey("MIZU_USERNAME") && project.properties.containsKey("MIZU_TOKEN")) {
                val username = project.properties["MIZU_USERNAME"] as String
                val token = project.properties["MIZU_TOKEN"] as String
                project.repository(
                    "mizu-releases", "https://maven.mizu.wtf/private",
                    username, token
                )
            }

            // Adds the libraries of mizu if requested
            if(extension.common.isNotEmpty())
                project.dependsOn("wtf.mizu", "common", extension.common)
            if(extension.animations.isNotEmpty())
                project.dependsOn("wtf.mizu", "animations", extension.animations)
            if(extension.events.isNotEmpty()) {
                project.dependsOn("wtf.mizu", "events-api", extension.events)
                project.dependsOn("wtf.mizu", "events-${extension.eventsImpl.id}", extension.events)
            }
            if(extension.settings.isNotEmpty())
                project.dependsOn("wtf.mizu", "settings", extension.settings)

            // Adds the repositories of mizu for maven publish if its available.
            project.mizuPublishMavenRepository()
        }
    }

}