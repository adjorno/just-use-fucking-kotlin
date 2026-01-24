package com.ifochka.jufk.data

import com.ifochka.jufk.Platform
import com.ifochka.jufk.getPlatform
import jufk.composeapp.generated.resources.Res
import jufk.composeapp.generated.resources.icon_github
import jufk.composeapp.generated.resources.icon_linkedin
import jufk.composeapp.generated.resources.icon_x

object Content {
    val SocialLinks = listOf(
        SocialLink(
            name = "Github",
            icon = Res.drawable.icon_github,
            url = "https://github.com/adjorno/just-use-fucking-kotlin",
        ),
        SocialLink(
            name = "X",
            icon = Res.drawable.icon_x,
            url = "https://x.com/adjorno",
        ),
        SocialLink(
            name = "LinkedIn",
            icon = Res.drawable.icon_linkedin,
            url = "https://www.linkedin.com/in/mykhailo-dorokhin-0b99305a",
        ),
    )

    val Fucking = when (getPlatform().name) {
        Platform.IOS,
        Platform.ANDROID -> "F*cking"

        else -> "Fucking"
    }

    const val YOUTUBE_PLAYLIST_ID = "PLGS6AZIpM4eHR6EWt6IZ8HeizdP8SOCxU"
}
