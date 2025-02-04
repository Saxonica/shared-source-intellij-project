package com.saxonica.build.java

import org.gradle.api.DefaultTask
import org.gradle.api.file.ConfigurableFileCollection
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.file.ProjectLayout
import org.gradle.api.provider.Property
import org.gradle.api.tasks.*
import org.gradle.kotlin.dsl.assign
import javax.inject.Inject

abstract class PreprocessSourcesTask @Inject constructor(private val layout: ProjectLayout) : DefaultTask() {
    @get:InputFiles
    abstract val classpath: ConfigurableFileCollection

    @get:InputDirectory
    abstract val sources: DirectoryProperty

    @get:OutputDirectory
    abstract val output: DirectoryProperty

    @get:Input
    abstract val bogus: Property<Boolean>

    @TaskAction
    fun preprocess() {
        val cls = classpath
        println("Preprocessing ${sources.get().asFile}")
        project.javaexec {
            classpath = cls
            mainClass = "com.igormaznitsa.jcp.JcpPreprocessor"
            args("/c",
                "/i:${sources.get()}",
                "/o:${layout.buildDirectory.dir("src/main/java").get()}",
                "/p:BOGUS=${bogus.get()}")
        }

    }
}