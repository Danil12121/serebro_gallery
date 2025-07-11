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
    }
}

rootProject.name = "serebro_gallery"
include(":app")
include(":feature:form_filling_screen")
include(":core:ui")
include(":feature:project_information")
include(":core:common")
include(":core:data")
include(":feature:exhibition")
include(":feature:user_profile")
