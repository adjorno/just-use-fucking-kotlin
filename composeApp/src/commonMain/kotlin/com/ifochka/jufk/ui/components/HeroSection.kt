package com.ifochka.jufk.ui.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.sp

@Composable
fun HeroSection() {
    Text(
        text = buildAnnotatedString {
            append("Just Use Fucking ")
            withStyle(
                SpanStyle(
                    fontSize = 72.sp,
                    fontWeight = FontWeight.Normal,
                    color = MaterialTheme.colorScheme.primary,
                ),
            ) {
                append("Kotlin")
            }
            append(".\nPeriod.")
        },
        color = Color.White,
        fontSize = 72.sp,
        fontWeight = FontWeight.Bold,
        lineHeight = 96.sp,
        textAlign = TextAlign.Center,
    )
}
