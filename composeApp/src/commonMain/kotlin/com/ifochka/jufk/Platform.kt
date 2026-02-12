package com.ifochka.jufk

import io.ktor.client.HttpClient

interface Platform {
    val name: String

    companion object {
        const val IOS = "ios"
        const val ANDROID = "android"
        const val JS = "web/js"
        const val WASM = "web/wasm"
        const val DESKTOP = "desktop"
        const val CLI = "cli"
    }
}

expect fun getPlatform(): Platform

expect fun createHttpClient(): HttpClient
