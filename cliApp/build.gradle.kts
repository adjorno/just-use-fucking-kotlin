plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.buildkonfig)
}

kotlin {
    listOf(
        macosArm64(),
        macosX64(),
        linuxX64(),
        mingwX64(),
    ).forEach { target ->
        target.binaries {
            executable {
                entryPoint = "com.ifochka.jufk.main"
                baseName = "jufk"
            }
        }
    }
}
