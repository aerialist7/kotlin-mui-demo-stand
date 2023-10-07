package team.karakum.page

import js.uri.decodeURIComponent
import kotlinx.browser.window
import mui.icons.material.Brightness4
import mui.icons.material.Brightness7
import mui.icons.material.GitHub
import mui.icons.material.MenuBook
import mui.material.*
import mui.material.styles.TypographyVariant
import mui.system.sx
import react.FC
import react.ReactNode
import react.create
import react.dom.aria.AriaHasPopup.Companion.`false`
import react.dom.aria.ariaHasPopup
import react.dom.aria.ariaLabel
import react.dom.html.ReactHTML
import team.karakum.router.useShowcaseIdParam
import team.karakum.theme.Themes
import team.karakum.theme.useSetTheme
import team.karakum.theme.useTheme
import web.cssom.integer
import web.cssom.number

val Header = FC {
    val theme = useTheme()
    val setTheme = useSetTheme()
    val showcaseId = useShowcaseIdParam()

    AppBar {
        sx {
            gridArea = Area.Header
            zIndex = integer(1_500)
        }

        position = AppBarPosition.fixed

        Toolbar {
            Typography {
                sx {
                    flexGrow = number(1.0)
                }

                component = ReactHTML.div
                variant = TypographyVariant.h6
                noWrap = true

                +"Kotlin MUI Showcase"
            }

            Tooltip {
                title = ReactNode("Theme")

                Switch {
                    ariaLabel = "theme"

                    icon = Brightness7.create()
                    checkedIcon = Brightness4.create()
                    checked = theme == Themes.Dark

                    onChange = { _, checked ->
                        setTheme(if (checked) Themes.Dark else Themes.Light)
                    }
                }
            }

            Tooltip {
                title = ReactNode("Read Documentation")

                IconButton {
                    ariaLabel = "official documentation"
                    ariaHasPopup = `false`

                    size = Size.large
                    color = IconButtonColor.inherit
                    onClick = {
                        window.location.href = "https://mui.com/${decodeURIComponent(showcaseId)}/"
                    }

                    MenuBook()
                }
            }

            Tooltip {
                title = ReactNode("View Sources")

                IconButton {
                    ariaLabel = "source code"
                    ariaHasPopup = `false`

                    size = Size.large
                    color = IconButtonColor.inherit
                    onClick = {
                        var name = showcaseId
                            .split("-")
                            .asSequence()
                            .map { it.replaceFirstChar(Char::titlecase) }
                            .reduce { accumulator, word -> accumulator.plus(word) }

                        if (name.isNotEmpty()) {
                            name += ".kt"
                        }

                        // TODO: Remove it after storing selected showcase
                        name = ""

                        window.location.href =
                            "https://github.com/karakum-team/kotlin-mui-showcase/blob/main/src/jsMain/kotlin/team/karakum/showcase/material/$name"
                    }

                    GitHub()
                }
            }
        }
    }
}
