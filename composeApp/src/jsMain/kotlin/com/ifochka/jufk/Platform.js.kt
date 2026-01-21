package com.ifochka.jufk

import io.ktor.client.HttpClient
import io.ktor.client.engine.js.Js

class JsPlatform : Platform {
    override val name: String = "Web with Kotlin/JS"
}

actual fun getPlatform(): Platform = JsPlatform()

actual fun createHttpClient(): HttpClient = HttpClient(Js)
