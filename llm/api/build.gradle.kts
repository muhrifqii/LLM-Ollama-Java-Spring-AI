plugins {
  java
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
  implementation("io.projectreactor:reactor-core")
  compileOnly(libs.lombok)
}

tasks.test {
  useJUnitPlatform()
}
