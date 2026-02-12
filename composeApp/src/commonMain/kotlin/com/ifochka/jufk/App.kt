package com.ifochka.jufk

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.ifochka.jufk.ui.JufkTheme
import com.ifochka.jufk.ui.components.Footer
import com.ifochka.jufk.ui.screens.HomeScreen
import com.ifochka.jufk.ui.theme.Dimensions
import com.ifochka.jufk.viewmodel.HomeViewModel
import kotlinx.coroutines.launch
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    JufkTheme {
        val snackbarHostState = remember { SnackbarHostState() }
        val scope = rememberCoroutineScope()
        val viewModel = remember(scope) { HomeViewModel(scope) }

        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background,
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.TopCenter,
            ) {
                Box(modifier = Modifier.widthIn(max = Dimensions.MAX_CONTENT_WIDTH).fillMaxSize()) {
                    Scaffold(
                        snackbarHost = {
                            SnackbarHost(snackbarHostState) { data ->
                                Snackbar(
                                    snackbarData = data,
                                    containerColor = MaterialTheme.colorScheme.surface,
                                    contentColor = MaterialTheme.colorScheme.onSurface,
                                )
                            }
                        },
                        bottomBar = {
                            Footer(
                                links = viewModel.uiState.socialLinks,
                                modifier = Modifier.navigationBarsPadding(),
                            )
                        },
                    ) { innerPadding ->
                        HomeScreen(
                            platformSections = viewModel.uiState.platformSections,
                            makingOfHeading = viewModel.uiState.makingOfHeading,
                            videos = viewModel.uiState.videos,
                            isLoadingVideos = viewModel.uiState.isLoadingVideos,
                            inspirationText = viewModel.uiState.inspirationText,
                            inspirationLinks = viewModel.uiState.inspirationLinks,
                            inspirationSuffix = viewModel.uiState.inspirationSuffix,
                            onCodeCopy = { _ ->
                                scope.launch {
                                    snackbarHostState.showSnackbar("Copied!")
                                }
                            },
                            modifier = Modifier.padding(innerPadding),
                        )
                    }
                }
            }
        }
    }
}
