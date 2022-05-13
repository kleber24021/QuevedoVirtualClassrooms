package org.quevedo.quevedovirtualclassrooms.ui.navigation

import org.quevedo.quevedovirtualclassrooms.util.Routes

sealed class NavItem(private val baseRoute: String) {

    object VideoListItem : NavItem(Routes.RESOURCES_LIST)
    object VideoDetailItem: NavItem(Routes.RESOURCE_DETAIL)
    object ClassroomList: NavItem(Routes.CLASSROOM_LIST)

    val route = baseRoute

}