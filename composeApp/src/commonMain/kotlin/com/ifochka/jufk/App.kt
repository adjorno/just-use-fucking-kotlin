package com.ifochka.jufk

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    MaterialTheme {
        Box(
            modifier = Modifier.fillMaxSize().background(Color.Black),
            contentAlignment = Alignment.TopCenter,
        ) {
            Text(
                text = buildAnnotatedString {
                    append("Just Use Fucking ")
                    withStyle(
                        SpanStyle(
                            fontSize = 72.sp,
                            fontWeight = FontWeight.Normal,
                            color = Color(0xFF8A45FC),
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
    }
}
