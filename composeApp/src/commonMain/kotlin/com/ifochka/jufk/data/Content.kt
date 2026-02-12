package com.ifochka.jufk.data

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Android
import androidx.compose.material.icons.filled.Computer
import androidx.compose.material.icons.filled.Devices
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.Terminal
import androidx.compose.ui.graphics.Color
import com.ifochka.jufk.Platform
import com.ifochka.jufk.getPlatform
import jufk.composeapp.generated.resources.Res
import jufk.composeapp.generated.resources.icon_github
import jufk.composeapp.generated.resources.icon_linkedin
import jufk.composeapp.generated.resources.icon_x

object Content {
    val currentPlatform = getPlatform().name
    val isMobilePlatform = currentPlatform in listOf(Platform.IOS, Platform.ANDROID)

    fun getPlatformAdjective(): String =
        when {
            isMobilePlatform -> "Lovely"
            else -> "Fucking"
        }

    val HERO_TITLE: String
        get() = "Just Use ${getPlatformAdjective()} Kotlin. Period."
    const val HERO_SUBTITLE =
        "Too many platforms. Too many rewrites. " +
            "The same logic, drifting apart over time. One language keeps it together."

    val WHY_KOTLIN_DESCRIPTION: String
        get() {
            val platformList = when (currentPlatform) {
                Platform.IOS -> "Web, iOS, Desktop, and even a CLI tool"
                Platform.ANDROID -> "Web, Android, Desktop, and even a CLI tool"
                else -> "Web, iOS, Android, Desktop, and even a CLI tool"
            }
            return "See for yourself. This entire app - $platformList - " +
                "is built from a single Kotlin codebase. No magic. Just code."
        }

    const val BREW_COMMAND = "brew install adjorno/jufk/jufk"

    const val GITHUB_URL = "https://github.com/adjorno/just-use-fucking-kotlin"
    const val WEBSITE_URL = "https://justusefuckingkotlin.com"

    const val YOUTUBE_PLAYLIST_ID = "PLGS6AZIpM4eHR6EWt6IZ8HeizdP8SOCxU"

    val platformSections: List<PlatformSection>
        get() {
            val allSections = listOf(
                PlatformSection(
                    id = Platform.WASM,
                    title = "Web/WASM",
                    content = "Build fast Web UIs. Compiled from the same Kotlin codebase. Seriously performant.",
                    icon = Icons.Default.Language,
                    iconTint = Color(0xFFD500F9),
                    cta = Cta.Link(
                        text = if (isMobilePlatform) "(with the site)" else "justusefuckingkotlin.com",
                        url = WEBSITE_URL,
                    ),
                ),
                PlatformSection(
                    id = Platform.ANDROID,
                    title = "Android",
                    content = "Kotlin's native platform. Android Studio loves it. Your users will too.",
                    icon = Icons.Default.Android,
                    iconTint = Color(0xFF00C853),
                    cta = Cta.Button(
                        text = "Get it on Google Play",
                        url = "https://play.google.com/apps/internaltest/4701355457155312910",
                        icon = null,
                    ),
                ),
                PlatformSection(
                    id = Platform.IOS,
                    title = "iOS",
                    content = "Yes, a Kotlin app on iOS. Compiles down to native iOS. No joke.",
                    icon = Icons.Default.Devices,
                    iconTint = Color.White,
                    cta = Cta.Button(
                        "App Store",
                        "https://testflight.apple.com/join/sENnMKjM",
                        Icons.Default.Download,
                    ),
                ),
                PlatformSection(
                    id = Platform.DESKTOP,
                    title = "Desktop",
                    content = "One codebase. Your desktop app that no one asked for.",
                    icon = Icons.Default.Computer,
                    iconTint = Color(0xFF2979FF),
                    cta = Cta.Button(
                        "Download",
                        "https://github.com/adjorno/just-use-fucking-kotlin/releases",
                        Icons.Default.Download,
                    ),
                ),
                PlatformSection(
                    id = Platform.CLI,
                    title = "CLI",
                    content = "For the terminal warriors. It's just a Kotlin app. Even a command line tool.",
                    icon = Icons.Default.Terminal,
                    iconTint = Color(0xFFBDBDBD),
                    cta = Cta.Code(BREW_COMMAND),
                ),
            )

            // Filter out competitor platforms: hide Android on iOS, hide iOS on Android
            return when (currentPlatform) {
                Platform.IOS -> allSections.filter { it.id != Platform.ANDROID }
                Platform.ANDROID -> allSections.filter { it.id != Platform.IOS }
                else -> allSections
            }
        }

    const val FOOTER_AUTHOR = "@adjorno"
    val socialLinks = listOf(
        SocialLink(
            name = "GitHub",
            url = "https://github.com/adjorno",
            icon = Res.drawable.icon_github,
        ),
        SocialLink(
            name = "X",
            url = "https://x.com/adjorno",
            icon = Res.drawable.icon_x,
        ),
        SocialLink(
            name = "LinkedIn",
            url = "https://www.linkedin.com/in/mykhailo-dorokhin-0b99305a",
            icon = Res.drawable.icon_linkedin,
        ),
    )

    const val INSPIRATION_TEXT = "Inspired by"
    const val INSPIRATION_SUFFIX = "and other legends."
    val inspirationLinks: List<InspirationLink>
        get() = if (isMobilePlatform) {
            listOf(
                InspirationLink("Legend 1", "https://justfuckingusetailwind.com"),
                InspirationLink("Legend 2", "https://motherfuckingwebsite.com"),
            )
        } else {
            listOf(
                InspirationLink("justfuckingusetailwind.com", "https://justfuckingusetailwind.com"),
                InspirationLink("motherfuckingwebsite.com", "https://motherfuckingwebsite.com"),
            )
        }
}
