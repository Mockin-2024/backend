import org.jetbrains.kotlin.codegen.intrinsics.ArrayOf

plugins {
    kotlin("jvm") version "1.9.25"
    kotlin("plugin.spring") version "1.9.25"
    id("org.springframework.boot") version "3.3.4"
    id("io.spring.dependency-management") version "1.1.6"
    id("org.asciidoctor.jvm.convert") version "4.0.3"
    id("jacoco")
}

group = "com.knu"
version = "0.0.1-SNAPSHOT"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}

repositories {
    mavenCentral()
}

jacoco {
    toolVersion = "0.8.12"
    reportsDirectory = layout.buildDirectory.dir("jacoco")
}

dependencies {
    // Kotlin Basic
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    developmentOnly("org.springframework.boot:spring-boot-devtools")

    // Junit test
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")

    // Kotest
    testImplementation("io.kotest:kotest-runner-junit5:5.9.1")
    testImplementation("io.kotest:kotest-assertions-core:5.9.1")
    testImplementation("io.kotest.extensions:kotest-extensions-spring:1.3.0")

    // reactor-test
    testImplementation("io.projectreactor:reactor-test:3.6.11")

    // MockK
    testImplementation("io.mockk:mockk:1.13.12")
    testImplementation("com.ninja-squad:springmockk:4.0.2")

    // Spring WebFLux
    implementation("org.springframework.boot:spring-boot-starter-webflux")

    // Spring logging
    implementation("org.springframework.boot:spring-boot-starter-logging")

    // Spring Rest Docs
    implementation("org.springframework.restdocs:spring-restdocs-webtestclient:3.0.2")

    // Spring R2DBC with MySQL
    implementation("org.springframework.boot:spring-boot-starter-data-r2dbc")
    implementation("io.asyncer:r2dbc-mysql:1.2.0")
    implementation("mysql:mysql-connector-java:8.0.33")
    implementation("com.h2database:h2")

    // Kotlin Coroutine
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactive:1.9.0")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.9.0")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor:1.9.0")

    // Spring AOP
    implementation("org.springframework.boot:spring-boot-starter-aop")

    // Redis
    implementation("org.springframework.boot:spring-boot-starter-data-redis-reactive")

    // Email
    implementation("org.springframework.boot:spring-boot-starter-mail")

    // Security
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("io.jsonwebtoken:jjwt-api:0.11.5")
    runtimeOnly("io.jsonwebtoken:jjwt-impl:0.11.5")
    runtimeOnly("io.jsonwebtoken:jjwt-jackson:0.11.5")
    testImplementation("org.springframework.security:spring-security-test")
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "17"
    }
}

val snippetsDir = file("./build/generated-snippets")

tasks.withType<Test> {
    useJUnitPlatform()
    outputs.dir(snippetsDir)
    finalizedBy(tasks.jacocoTestReport)
}

tasks.jacocoTestReport {
    dependsOn(tasks.test)
    reports {
        xml.required = true
        csv.required = false
        html.outputLocation = layout.buildDirectory.dir("jacocoHtml")
    }
    classDirectories.setFrom(files(classDirectories.files.map {  classDir ->
        fileTree(classDir) {
            exclude("com/knu/mockin/model/**")
            exclude("com/knu/mockin/config/**")
            exclude("com/knu/mockin/kisclient/**")
            exclude("com/knu/mockin/util/**")
            exclude("com/knu/mockin/MockinApplicationKt.class")
        }
    }))
}

tasks.jacocoTestCoverageVerification {
    dependsOn(tasks.jacocoTestReport)
    violationRules {
        rule {
            enabled = false
            element = "CLASS"

            limit {
                counter = "LINE"
                value = "COVEREDRATIO"
                minimum = "0.8".toBigDecimal()
            }
        }
    }
}

tasks.asciidoctor {
    dependsOn(tasks.jacocoTestCoverageVerification)
    finalizedBy("copyDocument")

    inputs.dir(snippetsDir)
}

tasks.register<Copy>("copyDocument") {
    dependsOn("asciidoctor")

    from(file("./build/docs/asciidoc/"))
    into(file("./src/main/resources/static/docs"))
}

