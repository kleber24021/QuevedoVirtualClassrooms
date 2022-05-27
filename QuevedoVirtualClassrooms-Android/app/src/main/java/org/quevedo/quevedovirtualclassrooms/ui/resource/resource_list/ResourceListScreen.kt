package org.quevedo.quevedovirtualclassrooms.ui.resource.resource_list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.SnackbarResult
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.PlayCircleFilled
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import org.quevedo.quevedovirtualclassrooms.QueVirtualClassApp
import org.quevedo.quevedovirtualclassrooms.data.models.resource.ResourceLiteGetDTO
import org.quevedo.quevedovirtualclassrooms.ui.components.StandardCard
import org.quevedo.quevedovirtualclassrooms.ui.resource.resource_list.ResourceListContract.Event

@Composable
fun ResourceListScreen(
    classroomId: String,
    hasBackStack: Boolean,
    onBack: () -> Unit,
    onNavigate: (resourceId: String) -> Unit
) {
    val viewModel: ResourceListViewModel = hiltViewModel()
    QueVirtualClassApp(
        onBackAction = onBack,
        hasBackStack = hasBackStack
    ) { paddingModifier ->
        val uiState = viewModel.uiState.collectAsState()
        val scaffoldState = rememberScaffoldState()
        remember {
            viewModel.handleEvent(Event.SetClassroomId(classroomId))
            viewModel.handleEvent(Event.GetData)
        }
        LaunchedEffect(key1 = true) {
            viewModel.uiState.collect { state ->
                state.error?.let {
                    val result = scaffoldState.snackbarHostState.showSnackbar(message = it)
                    if (result == SnackbarResult.ActionPerformed) {
                        viewModel.handleEvent(Event.ErrorMostrado)
                    }
                }
            }
        }
        VideoList(
            videos = uiState.value.videosList ?: emptyList(), onClick = {
                onNavigate(it.uuidResource)
                viewModel.handleEvent(Event.SelectVideo(it))
            },
            modifier = paddingModifier
        )
    }
}

@Composable
fun VideoList(
    videos: List<ResourceLiteGetDTO>,
    onClick: (selectedVideo: ResourceLiteGetDTO) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        items(videos) { videos ->
            VideoItem(resourceLiteGetDTO = videos, selectVideo = onClick, modifier = modifier)
        }
    }
}

@Composable
fun VideoItem(
    resourceLiteGetDTO: ResourceLiteGetDTO,
    selectVideo: (selectedVideo: ResourceLiteGetDTO) -> Unit,
    modifier: Modifier = Modifier
) {
    StandardCard(
        title = resourceLiteGetDTO.resourceName,
        secondaryText = resourceLiteGetDTO.timestamp.toString(),
        color = MaterialTheme.colors.background,
        showArrow = false,
        cardAction = {
            selectVideo(resourceLiteGetDTO)
        }
    ) {
        Icon(
            Icons.Outlined.PlayCircleFilled, "Image",
            modifier = Modifier
                .size(50.dp),
            tint = MaterialTheme.colors.primaryVariant
        )
    }
}