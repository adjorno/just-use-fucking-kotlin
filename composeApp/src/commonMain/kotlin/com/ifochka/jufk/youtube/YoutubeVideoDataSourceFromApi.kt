package com.ifochka.jufk.youtube

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.statement.bodyAsText
import kotlinx.serialization.json.Json

class YoutubeVideoDataSourceFromApi(
    private val apiKey: String,
    private val httpClient: HttpClient,
) : YoutubeVideosDataSource {
    override suspend fun fetchVideos(playlistId: String): List<YoutubeVideo> {
        val playlistResponse = httpClient.get("https://www.googleapis.com/youtube/v3/playlistItems") {
            parameter("part", "snippet")
            parameter("playlistId", playlistId)
            parameter("key", apiKey)
            parameter("maxResults", MAX_VIDEOS)
        }
        val playlistText = playlistResponse.bodyAsText()
        val playlist = json.decodeFromString<YoutubePlaylistResponse>(playlistText)

        return playlist.items.map {
            YoutubeVideo(
                title = it.snippet.title,
                thumbnailUrl = it.snippet.thumbnails.maxres?.url
                    ?: it.snippet.thumbnails.high?.url
                    ?: it.snippet.thumbnails.medium?.url
                    ?: it.snippet.thumbnails.default?.url,
                url = "https://www.youtube.com/watch?v=${it.snippet.resourceId.videoId}",
            )
        }
    }

    companion object {
        private const val MAX_VIDEOS = 50

        val json = Json {
            ignoreUnknownKeys = true
            isLenient = true
        }
    }
}
