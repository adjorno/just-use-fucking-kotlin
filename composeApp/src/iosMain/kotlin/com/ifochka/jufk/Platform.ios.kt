package com.ifochka.jufk

import com.ifochka.jufk.youtube.YoutubeVideo
import com.ifochka.jufk.youtube.toWidgetModel
import io.ktor.client.HttpClient
import io.ktor.client.engine.darwin.Darwin
import io.ktor.client.request.get
import io.ktor.client.statement.readBytes
import io.ktor.client.statement.readRawBytes
import kotlinx.cinterop.BetaInteropApi
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.addressOf
import kotlinx.cinterop.usePinned
import kotlinx.serialization.json.Json
import platform.Foundation.NSData
import platform.Foundation.NSFileManager
import platform.Foundation.NSURL
import platform.Foundation.NSUserDefaults
import platform.Foundation.create
import platform.Foundation.writeToFile
import platform.UIKit.UIActivityViewController
import platform.UIKit.UIApplication
import platform.UIKit.UIImpactFeedbackGenerator
import platform.UIKit.UIImpactFeedbackStyle
import platform.UIKit.UINotificationFeedbackGenerator
import platform.UIKit.UINotificationFeedbackType

class IOSPlatform : Platform {
    override val name: String = Platform.IOS
}

actual fun getPlatform(): Platform = IOSPlatform()

actual fun createHttpClient(): HttpClient = HttpClient(Darwin)

actual fun shareContent(
    url: String,
    title: String,
) {
    val currentViewController = UIApplication.sharedApplication.keyWindow?.rootViewController
    val activityItems = listOf(title, NSURL.URLWithString(url))
    val activityViewController = UIActivityViewController(
        activityItems = activityItems,
        applicationActivities = null,
    )
    currentViewController?.presentViewController(
        activityViewController,
        animated = true,
        completion = null,
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

actual suspend fun saveVideosForWidget(videos: List<YoutubeVideo>) {
    println("📱 Saving ${videos.size} videos for widget")

    // Use proper App Groups initialization
    val sharedDefaults = NSUserDefaults(suiteName = "group.com.ifochka.jufk.widgets")

    // Get App Groups shared container URL
    val fileManager = NSFileManager.defaultManager
    val containerURL = fileManager.containerURLForSecurityApplicationGroupIdentifier("group.com.ifochka.jufk.widgets")

    if (containerURL == null) {
        println("❌ Failed to get App Groups container URL")
        return
    }

    // Take the last 4 videos (most recent) and reverse to show newest first
    val widgetVideos = videos.takeLast(4).reversed()

    println("📱 Downloading ${widgetVideos.size} thumbnails asynchronously...")

    // Create HTTP client for downloading images
    val httpClient = createHttpClient()

    // Download and save thumbnail images asynchronously
    widgetVideos.forEach { video ->
        video.thumbnailUrl?.let { urlString ->
            val videoId = video.url.substringAfter("v=").substringBefore("&")
            val imageFileName = "$videoId.jpg"
            val imagePath = containerURL.path + "/" + imageFileName

            try {
                // Download image asynchronously using Ktor
                val imageBytes = httpClient.get(urlString).readRawBytes()
                val imageData = imageBytes.toNSData()

                val saved = imageData.writeToFile(imagePath, atomically = true)
                if (saved) {
                    println("📱 ✓ Saved thumbnail: $imageFileName")
                } else {
                    println("❌ Failed to save thumbnail: $imageFileName")
                }
            } catch (e: Exception) {
                println("❌ Failed to download thumbnail from: $urlString - ${e.message}")
            }
        }
    }

    // Save video data with local image paths
    val widgetModels = widgetVideos.map { it.toWidgetModel() }
    val json = Json.encodeToString(widgetModels)
    println("📱 JSON (showing ${widgetModels.size} videos): ${widgetModels.joinToString { it.title }}")

    // Clear old data first
    sharedDefaults.removeObjectForKey("youtubeVideos")

    // Save new data
    sharedDefaults.setObject(json as Any, forKey = "youtubeVideos")
    val synced = sharedDefaults.synchronize()
    println("📱 Synchronize result: $synced")

    // Verify it was saved
    val readBack = sharedDefaults.stringForKey("youtubeVideos")
    if (readBack != null) {
        println("📱 Verified: Data saved successfully, length: ${readBack.length}")
        println("📱 First video in saved data: ${readBack.substringAfter("\"title\":\"").substringBefore("\"")}")
    } else {
        println("❌ Verification failed: Could not read back data")
    }
}

// Helper to convert ByteArray to NSData
@OptIn(ExperimentalForeignApi::class, BetaInteropApi::class)
private fun ByteArray.toNSData(): NSData =
    this.usePinned { pinned ->
        NSData.create(bytes = pinned.addressOf(0), length = this.size.toULong())
    }
