package com.ifochka.jufk

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ifochka.jufk.data.Content
import com.ifochka.jufk.ui.JufkTheme
import com.ifochka.jufk.ui.components.Footer
import com.ifochka.jufk.ui.components.HeroSection
import com.ifochka.jufk.ui.theme.Dimensions
import com.ifochka.jufk.youtube.YoutubeSection
import com.ifochka.jufk.youtube.YoutubeVideo
import com.ifochka.jufk.youtube.YoutubeVideoDataSourceFromApi
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    var videos by mutableStateOf(emptyList<YoutubeVideo>())
    var isVideosLoading by mutableStateOf(false)

    LaunchedEffect(Unit) {
        isVideosLoading = true
        val youtubeVideosDataSource = YoutubeVideoDataSourceFromApi(
            apiKey = BuildKonfig.YOUTUBE_API_KEY,
            httpClient = createHttpClient(),
        )
        isVideosLoading = false
        videos = youtubeVideosDataSource.fetchVideos(Content.YOUTUBE_PLAYLIST_ID)
    }
    JufkTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background,
        ) {
            Scaffold(
                bottomBar = {
                    Footer(
                        links = Content.SocialLinks,
                        modifier = Modifier.navigationBarsPadding(),
                    )
                },
            ) { paddingValues ->
                Box(
                    modifier = Modifier.fillMaxSize().padding(paddingValues),
                    contentAlignment = Alignment.TopCenter,
                ) {
                    Box(
                        modifier = Modifier.widthIn(max = Dimensions.MAX_CONTENT_WIDTH).fillMaxSize(),
                    ) {
                        Column(
                            modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()),
                            horizontalAlignment = Alignment.CenterHorizontally,
                        ) {
                            HeroSection()
                            Spacer(modifier = Modifier.height(32.dp))

                            YoutubeSection(
                                isLoading = isVideosLoading,
                                videos = videos,
                            )
                        }
                    }
                }
            }
        }
    }
}
