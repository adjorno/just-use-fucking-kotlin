package com.ifochka.jufk.youtube

interface YoutubeVideosDataSource {
    suspend fun fetchVideos(playlistId: String): List<YoutubeVideo>
}
