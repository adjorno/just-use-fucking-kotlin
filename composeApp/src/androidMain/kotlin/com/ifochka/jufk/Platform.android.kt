package com.ifochka.jufk

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
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
    val sendIntent: Intent = Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(Intent.EXTRA_TEXT, "$title\n$url")
        type = "text/plain"
    }
    // FLAG_ACTIVITY_NEW_TASK is required when starting an Activity from a non-Activity context.
    val shareIntent = Intent.createChooser(sendIntent, null)
    AppContext.applicationContext.startActivity(shareIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK))
}

actual fun triggerHaptic(style: HapticStyle) {
    val vibrator = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        val vibratorManager = AppContext.applicationContext.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as android.os.VibratorManager
        vibratorManager.defaultVibrator
    } else {
        @Suppress("DEPRECATION")
        AppContext.applicationContext.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
    }

    if (vibrator.hasVibrator()) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val effect = when (style) {
                HapticStyle.LIGHT -> VibrationEffect.createOneShot(50, VibrationEffect.DEFAULT_AMPLITUDE)
                HapticStyle.MEDIUM -> VibrationEffect.createOneShot(100, VibrationEffect.DEFAULT_AMPLITUDE)
                HapticStyle.HEAVY -> VibrationEffect.createOneShot(150, VibrationEffect.DEFAULT_AMPLITUDE)
                HapticStyle.SUCCESS -> VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE)
                HapticStyle.WARNING -> VibrationEffect.createOneShot(100, VibrationEffect.DEFAULT_AMPLITUDE) // Using medium as fallback
                HapticStyle.ERROR -> VibrationEffect.createOneShot(150, VibrationEffect.DEFAULT_AMPLITUDE) // Using heavy as fallback
            }
            vibrator.vibrate(effect)
        } else {
            @Suppress("DEPRECATION")
            vibrator.vibrate(when (style) {
                HapticStyle.LIGHT -> 50L
                HapticStyle.MEDIUM -> 100L
                HapticStyle.HEAVY -> 150L
                HapticStyle.SUCCESS -> 200L
                HapticStyle.WARNING -> 100L
                HapticStyle.ERROR -> 150L
            })
        }
    }
}

actual suspend fun saveVideosForWidget(videos: List<YoutubeVideo>) {
    // No widget implementation for Android
}
