package scripts

private object Default {
    const val BUILD_TYPE = "debug"
    const val BUILD_FLAVOR = ""

    val BUILD_VARIANT = "${BUILD_FLAVOR.capitalize()}${BUILD_TYPE.capitalize()}"
}

val jacocoReport by tasks.registering(JacocoReport::class) {
    group = "Quality"
    description = "Report code coverage on tests within the Android codebase."
    dependsOn("test${Default.BUILD_VARIANT}UnitTest")

    val buildVariantClassPath = "${Default.BUILD_FLAVOR}${Default.BUILD_TYPE.capitalize()}"

    reports {
        xml.required.set(true)
    }

    classDirectories.setFrom(fileTree(project.buildDir) {
        include(
            "**/classes/**/main/**",
            "**/intermediates/classes/$buildVariantClassPath/**",
            "**/intermediates/javac/$buildVariantClassPath/*/classes/**",
            "**/tmp/kotlin-classes/$buildVariantClassPath/**")
        exclude(
            "**/R.class",
            "**/R\$*.class",
            "**/BuildConfig.*",
            "**/Manifest*.*",
            "**/Manifest$*.class",
            "**/*Test*.*",
            "**/Injector.*",
            "android/**/*.*",
            "**/*\$Lambda$*.*",
            "**/*\$inlined$*.*",
            "**/di/*.*",
            "**/*Database.*",
            "**/*Response.*",
            "**/*Application.*",
            "**/*Entity.*")
    }
    )
    sourceDirectories.setFrom(fileTree(project.projectDir) {
        include("src/main/java/**", "src/main/kotlin/**") })
    executionData.setFrom(fileTree(project.buildDir) {
        include("**/*.exec", "**/*.ec") })
}

tasks.register("runTestCoverage") {
    group = "Quality"
    description = "Report code coverage on tests within the Android codebase."
    dependsOn(jacocoReport)
}