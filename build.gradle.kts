plugins {
    java
    id("org.springframework.boot") version "3.2.4"
    id("io.spring.dependency-management") version "1.1.4"
}

group = "com.elmenus"
version = "0.0.1-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_17
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

ext {
    set("testcontainers.version", "1.19.3")
}

repositories {
    mavenCentral()
}

tasks.withType<Copy> {
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
}

sourceSets {
    create("behavioralTest") {
        java.srcDir("src/behavioralTest/java")
        resources.srcDir("src/behavioralTest/resources")
        runtimeClasspath += sourceSets["main"].runtimeClasspath + sourceSets["test"].runtimeClasspath
        compileClasspath += sourceSets["main"].compileClasspath + sourceSets["test"].compileClasspath
    }
}


dependencies {

    // framework dependencies
    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("org.springframework.boot:spring-boot-starter-data-r2dbc")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-validation")

    // testing dependencies
    testImplementation("io.projectreactor:reactor-test")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.security:spring-security-test")
    testImplementation("io.cucumber:cucumber-junit:7.17.0")
    testImplementation("org.testcontainers:junit-jupiter")
    testImplementation("org.testcontainers:postgresql")
    testImplementation("org.testcontainers:r2dbc")
    testImplementation("io.rest-assured:rest-assured:5.4.0")
    testImplementation("io.rest-assured:rest-assured-common:5.4.0")
    testImplementation("io.rest-assured:xml-path:5.4.0")
    testImplementation("io.rest-assured:json-path:5.4.0")


    // tooling
    implementation("org.mapstruct:mapstruct:1.5.5.Final")
    implementation("org.projectlombok:lombok")
    implementation("software.amazon.awssdk:s3:2.18.41")
    implementation("software.amazon.awssdk:netty-nio-client:2.18.41")
    implementation("io.cucumber:cucumber-java:7.17.0")
    implementation("io.cucumber:cucumber-spring:7.17.0")


    annotationProcessor("org.mapstruct:mapstruct-processor:1.5.5.Final")
    annotationProcessor("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok-mapstruct-binding:0.2.0")

    // security
    implementation("com.auth0:java-jwt:4.4.0")

    // database
    runtimeOnly("org.postgresql:postgresql")
    runtimeOnly("org.postgresql:r2dbc-postgresql")

}

tasks.withType<Test> {
    useJUnitPlatform()
}

