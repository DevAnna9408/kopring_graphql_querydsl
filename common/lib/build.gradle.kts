import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    val kotlinVersion = "1.6.21"

    id("org.springframework.boot") version "2.7.7"
    id("io.spring.dependency-management") version "1.0.15.RELEASE"
    kotlin("jvm")
    kotlin("plugin.spring") version kotlinVersion

}

group = "kr.co.anna.common"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    mavenCentral()
}

dependencies {
    // Modules
    implementation(project(":common:domain"))

    // Spring Boot
    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework:spring-web")
    implementation("org.springframework:spring-webmvc")
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
    testImplementation("org.springframework.boot:spring-boot-starter-test")

    // Kotlin
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

    // Spring Security
    implementation("org.springframework.security:spring-security-core")
    implementation("org.springframework.security:spring-security-web")

    // jackson
    implementation("com.fasterxml.jackson.core:jackson-annotations")

    //Servlet
    implementation("javax.servlet:javax.servlet-api")

    // JWT
    implementation("io.jsonwebtoken:jjwt-api:0.11.2")
    runtimeOnly("io.jsonwebtoken:jjwt-impl:0.11.2")
    runtimeOnly("io.jsonwebtoken:jjwt-jackson:0.11.2")

}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "11"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.bootJar {
    enabled = false
}

tasks.jar {
    enabled = true
    archiveBaseName.set("studio-world-common-lib")
}
