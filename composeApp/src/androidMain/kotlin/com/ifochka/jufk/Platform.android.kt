package com.ifochka.jufk

import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp

class AndroidPlatform : Platform {
    override val name: String = Platform.ANDROID
}

actual fun getPlatform(): Platform = AndroidPlatform()

actual fun createHttpClient(): HttpClient = HttpClient(OkHttp)
