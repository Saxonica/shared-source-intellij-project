# shared-source-intellij-project

Demonstrates issues we're having with debug/run configurations for a shared-source, multi-project, Gradle build.

While running tests with `./gradlew java:he:test` or `./gradlew java:ee:test` works just fine, running a test under the debugger is problematic:

* The IntelliJ module configuration picked up from the Gradle project does not correctly find the source, or test, classes.
* The use of a source preprocessor requires manual tweaking of the module config so that the debugger shows the correct source file (i.e. the original file, not the generated source under `build/`).
* If a module configuration is manually tweaked in the IDE to get the debug/run configurations to work correctly, re-syncing the Gradle project wipes those tweaks out.
* If one of the Gradle subprojects is manually configured so that the debug/run configurations work correctly, those configuration changes have to be erased before one of the other subprojects can be configured otherwise you can't set up the other subprojects because of 'duplicate content root' errors.
* If 'Generate .iml files for gradle projects' is checked, and the content root tweaks required made, the settings will be persisted in a `.iml` file, but that file will be deleted when the gradle project is re-synced.

This project is simplified from the project we're having problems with, where different editions (`ee` and `he` here) have slightly different filtered selections of the source files, different preprocessor options, and different library dependencies.
