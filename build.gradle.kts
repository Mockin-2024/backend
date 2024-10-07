plugins {
    kotlin("jvm") version "1.9.25"
    kotlin("plugin.spring") version "1.9.25"
    id("org.springframework.boot") version "3.3.4"
    id("io.spring.dependency-management") version "1.1.6"
    id("org.asciidoctor.jvm.convert") version "4.0.3"
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

    // MockK
    testImplementation("io.mockk:mockk:1.13.12")
    testImplementation("com.ninja-squad:springmockk:4.0.2")

    // Spring Web with FLux
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-webflux")

    // Spring logging
    implementation("org.springframework.boot:spring-boot-starter-logging")

    // Spring Rest Docs
    testImplementation("org.springframework.restdocs:spring-restdocs-mockmvc")

    // Spring R2DBC with MySQL
    implementation("org.springframework.boot:spring-boot-starter-data-r2dbc")
    implementation("io.asyncer:r2dbc-mysql:1.2.0")
    runtimeOnly("mysql:mysql-connector-java:8.0.33")
    runtimeOnly("com.h2database:h2")

    // Kotlin Coroutine
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactive:1.9.0")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.9.0")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor:1.9.0")

    // Spring AOP
    implementation("org.springframework.boot:spring-boot-starter-aop")
}

kotlin {
    compilerOptions {
        freeCompilerArgs.addAll("-Xjsr305=strict")
    }
}

val snippetsDir by extra { file("build/generated-snippets") }

//tasks.withType<Test> {
//    useJUnitPlatform()
//}

// Ascii Doc Create Tasks
tasks {
    // Test 결과를 snippet Directory에 출력
    test {
        outputs.dir(snippetsDir)
        useJUnitPlatform()
    }

    asciidoctor {
        // test 가 성공해야만, 아래 Task 실행
        dependsOn(test)

//        // 기존에 존재하는 Docs 삭제(문서 최신화를 위해)
//        doFirst {
//            delete(file("src/main/resources/static/docs"))
//        }

        // snippet Directory 설정
        inputs.dir(snippetsDir)

        // Ascii Doc 파일 생성
        doLast {
            copy {
                from("build/docs/asciidoc")
                into("src/main/resources/static/docs")
            }
        }
    }

    build {
        // Ascii Doc 파일 생성이 성공해야만, Build 진행
        dependsOn(asciidoctor)
    }
}