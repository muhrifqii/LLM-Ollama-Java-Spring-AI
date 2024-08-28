plugins {
  java
}

java {
  sourceCompatibility = JavaVersion.VERSION_22
  toolchain {
    languageVersion.set(JavaLanguageVersion.of(22))
  }
}

configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
}

dependencies {
  implementation(libs.reactor.core)
  compileOnly(libs.lombok)
  annotationProcessor(libs.lombok)
}

tasks.test {
  useJUnitPlatform()
}
