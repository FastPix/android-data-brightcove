pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven {
            url = uri("https://repo.brightcove.com/releases")
        }
        maven {
            url = uri("https://maven.pkg.github.com/FastPix/android-data-androidXmedia3")
            credentials {
                username = "your_github_username"
                password = "your_github_token"
            }
        }
    }
}

rootProject.name = "Brightcove"
include(":app")
include(":brightcove")
