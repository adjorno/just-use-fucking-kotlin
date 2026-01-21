package com.ifochka.jufk.youtube

import kotlinx.coroutines.delay

interface YoutubeVideosDataSource {
    suspend fun fetchVideos(playlistId: String): List<YoutubeVideo>
}

class StubYoutubeVideosDataSource : YoutubeVideosDataSource {
    override suspend fun fetchVideos(playlistId: String): List<YoutubeVideo> {
        delay(2000)
        return listOf(
            YoutubeVideo(
                url = "https://www.youtube.com/watch?v=dQw4w9WgXcQ",
                title = "Rick Astley - Never Gonna Give You Up (Official Music Video)",
                thumbnailUrl = null,
            ),
        )
    }
}
