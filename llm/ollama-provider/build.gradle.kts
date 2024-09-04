plugins {
  java
  alias(libs.plugins.spring.boot)
  alias(libs.plugins.graalvm)
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
  implementation(projects.llm.api)

  implementation(libs.spring.boot.web)
  implementation(libs.spring.boot.webflux)
  implementation(libs.spring.boot.r2dbc)

  implementation(libs.flyway.core)
  implementation(libs.flyway.postgresql)

  runtimeOnly(libs.postgresql)
  runtimeOnly(libs.postgresql.r2dbc)

  compileOnly(libs.lombok)
  annotationProcessor(libs.lombok)

  implementation(platform(libs.spring.ai.bom))
	implementation(libs.spring.ai.ollama)

  implementation(libs.micrometer.tracing.otel)
  implementation(libs.otel.exporter)
  implementation(libs.micrometer.registry)

  implementation(libs.uuid)
  implementation(libs.jackson.jsr310)

  testAndDevelopmentOnly(libs.spring.boot.devtools)

	testImplementation(libs.spring.boot.test)
  testImplementation(libs.reactor.test)
}

tasks.test {
  useJUnitPlatform()
}
