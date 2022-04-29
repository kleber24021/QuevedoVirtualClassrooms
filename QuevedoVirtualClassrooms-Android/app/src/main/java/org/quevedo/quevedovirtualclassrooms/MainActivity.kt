package org.quevedo.quevedovirtualclassrooms

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import dagger.hilt.android.AndroidEntryPoint
import org.quevedo.quevedovirtualclassrooms.ui.navigation.Navigation
import org.quevedo.quevedovirtualclassrooms.ui.theme.QuevedoVirtualClassroomsTheme
import org.quevedo.quevedovirtualclassrooms.ui.video.video_list.VideoListScreen
import org.quevedo.quevedovirtualclassrooms.util.Routes

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            QuevedoVirtualClassroomsTheme {
                Navigation()
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    QuevedoVirtualClassroomsTheme {
        Navigation()
    }
}