# gradle-plugin

A gradle plugin designed for Mizu-related projects.

## Installation

To install the plugin, add the following to your settings.gradle.kts:
```kotlin
pluginManagement {
    repositories {
        gradlePluginPortal()
        maven("https://maven.mizu.wtf/releases")
    }
}
```
This will allow Gradle to search for the plugin in our maven repository.  
Then, add the following to your build.gradle.kts:

```kotlin
plugins {
    `java-library`
    id("wtf.mizu.gradle-plugin") version "1.0.x"
}
```

## Usage

The plugin will automatically configure the maven repository for both dependencies and publishing.
The main goal of the plugin is to provide a way to easily use our libraries. Consider this example:
```kotlin
mizu {
    // This will add the common to your libraries
    common()
    // ... also works for animations and events
}
```

You can pass a value to the functions to configure the versions of each library:

```kotlin
mizu {
    // This will add the common version 1.0.7 to your libraries
    common("1.0.7")
}
```

## Maintainers:

- [Shyrogan](https://github.com/Shyrogan)
- [xtrm](https://github.com/xtrm-en)
