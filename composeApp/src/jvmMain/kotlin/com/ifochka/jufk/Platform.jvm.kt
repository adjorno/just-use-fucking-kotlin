package com.ifochka.jufk

import com.ifochka.jufk.youtube.YoutubeVideo
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO

class JVMPlatform : Platform {
    override val name: String = Platform.DESKTOP
}

actual fun getPlatform(): Platform = JVMPlatform()

actual fun createHttpClient(): HttpClient = HttpClient(CIO)

actual fun shareContent(
    url: String,
    title: String,
) {
    // Desktop: Could open default browser or clipboard
    println("Share on Desktop: $title - $url")
}

actual fun triggerHaptic(style: HapticStyle) {
    // No haptic feedback on desktop
}

actual suspend fun saveVideosForWidget(videos: List<YoutubeVideo>) {
    // No widget implementation for Desktop
}
