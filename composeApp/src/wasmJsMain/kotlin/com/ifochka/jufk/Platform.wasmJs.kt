package com.ifochka.jufk

import io.ktor.client.HttpClient
import io.ktor.client.engine.js.Js

class WasmPlatform : Platform {
    override val name: String = "Web with Kotlin/Wasm"
}

actual fun getPlatform(): Platform = WasmPlatform()
actual fun createHttpClient(): HttpClient = HttpClient(Js)
