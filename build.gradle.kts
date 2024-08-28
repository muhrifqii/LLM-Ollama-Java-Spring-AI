plugins {
	java
  alias(libs.plugins.spring.boot) apply false
  alias(libs.plugins.spring.dependencies) apply false
  alias(libs.plugins.graalvm) apply false
}

allprojects {
  repositories {
    mavenCentral()
    maven(url = "https://repo.spring.io/milestone")
    maven(url = "https://repo.spring.io/snapshot")
  }

  tasks.withType<Test> {
    useJUnitPlatform()
  }
}
