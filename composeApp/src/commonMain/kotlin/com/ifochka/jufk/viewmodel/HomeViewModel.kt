package com.ifochka.jufk.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ifochka.jufk.BuildKonfig
import com.ifochka.jufk.Platform
import com.ifochka.jufk.createHttpClient
import com.ifochka.jufk.data.Content
import com.ifochka.jufk.data.InspirationLink
import com.ifochka.jufk.data.PlatformSection
import com.ifochka.jufk.data.SocialLink
import com.ifochka.jufk.getPlatform
import com.ifochka.jufk.saveVideosForWidget
import com.ifochka.jufk.youtube.YoutubeVideo
import com.ifochka.jufk.youtube.YoutubeVideoDataSourceFromApi
import kotlinx.coroutines.launch

/**
 * UI state for the home screen.
 */
data class HomeUiState(
    val heroTitle: String = Content.HERO_TITLE,
    val heroSubtitle: String = Content.HERO_SUBTITLE,
    val platformSections: List<PlatformSection> = Content.platformSections,
    val socialLinks: List<SocialLink> = Content.socialLinks,
    val footerAuthor: String = Content.FOOTER_AUTHOR,
    val videos: List<YoutubeVideo> = emptyList(),
    val isLoadingVideos: Boolean = true,
    val inspirationText: String = Content.INSPIRATION_TEXT,
    val inspirationLinks: List<InspirationLink> = Content.inspirationLinks,
    val inspirationSuffix: String = Content.INSPIRATION_SUFFIX,
)

/**
 * ViewModel for the home screen.
 */
class HomeViewModel : ViewModel() {
    private val youtubeDataSource = YoutubeVideoDataSourceFromApi(
        apiKey = BuildKonfig.YOUTUBE_API_KEY,
        httpClient = createHttpClient(),
    )

    var uiState by mutableStateOf(HomeUiState())
        private set

    init {
        loadVideos()
    }

    private fun loadVideos() {
        viewModelScope.launch {
            uiState = uiState.copy(isLoadingVideos = true)
            val videos = youtubeDataSource.fetchVideos(Content.YOUTUBE_PLAYLIST_ID)
            uiState = uiState.copy(
                videos = videos,
                isLoadingVideos = false,
            )

            // Save for widget (iOS only)
            if (getPlatform().name == Platform.IOS) {
                saveVideosForWidget(videos)
            }
        }
    }
}
