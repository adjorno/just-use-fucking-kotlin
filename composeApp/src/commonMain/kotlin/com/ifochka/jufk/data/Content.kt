package com.ifochka.jufk.data

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
            url = "https://github.com/adjorno/just-use-fucking-kotlin",
        ),
        SocialLink(
            name = "LinkedIn",
            icon = Res.drawable.icon_linkedin,
            url = "https://github.com/adjorno/just-use-fucking-kotlin",
        ),
    )
}
