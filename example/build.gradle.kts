plugins {
    `java-library`
    id("wtf.mizu.mizu-gradle") version "1.0.7"
    `maven-publish`
}

mizu {

}

publishing {
    publications {
        register("mavenJava", MavenPublication::class) {
            from(components["java"])
        }
    }
}