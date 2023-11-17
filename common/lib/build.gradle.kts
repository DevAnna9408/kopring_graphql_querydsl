import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    val kotlinVersion = "1.5.21"

    id("org.springframework.boot") version "2.5.4"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
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
    jcenter()
}
//21년 12월 버전
val awsVersion = "1.12.131"

dependencies {
    implementation(project(":common:domain"))
    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("org.springframework:spring-web")
    implementation("org.springframework:spring-webmvc")
    implementation("org.springframework.security:spring-security-core")
    implementation("org.springframework.security:spring-security-web")
    implementation("com.fasterxml.jackson.core:jackson-annotations")
    implementation("javax.servlet:javax.servlet-api")
    implementation("io.jsonwebtoken:jjwt-api:0.11.2")

    runtimeOnly("io.jsonwebtoken:jjwt-impl:0.11.2")
    runtimeOnly("io.jsonwebtoken:jjwt-jackson:0.11.2")
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
    annotationProcessor ("org.projectlombok:lombok")
    testImplementation("org.springframework.boot:spring-boot-starter-test")

    //birt
    implementation("org.eclipse.birt.runtime:org.eclipse.birt.runtime:4.4.2") {
        exclude("org.eclipse.birt.runtime.3_7_1', module: 'org.apache.batik.pdf")
//        exclude("org.eclipse.birt.runtime.4.3.1', module: 'org.apache.poi")
    }
    implementation("org.apache.pdfbox:pdfbox:2.0.7")

    implementation("commons-io:commons-io:2.11.0")
    implementation("org.apache.poi:poi:4.1.2")
    implementation("org.apache.poi:poi-ooxml:4.1.2")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.apache.commons:commons-csv:1.5")
    //aws
    implementation("com.amazonaws:aws-java-sdk-s3:${awsVersion}")
    implementation("com.amazonaws:aws-java-sdk-ses:${awsVersion}")
    implementation("com.amazonaws:aws-encryption-sdk-java:2.0.0")


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
