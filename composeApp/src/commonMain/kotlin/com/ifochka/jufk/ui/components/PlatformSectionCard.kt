package com.ifochka.jufk.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ifochka.jufk.HapticStyle
import com.ifochka.jufk.data.Cta
import com.ifochka.jufk.data.PlatformSection
import com.ifochka.jufk.triggerHaptic

@Composable
fun PlatformSectionCard(
    section: PlatformSection,
    onCodeCopy: (String) -> Unit,
    isCurrentPlatform: Boolean,
    modifier: Modifier = Modifier,
) {
    val uriHandler = LocalUriHandler.current
    val clipboardManager = LocalClipboardManager.current

    val borderBrush = if (section.isHighlighted || isCurrentPlatform) {
        Brush.verticalGradient(
            colors = listOf(colorScheme.primary, colorScheme.secondary),
        )
    } else {
        SolidColor(colorScheme.onSurface.copy(alpha = 0.1f))
    }

    Box(contentAlignment = Alignment.TopEnd) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .border(
                    width = 1.dp,
                    brush = borderBrush,
                    shape = RoundedCornerShape(12.dp),
                ).background(
                    color = colorScheme.surface.copy(alpha = 0.5f),
                    shape = RoundedCornerShape(12.dp),
                ).clip(RoundedCornerShape(12.dp))
                .padding(24.dp),
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = section.icon,
                    contentDescription = section.title,
                    tint = section.iconTint,
                    modifier = Modifier.size(24.dp),
                )
                Text(
                    text = section.title,
                    style = typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                    modifier = Modifier.padding(start = 12.dp),
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = section.content,
                style = typography.bodyMedium,
                color = colorScheme.onSurface.copy(alpha = 0.7f),
                lineHeight = 22.sp,
            )

            Spacer(modifier = Modifier.height(24.dp))

            when (val cta = section.cta) {
                is Cta.Link -> {
                    Text(
                        text = cta.text,
                        style = typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                        color = colorScheme.primary,
                        modifier = Modifier.clickable {
                            triggerHaptic(HapticStyle.LIGHT)
                            uriHandler.openUri(cta.url)
                        },
                    )
                }
                is Cta.Button -> {
                    Button(
                        onClick = {
                            triggerHaptic(HapticStyle.LIGHT)
                            uriHandler.openUri(cta.url)
                        },
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = colorScheme.surfaceVariant,
                            contentColor = colorScheme.onSurface,
                        ),
                    ) {
                        cta.icon?.let { icon ->
                            Icon(imageVector = icon, contentDescription = null, modifier = Modifier.size(18.dp))
                        }
                        Text(cta.text, modifier = Modifier.padding(start = 8.dp))
                    }
                }
                is Cta.Code -> {
                    CodeBlock(code = cta.code, onCopy = {
                        triggerHaptic(HapticStyle.SUCCESS)
                        clipboardManager.setText(AnnotatedString(it))
                        onCodeCopy(it)
                    })
                }
            }
        }

        if (isCurrentPlatform) {
            YouAreHereBadge(modifier = Modifier.offset(x = (-12).dp, y = (-12).dp))
        }
    }
}

@Composable
private fun YouAreHereBadge(modifier: Modifier = Modifier) {
    Text(
        text = "You are here",
        fontSize = 10.sp,
        color = Color.White,
        modifier = modifier
            .background(colorScheme.primary, RoundedCornerShape(50))
            .padding(horizontal = 8.dp, vertical = 4.dp),
    )
}

@Composable
private fun CodeBlock(
    code: String,
    onCopy: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(color = Color.Black, shape = RoundedCornerShape(8.dp))
            .border(
                width = 1.dp,
                color = colorScheme.onSurface.copy(alpha = 0.1f),
                shape = RoundedCornerShape(8.dp),
            ).clip(RoundedCornerShape(8.dp))
            .clickable { onCopy(code) },
    ) {
        Text(
            text = code,
            fontFamily = FontFamily.Monospace,
            color = colorScheme.onSurface,
            fontSize = 14.sp,
            modifier = Modifier
                .align(Alignment.CenterStart)
                .padding(horizontal = 16.dp, vertical = 12.dp)
                .padding(end = 48.dp),
        )
        Text(
            text = "Copy",
            color = colorScheme.onSurface.copy(alpha = 0.6f),
            fontSize = 12.sp,
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .padding(end = 16.dp),
        )
    }
}
