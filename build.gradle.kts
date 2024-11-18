import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    java
    idea
    `java-library`
    `maven-publish`
    id("org.jetbrains.kotlin.jvm") version "2.0.21" apply false
    id("com.github.johnrengelman.shadow") version "7.1.2"
}

subprojects {
    apply(plugin = "org.jetbrains.kotlin.jvm")
    apply(plugin = "maven-publish")
    apply(plugin = "idea")
    apply(plugin = "java")
    apply(plugin = "java-library")
    apply(plugin = "com.github.johnrengelman.shadow")


    repositories {
        mavenLocal()
        maven("https://maven.wcpe.top/repository/maven-public/")
        maven("https://jitpack.io")
        maven("https://libraries.minecraft.net")
        maven("https://repo1.maven.org/maven2")
        maven("https://maven.aliyun.com/repository/central")
        maven("https://repo.codemc.io/repository/nms/")
        maven("https://libraries.minecraft.net")
        maven("https://maven.aliyun.com/repository/central")
        mavenCentral()
    }

    dependencies {
        compileOnly(kotlin("stdlib"))
        compileOnly("com.google.guava:guava:21.0")
        compileOnly("com.sk89q.worldedit:worldedit-core:6.1.3-SNAPSHOT")


        compileOnly("org.projectlombok:lombok:1.18.24")
        annotationProcessor("org.projectlombok:lombok:1.18.24")
        testCompileOnly("org.projectlombok:lombok:1.18.24")
        testAnnotationProcessor("org.projectlombok:lombok:1.18.24")

        testImplementation("com.google.guava:guava:21.0")
        testImplementation(platform("org.junit:junit-bom:5.9.1"))
        testImplementation("org.junit.jupiter:junit-jupiter")
    }

    java {
        withSourcesJar()
    }

    tasks.withType<JavaCompile> {
        options.encoding = "UTF-8"
        options.compilerArgs.addAll(listOf("-XDenableSunApiLintControl"))
    }

    tasks.withType<KotlinCompile> {
        kotlinOptions {
            jvmTarget = "1.8"
            freeCompilerArgs = listOf("-Xjvm-default=all")
        }
    }

    configure<JavaPluginConvention> {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    tasks {
        processResources {
            filesMatching(listOf("plugin.yml", "bungee.yml", "plugin.properties")) {
                expand(project.properties)
            }
            duplicatesStrategy = DuplicatesStrategy.EXCLUDE
        }
        named<ShadowJar>("shadowJar") {
            archiveFileName.set("${rootProject.name}-${project.name}-${project.version}.jar")
        }
        build {
            dependsOn("shadowJar")
        }
    }


}