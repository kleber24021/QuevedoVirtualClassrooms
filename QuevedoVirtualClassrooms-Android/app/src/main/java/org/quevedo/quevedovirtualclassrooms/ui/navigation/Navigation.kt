package org.quevedo.quevedovirtualclassrooms.ui.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import org.quevedo.quevedovirtualclassrooms.Greeting
import org.quevedo.quevedovirtualclassrooms.ui.video.VideoViewModel
import org.quevedo.quevedovirtualclassrooms.ui.video.video_list.VideoListScreen

@Composable
fun Navigation() {
    val navController = rememberNavController()
    val viewModel: VideoViewModel = hiltViewModel()
    NavHost(
        navController = navController,
        startDestination = NavItem.VideoListItem.route
    ) {
        composable(NavItem.VideoListItem.route) {
            VideoListScreen(
                onNavigate = {
                    navController.navigate(NavItem.VideoDetailItem.route)
                },
                viewModel = viewModel
            )
        }
        composable(
            route = NavItem.VideoDetailItem.route
        )
        {
            Greeting(name = "VideoDetail")
        }
    }
}