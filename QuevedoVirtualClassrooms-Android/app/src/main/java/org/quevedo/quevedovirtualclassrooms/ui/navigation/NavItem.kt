package org.quevedo.quevedovirtualclassrooms.ui.navigation

import org.quevedo.quevedovirtualclassrooms.util.Routes

sealed class NavItem(
    private val baseRoute: String
) {
    object VideoListItem : NavItem(Routes.VIDEO_LIST)
    object VideoDetailItem: NavItem(Routes.WATCH_VIDEO)

    val route = run {
        baseRoute
    }
}