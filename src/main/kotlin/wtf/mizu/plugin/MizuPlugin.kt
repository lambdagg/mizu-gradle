package wtf.mizu.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.publish.PublishingExtension
import wtf.mizu.plugin.dependency.dependsOn
import wtf.mizu.plugin.dependency.repository
import wtf.mizu.plugin.extension.MizuExtension
import java.net.URI

class MizuPlugin: Plugin<Project> {

    override fun apply(project: Project) {
        val extension = project.extensions.create("mizu", MizuExtension::class.java)
        project.afterEvaluate { a ->
            project.logger.error("HI")
            project.repository("mizu-releases", "https://maven.mizu.wtf/releases")
            project.repository("mizu-snapshots", "https://maven.mizu.wtf/snapshots")

            if(extension.common.isNotEmpty())
                project.dependsOn("wtf.mizu", "common", extension.common)
            if(extension.events.isNotEmpty()) {
                project.dependsOn("wtf.mizu", "events-api", extension.events)
                project.dependsOn("wtf.mizu", "events-${extension.eventsImpl.id}", extension.events)
            }

            project.extensions.configure(PublishingExtension::class.java) { publishing ->
                publishing.repositories { repositories ->
                    repositories.maven { maven ->
                        maven.name = "mizu-releases"
                        maven.url = URI.create("https://maven.mizu.wtf/releases")
                        maven.credentials {
                            it.username = project.properties["MIZU_USERNAME"] as String
                            it.password = project.properties["MIZU_TOKEN"] as String
                        }
                    }
                    repositories.maven { maven ->
                        maven.name = "mizu-snapshots"
                        maven.url = URI.create("https://maven.mizu.wtf/snapshots")
                    }
                }
            }
        }
    }

}