package org.quevedo.quevedovirtualclassrooms.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import org.quevedo.quevedovirtualclassrooms.data.models.common.ResourceType
import org.quevedo.quevedovirtualclassrooms.ui.classroom.classroom_list.ClassroomListScreen
import org.quevedo.quevedovirtualclassrooms.ui.login.LoginScreen
import org.quevedo.quevedovirtualclassrooms.ui.resource.resource_detail.ResourceDetailScreen
import org.quevedo.quevedovirtualclassrooms.ui.resource.resource_list.ResourceListScreen
import org.quevedo.quevedovirtualclassrooms.ui.resource.resource_selector.ResourceSelectorScreen
import org.quevedo.quevedovirtualclassrooms.ui.resource.resource_watcher.ResourceWatcherScreen
import org.quevedo.quevedovirtualclassrooms.util.Routes

@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = NavItem.Login.route
    ) {

        composable(
            route = NavItem.Login.route
        ) {
            LoginScreen(goToMainScreen = {
                navController.navigate("${NavItem.ClassroomList.route}/${it}")
            })
        }

        composable(
            route = "${NavItem.ClassroomList.route}/{${Routes.USERNAME}}"
        ) {
            ClassroomListScreen(
                hasBackStack = navController.backQueue.size > 0,
                onBack = { navController.popBackStack() },
                loggedUsername = it.arguments?.getString(Routes.USERNAME) ?: "DEFAULT",
                onNavigate = { classroomId ->
                    navController.navigate("${NavItem.ResourceSelector.route}/${classroomId}")
                }
            )
        }

        composable(
            route = "${NavItem.ResourceSelector.route}/{${Routes.CLASSROOM_ID}}"
        ) {
            ResourceSelectorScreen(
                classroomId = it.arguments?.getString(Routes.CLASSROOM_ID) ?: "DEFAULT",
                hasBackStack = navController.backQueue.size > 0,
                onBack = { navController.popBackStack() },
                onSelection = {classroomId, resourceType ->
                    navController.navigate("${NavItem.VideoListItem.route}/${classroomId}/${resourceType.stringValue}")
                }
                )
        }

        composable(
            route = "${NavItem.VideoListItem.route}/{${Routes.CLASSROOM_ID}}/{${Routes.RESOURCE_TYPE}}"
        ) {
            ResourceListScreen(
                classroomId = it.arguments?.getString(Routes.CLASSROOM_ID) ?: "DEFAULT",
                resourceType = ResourceType.valueOf(
                    it.arguments?.getString(Routes.RESOURCE_TYPE) ?: "DEFAULT"
                ),
                hasBackStack = navController.backQueue.size > 0,
                onBack = { navController.popBackStack() },
                onNavigate = { resourceId ->
                    navController.navigate("${NavItem.VideoDetailItem.route}/${resourceId}")
                }
            )
        }

        composable(
            route = "${NavItem.VideoDetailItem.route}/{${Routes.RESOURCE_ID}}"
        )
        {
            ResourceDetailScreen(
                hasBackStack = navController.backQueue.size > 0,
                onBack = { navController.popBackStack() },
                resourceId = it.arguments?.getString(Routes.RESOURCE_ID) ?: "DEFAULT",
                goToWatcher = { resourceId ->
                    navController.navigate("${NavItem.ResourceWatcher.route}/${resourceId}")
                }
            )
        }

        composable(
            route = "${NavItem.ResourceWatcher.route}/{${Routes.RESOURCE_ID}}"
        ){
            ResourceWatcherScreen(
                resourceId = it.arguments?.getString(Routes.RESOURCE_ID) ?: "DEFAULT"
            )
        }
    }
}