pluginManagement {
  includeBuild("gradle/build-logic")
  repositories {
    gradlePluginPortal()
    mavenCentral()
  }
}

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

rootProject.name = "SlakingSpringBedAI"
// include("llm:impl")
include("llm:ollama-provider")
include("llm:api")
