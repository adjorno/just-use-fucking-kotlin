package com.ifochka.jufk.youtube

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import io.ktor.http.Url

@Composable
fun VideoCard(video: YoutubeVideo) {
    val uriHandler = LocalUriHandler.current

    Column(
        modifier = Modifier.width(280.dp),
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(16f / 9f)
                .clip(RoundedCornerShape(16.dp))
                .clickable { uriHandler.openUri(video.url) },
            contentAlignment = Alignment.Center,
        ) {
            video.thumbnailUrl?.let { url ->
                KamelImage(
                    resource = { asyncPainterResource(Url(url)) },
                    contentScale = ContentScale.Crop,
                    contentDescription = video.title,
                )
            }
            Icon(
                imageVector = Icons.Default.PlayCircle,
                tint = MaterialTheme.colorScheme.onBackground,
                contentDescription = "Play",
                modifier = Modifier.size(48.dp),
            )
        }

        Text(
            text = video.title,
            style = MaterialTheme.typography.bodyMedium,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
        )
    }
}
