package com.ifochka.jufk

import io.ktor.client.HttpClient
import io.ktor.client.engine.darwin.Darwin
import platform.Foundation.NSURL
import platform.UIKit.*

class IOSPlatform : Platform {
    override val name: String = Platform.IOS
}

actual fun getPlatform(): Platform = IOSPlatform()

actual fun createHttpClient(): HttpClient = HttpClient(Darwin)

actual fun shareContent(url: String, title: String) {
    val currentViewController = UIApplication.sharedApplication.keyWindow?.rootViewController
    val activityItems = listOf(title, NSURL.URLWithString(url))
    val activityViewController = UIActivityViewController(
        activityItems = activityItems,
        applicationActivities = null
    )
    currentViewController?.presentViewController(
        activityViewController,
        animated = true,
        completion = null
    )
}

actual fun triggerHaptic(style: HapticStyle) {
    when (style) {
        HapticStyle.LIGHT -> {
            val generator = UIImpactFeedbackGenerator(UIImpactFeedbackStyle.UIImpactFeedbackStyleLight)
            generator.prepare()
            generator.impactOccurred()
        }
        HapticStyle.MEDIUM -> {
            val generator = UIImpactFeedbackGenerator(UIImpactFeedbackStyle.UIImpactFeedbackStyleMedium)
            generator.prepare()
            generator.impactOccurred()
        }
        HapticStyle.HEAVY -> {
            val generator = UIImpactFeedbackGenerator(UIImpactFeedbackStyle.UIImpactFeedbackStyleHeavy)
            generator.prepare()
            generator.impactOccurred()
        }
        HapticStyle.SUCCESS -> {
            val generator = UINotificationFeedbackGenerator()
            generator.prepare()
            generator.notificationOccurred(UINotificationFeedbackType.UINotificationFeedbackTypeSuccess)
        }
        HapticStyle.WARNING -> {
            val generator = UINotificationFeedbackGenerator()
            generator.prepare()
            generator.notificationOccurred(UINotificationFeedbackType.UINotificationFeedbackTypeWarning)
        }
        HapticStyle.ERROR -> {
            val generator = UINotificationFeedbackGenerator()
            generator.prepare()
            generator.notificationOccurred(UINotificationFeedbackType.UINotificationFeedbackTypeError)
        }
    }
}
