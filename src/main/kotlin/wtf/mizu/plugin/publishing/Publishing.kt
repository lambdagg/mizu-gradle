package wtf.mizu.plugin.publishing

import org.gradle.api.Project
import org.gradle.api.publish.PublishingExtension
import wtf.mizu.plugin.MizuPlugin
import java.net.URI

fun Project.mizuPublishMavenRepository() {
    extensions.configure(PublishingExtension::class.java) { publishing ->
        // The user cannot publish
        if (!project.properties.containsKey("MIZU_USERNAME") || !project.properties.containsKey("MIZU_TOKEN"))
            return@configure

        publishing.repositories { repositories ->
            repositories.maven { maven ->
                maven.name = "${MizuPlugin.NAME}-releases"
                maven.url = URI.create(MizuPlugin.REPO_RELEASES_URL)
                maven.credentials {
                    it.username = project.properties["MIZU_USERNAME"] as String
                    it.password = project.properties["MIZU_TOKEN"] as String
                }
            }
            repositories.maven { maven ->
                maven.name = "${MizuPlugin.NAME}-snapshots"
                maven.url = URI.create(MizuPlugin.REPO_SNAPSHOTS_URL)
                maven.credentials {
                    it.username = project.properties["MIZU_USERNAME"] as String
                    it.password = project.properties["MIZU_TOKEN"] as String
                }
            }
            repositories.maven { maven ->
                maven.name = "${MizuPlugin.NAME}-private"
                maven.url = URI.create(MizuPlugin.REPO_PRIVATE_URL)
                maven.credentials {
                    it.username = project.properties["MIZU_USERNAME"] as String
                    it.password = project.properties["MIZU_TOKEN"] as String
                }
            }
        }
    }
}