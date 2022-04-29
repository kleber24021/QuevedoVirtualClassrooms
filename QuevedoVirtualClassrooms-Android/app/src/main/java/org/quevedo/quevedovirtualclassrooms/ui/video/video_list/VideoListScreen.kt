package org.quevedo.quevedovirtualclassrooms.ui.video.video_list

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarResult
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import org.quevedo.quevedovirtualclassrooms.QueVirtualClassApp
import org.quevedo.quevedovirtualclassrooms.ui.video.VideoContract.Event
import org.quevedo.quevedovirtualclassrooms.ui.video.VideoList
import org.quevedo.quevedovirtualclassrooms.ui.video.VideoViewModel

@Composable
fun VideoListScreen(
    onNavigate: () -> Unit,
    viewModel: VideoViewModel
){
    QueVirtualClassApp {
        val uiState = viewModel.uiState.collectAsState()
        val scaffoldState = rememberScaffoldState()
        LaunchedEffect(key1 = true){
            viewModel.uiState.collect{state ->
                state.error?.let {
                    val result = scaffoldState.snackbarHostState.showSnackbar(message = it)
                    if (result == SnackbarResult.ActionPerformed){
                        viewModel.handleEvent(Event.ErrorMostrado)
                    }
                }
            }
        }
        Scaffold { padding ->
            VideoList(videos = uiState.value.videosList ?: emptyList(), onClick = {
                onNavigate()
                viewModel.handleEvent(Event.SelectVideo(it))
            },
            modifier = Modifier.padding(padding))
        }
    }
}