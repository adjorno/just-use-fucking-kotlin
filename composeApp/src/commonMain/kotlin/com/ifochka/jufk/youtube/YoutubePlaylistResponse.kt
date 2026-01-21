package com.ifochka.jufk.youtube

import kotlinx.serialization.Serializable

@Serializable
data class ThumbnailInfo(
    val url: String,
    val width: Int,
    val height: Int,
)

@Serializable
data class Thumbnails(
    val default: ThumbnailInfo?,
    val medium: ThumbnailInfo?,
    val high: ThumbnailInfo?,
    val standard: ThumbnailInfo?,
    val maxres: ThumbnailInfo?,
)

@Serializable
data class ResourceId(
    val kind: String,
    val videoId: String,
)

@Serializable
data class Snippet(
    val publishedAt: String,
    val channelId: String,
    val title: String,
    val description: String,
    val thumbnails: Thumbnails,
    val channelTitle: String,
    val playlistId: String,
    val position: Int,
    val resourceId: ResourceId,
    val videoOwnerChannelTitle: String,
    val videoOwnerChannelId: String,
)

@Serializable
data class Items(
    val kind: String,
    val etag: String,
    val id: String,
    val snippet: Snippet,
)

@Serializable
data class PageInfo(
    val totalResults: Int,
    val resultsPerPage: Int,
)

@Serializable
data class YoutubePlaylistResponse(
    val kind: String,
    val etag: String,
    val items: List<Items>,
    val pageInfo: PageInfo,
)
