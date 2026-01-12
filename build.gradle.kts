import com.codingfeline.buildkonfig.compiler.FieldSpec.Type.STRING

plugins {
    // this is necessary to avoid the plugins to be loaded multiple times
    // in each subproject's classloader
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.multiplatformLibrary) apply false
    alias(libs.plugins.composeHotReload) apply false
    alias(libs.plugins.composeMultiplatform) apply false
    alias(libs.plugins.composeCompiler) apply false
    alias(libs.plugins.kotlinMultiplatform) apply false
    alias(libs.plugins.kotlinAndroid) apply false
    alias(libs.plugins.detekt) apply false
    alias(libs.plugins.ktlint) apply false
    alias(libs.plugins.buildkonfig) apply false
}

subprojects {
    apply(plugin = "org.jlleitschuh.gradle.ktlint")
    apply(plugin = "io.gitlab.arturbosch.detekt")

    plugins.withId("com.codingfeline.buildkonfig") {
        configure<com.codingfeline.buildkonfig.gradle.BuildKonfigExtension> {
            packageName = "com.ifochka.jufk"

            val versionName = System.getenv("VERSION_NAME")
                ?: project.findProperty("VERSION_NAME")?.toString()
                ?: "local build"

            defaultConfigs {
                buildConfigField(STRING, "VERSION_NAME", versionName)
            }
        }
    }

    configure<org.jlleitschuh.gradle.ktlint.KtlintExtension> {
        debug.set(true)
        filter {
            exclude { it.file.path.contains("/build/") }
        }
    }

    configure<io.gitlab.arturbosch.detekt.extensions.DetektExtension> {
        buildUponDefaultConfig = true
        config.setFrom(files("$rootDir/detekt.yml"))
    }

    tasks.withType<io.gitlab.arturbosch.detekt.Detekt>().configureEach {
        exclude { it.file.path.contains("/build/") }
    }
}
