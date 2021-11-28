package wtf.mizu.plugin.publishing

import org.gradle.api.Project
import org.gradle.api.publish.PublishingExtension
import java.net.URI

inline fun Project.mizuPublishMavenRepository() {
    extensions.configure(PublishingExtension::class.java) { publishing ->
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