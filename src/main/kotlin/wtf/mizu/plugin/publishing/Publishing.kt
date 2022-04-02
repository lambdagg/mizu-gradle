package wtf.mizu.plugin.publishing

import org.gradle.api.Project
import org.gradle.api.publish.PublishingExtension
import wtf.mizu.plugin.MizuPlugin
import java.net.URI

fun Project.mizuPublishMavenRepository() {
    extensions.configure(PublishingExtension::class.java) { publishing ->
        val mizuUsername = project.properties["MIZU_USERNAME"] as? String
            ?: return@configure

        val mizuToken = project.properties["MIZU_TOKEN"] as? String
            ?: return@configure

        // Repository map (name -> url)
        val repos = mapOf(
            "release" to MizuPlugin.REPO_RELEASES_URL,
            "snapshots" to MizuPlugin.REPO_SNAPSHOTS_URL,
            "private" to MizuPlugin.REPO_PRIVATE_URL,
        )

        // Register repositories
        publishing.repositories { repositories ->
            repos.forEach { repo ->
                repositories.maven { maven ->
                    maven.name = "${MizuPlugin.NAME}-${repo.key}"
                    maven.url = URI.create(repo.value)
                    maven.credentials {
                        it.username = mizuUsername
                        it.password = mizuToken
                    }
                }
            }
        }
    }
}
