plugins {
  java
  alias(libs.plugins.spring.boot)
  alias(libs.plugins.spring.dependencies)
}

java {
  sourceCompatibility = JavaVersion.VERSION_22
}

configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
}

dependencies {
  implementation(projects.llm.api)

  implementation("org.springframework.boot:spring-boot-starter-webflux")
  implementation(platform(libs.spring.ai.bom))
	implementation(libs.spring.ai.ollama)
  compileOnly(libs.lombok)

	testImplementation("org.springframework.boot:spring-boot-starter-test")
}

tasks.test {
  useJUnitPlatform()
}
