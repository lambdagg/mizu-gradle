package wtf.mizu.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project
import wtf.mizu.plugin.dependency.dependsOn
import wtf.mizu.plugin.dependency.repository
import wtf.mizu.plugin.extension.MizuExtension
import wtf.mizu.plugin.publishing.mizuPublishMavenRepository

class MizuPlugin : Plugin<Project> {
    companion object {
        internal const val NAME = "mizu"
        internal const val GROUP_ID = "wtf.$NAME"

        private const val REPO_URL = "https://maven.mizu.wtf/"
        internal const val REPO_RELEASES_URL = "$REPO_URL/releases"
        internal const val REPO_SNAPSHOTS_URL = "$REPO_URL/snapshots"
        internal const val REPO_PRIVATE_URL = "$REPO_URL/private"
    }

    override fun apply(target: Project) {
        val extension = target.extensions.create(
            NAME, MizuExtension::class.java
        )

        target.afterEvaluate { project ->
            // Adds Mizu's repositories
            project.repository("$NAME-releases", REPO_RELEASES_URL)
            project.repository("$NAME-snapshots", REPO_SNAPSHOTS_URL)

            run {
                val username = project.properties["MIZU_USERNAME"] as? String
                    ?: return@run

                val token = project.properties["MIZU_TOKEN"] as? String
                    ?: return@run

                project.repository(
                    "$NAME-private", REPO_PRIVATE_URL,
                    username, token
                )
            }

            with(extension) {
                arrayOf(
                    Pair(common, "common"),
                    Pair(animations, "animations"),
                    Pair(events, "events"),
                    Pair(settings, "settings"),
                    Pair(clientApi, "clientApi"),
                    Pair(loader, "loader"),
                ).filter { it.first.isNotEmpty() }.forEach {
                    project.dependsOn(GROUP_ID, it.second, it.first)
                }
            }

            // If maven-publish is available, add Mizu's repositories
            project.mizuPublishMavenRepository()
        }
    }
}
