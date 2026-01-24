package com.ifochka.jufk

import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO

class JVMPlatform : Platform {
    override val name: String = Platform.DESKTOP
}

actual fun getPlatform(): Platform = JVMPlatform()

actual fun createHttpClient(): HttpClient = HttpClient(CIO)
