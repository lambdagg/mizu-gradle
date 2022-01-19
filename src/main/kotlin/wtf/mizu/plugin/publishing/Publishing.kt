package wtf.mizu.plugin.publishing

import org.gradle.api.Project
import org.gradle.api.publish.PublishingExtension
import java.net.URI

fun Project.mizuPublishMavenRepository() {
    extensions.configure(PublishingExtension::class.java) { publishing ->
        // The person cannot publish stuff.
        if(!project.properties.containsKey("MIZU_USERNAME") || !project.properties.containsKey("MIZU_TOKEN"))
            return@configure

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
                maven.credentials {
                    it.username = project.properties["MIZU_USERNAME"] as String
                    it.password = project.properties["MIZU_TOKEN"] as String
                }
            }
            repositories.maven { maven ->
                maven.name = "mizu-private"
                maven.url = URI.create("https://maven.mizu.wtf/private")
                maven.credentials {
                    it.username = project.properties["MIZU_USERNAME"] as String
                    it.password = project.properties["MIZU_TOKEN"] as String
                }
            }
        }
    }
}