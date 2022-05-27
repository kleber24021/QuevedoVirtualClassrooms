package org.quevedo.quevedovirtualclassrooms.ui.navigation

import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import org.quevedo.quevedovirtualclassrooms.ui.classroom.classroom_list.ClassroomListScreen
import org.quevedo.quevedovirtualclassrooms.ui.resource.resource_detail.ResourceDetailScreen
import org.quevedo.quevedovirtualclassrooms.ui.resource.resource_list.ResourceListScreen
import org.quevedo.quevedovirtualclassrooms.util.Routes

@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = "start"
    ) {
        composable(
            route = "${NavItem.VideoListItem.route}/{${ Routes.CLASSROOM_ID }}"
        ) {
            ResourceListScreen(
                classroomId = it.arguments?.getString(Routes.CLASSROOM_ID) ?: "DEFAULT",
                hasBackStack = navController.backQueue.size > 0,
                onBack = {navController.popBackStack()},
                onNavigate = {resourceId ->
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
                onBack = {navController.popBackStack()},
                resourceId = it.arguments?.getString(Routes.RESOURCE_ID)?:"DEFAULT"
            )
        }

        composable(
            route = "${NavItem.ClassroomList.route}/{${Routes.USERNAME}}"
        ){
            ClassroomListScreen(
                hasBackStack = navController.backQueue.size > 0,
                onBack = {navController.popBackStack()},
                loggedUsername = it.arguments?.getString(Routes.USERNAME) ?: "DEFAULT",
                onNavigate = {classroomId ->
                    navController.navigate("${NavItem.VideoListItem.route}/${ classroomId }")
                }
            )
        }

        composable(
            route = "start"
        ){
            Button(onClick = {
                navController.navigate("${NavItem.ClassroomList.route}/Teacher1")
            }) {
                Text(text = "START")
            }
        }
    }
}