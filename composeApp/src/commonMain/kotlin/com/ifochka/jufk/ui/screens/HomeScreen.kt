package com.ifochka.jufk.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withLink
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.ifochka.jufk.HapticStyle
import com.ifochka.jufk.Platform
import com.ifochka.jufk.data.Content
import com.ifochka.jufk.data.InspirationLink
import com.ifochka.jufk.data.PlatformSection
import com.ifochka.jufk.getPlatform
import com.ifochka.jufk.shareContent
import com.ifochka.jufk.triggerHaptic
import com.ifochka.jufk.ui.components.HeroSection
import com.ifochka.jufk.ui.components.PlatformSectionCard
import com.ifochka.jufk.ui.theme.Dimensions
import com.ifochka.jufk.youtube.YoutubeSection
import com.ifochka.jufk.youtube.YoutubeVideo
import jufk.composeapp.generated.resources.Res
import jufk.composeapp.generated.resources.icon_github
import org.jetbrains.compose.resources.painterResource

@Composable
fun HomeScreen(
    platformSections: List<PlatformSection>,
    videos: List<YoutubeVideo>,
    isLoadingVideos: Boolean,
    inspirationText: String,
    inspirationLinks: List<InspirationLink>,
    inspirationSuffix: String,
    onCodeCopy: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val uriHandler = LocalUriHandler.current

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
    ) {
        HeroSection()

        Text(
            text = "Why Kotlin?",
            style = MaterialTheme.typography.headlineLarge.copy(fontWeight = FontWeight.Bold),
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(top = 64.dp, bottom = 16.dp),
        )

        BoxWithConstraints {
            val isDesktop = maxWidth > Dimensions.DESKTOP_BREAKPOINT
            val textModifier = if (isDesktop) {
                Modifier.fillMaxWidth(0.6f)
            } else {
                Modifier.fillMaxWidth()
            }

            Text(
                text = Content.WHY_KOTLIN_DESCRIPTION,
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f),
                modifier = textModifier.padding(horizontal = if (isDesktop) 16.dp else 0.dp),
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        BoxWithConstraints {
            val isMobile = maxWidth < 800.dp

            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Button(
                    onClick = { uriHandler.openUri(Content.GITHUB_URL) },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.surface,
                        contentColor = MaterialTheme.colorScheme.onSurface,
                    ),
                    shape = RoundedCornerShape(8.dp),
                ) {
                    Image(
                        painter = painterResource(Res.drawable.icon_github),
                        contentDescription = "GitHub",
                        modifier = Modifier.size(20.dp),
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("View on GitHub")
                }

                // Share App button only visible on mobile
                if (isMobile) {
                    val currentPlatform = getPlatform().name
                    val appUrl = when (currentPlatform) {
                        Platform.IOS -> Content.IOS_APP_URL
                        Platform.ANDROID -> Content.ANDROID_APP_URL
                        else -> Content.WEBSITE_URL
                    }
                    val shareTitle = "Just Use Fucking Kotlin - One codebase, 5 platforms"

                    OutlinedButton(
                        onClick = {
                            triggerHaptic(HapticStyle.LIGHT)
                            shareContent(appUrl, shareTitle)
                        },
                        shape = RoundedCornerShape(8.dp),
                    ) {
                        Icon(
                            imageVector = Icons.Default.Share,
                            contentDescription = "Share",
                            modifier = Modifier.size(20.dp),
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Share App")
                    }
                }
            }
        }

        Text(
            text = "Check out the code, and don't forget to star the repo!",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f),
            modifier = Modifier.padding(top = 8.dp),
        )

        Spacer(modifier = Modifier.height(48.dp))

        BoxWithConstraints(
            modifier = Modifier.fillMaxWidth().padding(horizontal = Dimensions.CONTENT_PADDING),
        ) {
            val twoColumns = this.maxWidth > 800.dp

            if (twoColumns) {
                Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                    platformSections.chunked(2).forEach { rowItems ->
                        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                            rowItems.forEach { section ->
                                Box(modifier = Modifier.weight(1f)) {
                                    PlatformSectionCard(
                                        section = section,
                                        onCodeCopy = onCodeCopy,
                                    )
                                }
                            }
                            if (rowItems.size == 1) {
                                Spacer(modifier = Modifier.weight(1f))
                            }
                        }
                    }
                }
            } else {
                Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                    platformSections.forEach { section ->
                        PlatformSectionCard(
                            section = section,
                            onCodeCopy = onCodeCopy,
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(48.dp))

        YoutubeSection(
            isLoading = isLoadingVideos,
            videos = videos,
        )

        Spacer(modifier = Modifier.height(48.dp))

        // Inspiration section
        val textColor = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f)
        val linkColor = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f)
        val inspirationAnnotatedString = buildAnnotatedString {
            withStyle(SpanStyle(color = textColor)) {
                append("$inspirationText ")
            }
            inspirationLinks.forEachIndexed { index, link ->
                withLink(LinkAnnotation.Url(link.url)) {
                    withStyle(SpanStyle(color = linkColor, textDecoration = TextDecoration.Underline)) {
                        append(link.name)
                    }
                }
                if (index < inspirationLinks.lastIndex) {
                    withStyle(SpanStyle(color = textColor)) {
                        append(", ")
                    }
                }
            }
            withStyle(SpanStyle(color = textColor)) {
                append(" $inspirationSuffix")
            }
        }
        Text(
            text = inspirationAnnotatedString,
            style = MaterialTheme.typography.bodySmall,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = Dimensions.CONTENT_PADDING, vertical = 32.dp),
        )
    }
}
