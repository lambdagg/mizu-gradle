package wtf.mizu.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project
import wtf.mizu.plugin.dependency.dependsOn
import wtf.mizu.plugin.dependency.repository
import wtf.mizu.plugin.extension.MizuExtension
import wtf.mizu.plugin.publishing.mizuPublishMavenRepository

class MizuPlugin: Plugin<Project> {
    companion object {
        internal const val NAME = "mizu"
        internal const val GROUP_ID = "wtf.$NAME"

        internal const val REPO_URL = "https://maven.mizu.wtf/"
        internal const val REPO_RELEASES_URL = "$REPO_URL/releases"
        internal const val REPO_SNAPSHOTS_URL = "$REPO_URL/snapshots"
        internal const val REPO_PRIVATE_URL = "$REPO_URL/private"
    }

    override fun apply(target: Project) {
        val extension = target.extensions.create(NAME, MizuExtension::class.java)

        target.afterEvaluate { project ->
            // Adds Mizu's repositories
            project.repository("$NAME-releases", REPO_RELEASES_URL)
            project.repository("$NAME-snapshots", REPO_SNAPSHOTS_URL)

            if(project.properties.containsKey("MIZU_USERNAME") && project.properties.containsKey("MIZU_TOKEN")) {
                val username = project.properties["MIZU_USERNAME"] as String
                val token = project.properties["MIZU_TOKEN"] as String
                project.repository(
                    "$NAME-private", REPO_PRIVATE_URL,
                    username, token
                )
            }

            // Adds Mizu's required libraries
            if(extension.common.isNotEmpty())
                project.dependsOn(GROUP_ID, "common", extension.common)
            if(extension.animations.isNotEmpty())
                project.dependsOn(GROUP_ID, "animations", extension.animations)
            if(extension.events.isNotEmpty()) {
                project.dependsOn(GROUP_ID, "events-api", extension.events)
                project.dependsOn(GROUP_ID, "events-${extension.eventsImpl.id}", extension.events)
            }
            if(extension.settings.isNotEmpty())
                project.dependsOn(GROUP_ID, "settings", extension.settings)
            if(extension.clientApi.isNotEmpty())
                project.dependsOn(GROUP_ID, "client-api", extension.clientApi)
            if(extension.loader.isNotEmpty())
                project.dependsOn(GROUP_ID, "loader", extension.loader)

            // If maven-publish is available, add Mizu's repositories
            project.mizuPublishMavenRepository()
        }
    }

}