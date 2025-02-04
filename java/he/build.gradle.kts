import com.saxonica.build.java.PreprocessSourcesTask

plugins {
    id("java")
    id("idea")
}

group = "com.saxonica.bugs"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

val preprocessor by configurations.creating {
    extendsFrom(configurations["runtimeOnly"])
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")

    preprocessor("com.igormaznitsa:jcp:7.0.4")
}

val preprocessJava = tasks.register<PreprocessSourcesTask>("preprocessJava") {
    classpath.from(configurations.named("preprocessor"))
    sources = layout.projectDirectory.dir("../src/main/java")
    output = layout.buildDirectory.dir("src/main/java")
    bogus = false
}

sourceSets {
    main {
        java {
            srcDir(preprocessJava)
        }
    }

    test {
        java {
            srcDir(layout.projectDirectory.dir("../../src/test/java"))
        }
    }
}

idea {
    module {
        sourceDirs.add(layout.projectDirectory.file("../../src/main/java").asFile)
        //and some extra test source dirs
        testSources.from(layout.projectDirectory.file("../../src/test/java"))
    }
}

tasks.test {
    useJUnitPlatform()
}
