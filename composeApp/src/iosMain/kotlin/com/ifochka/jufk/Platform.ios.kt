package com.ifochka.jufk

import io.ktor.client.HttpClient
import io.ktor.client.engine.darwin.Darwin

class IOSPlatform : Platform {
    override val name: String = Platform.IOS
}

actual fun getPlatform(): Platform = IOSPlatform()

actual fun createHttpClient(): HttpClient = HttpClient(Darwin)
