package com.ifochka.jufk

import com.ifochka.jufk.youtube.YoutubeVideo
import io.ktor.client.HttpClient
import io.ktor.client.engine.js.Js

class WasmPlatform : Platform {
    override val name: String = Platform.WASM
}

actual fun getPlatform(): Platform = WasmPlatform()

actual fun createHttpClient(): HttpClient = HttpClient(Js)

actual fun shareContent(url: String, title: String) {
    // TODO: Implement Web Share API
    println("Share on Web: $title - $url")
}

actual fun triggerHaptic(style: HapticStyle) {
    // No haptic feedback on web
}

actual suspend fun saveVideosForWidget(videos: List<YoutubeVideo>) {
    // No widget implementation for Web
}
