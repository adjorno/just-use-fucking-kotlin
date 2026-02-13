package com.ifochka.jufk.youtube

import kotlinx.serialization.Serializable

@Serializable
data class YoutubeVideoWidget(
    val title: String,
    val url: String,
    val thumbnailUrl: String?,
    val videoId: String,
)

// Extension function for conversion
fun YoutubeVideo.toWidgetModel(): YoutubeVideoWidget {
    val videoId = url.substringAfterLast("v=").substringBefore("&")
    return YoutubeVideoWidget(
        title = title,
        url = url,
        thumbnailUrl = thumbnailUrl,
        videoId = videoId,
    )
}
