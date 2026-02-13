package com.ifochka.jufk

import com.ifochka.jufk.youtube.YoutubeVideo
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp

class AndroidPlatform : Platform {
    override val name: String = Platform.ANDROID
}

actual fun getPlatform(): Platform = AndroidPlatform()

actual fun createHttpClient(): HttpClient = HttpClient(OkHttp)

actual fun shareContent(
    url: String,
    title: String,
) {
    // TODO: Implement Android share intent
    println("Share on Android: $title - $url")
}

actual fun triggerHaptic(style: HapticStyle) {
    // TODO: Implement Android vibration/haptic feedback
}

actual suspend fun saveVideosForWidget(videos: List<YoutubeVideo>) {
    // No widget implementation for Android
}
