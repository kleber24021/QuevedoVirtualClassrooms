package org.quevedo.quevedovirtualclassrooms.ui.resource.resource_detail

import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.MediaMetadata
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.ui.StyledPlayerView
import org.quevedo.quevedovirtualclassrooms.QueVirtualClassApp
import org.quevedo.quevedovirtualclassrooms.data.models.common.ResourceType
import org.quevedo.quevedovirtualclassrooms.data.models.resource.ResourceDetailGetDTO

@Composable
fun ResourceDetailScreen(
    hasBackStack: Boolean,
    onBack: () -> Unit,
    resourceId: String
) {
    val viewModel: ResourceDetailViewModel = hiltViewModel()
    QueVirtualClassApp(
        hasBackStack = hasBackStack,
        onBackAction = onBack
    ) { _ ->
        val uiState = viewModel.uiState.collectAsState()
        val scaffoldState = rememberScaffoldState()
        remember {
            viewModel.handleEvent(ResourceDetailContract.Event.SetResourceId(resourceId = resourceId))
            viewModel.handleEvent(ResourceDetailContract.Event.GetObject)
        }
        LaunchedEffect(key1 = true) {
            viewModel.uiState.collect { state ->
                state.error?.let {
                    val result = scaffoldState.snackbarHostState.showSnackbar(message = it)
                    if (result == SnackbarResult.ActionPerformed) {
                        viewModel.handleEvent(ResourceDetailContract.Event.ErrorMostrado)
                    }
                }
            }
        }
        val resource = uiState.value.resource
        Column(modifier = Modifier.fillMaxSize()) {
            when (resource?.resourceType) {
                ResourceType.IMAGE -> {
                    ImageShower(
                        imageUrl = uiState.value.resource?.resourceUrl
                            ?: "https://cdn-icons-png.flaticon.com/512/107/107817.png",
                        contentDescription = resource.resourceName,
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f, fill = true)
                            .background(Color.Black)
                    )
                }
                ResourceType.VIDEO -> {
                    VideoPlayer(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f, fill = true)
                            .background(Color.Black),
                        videoResource = resource
                    )
                }
                else -> {
                    Text(
                        text = "URL not supported yet",
                        style = MaterialTheme.typography.h2,
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f, fill = true)
                            .background(Color.Black)
                    )
                }
            }
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f, fill = true)
                    .background(Color.Gray)
            ) {
                Text(text = "Comments will go here")
            }
        }
    }
}

@Composable
fun ImageShower(
    modifier: Modifier = Modifier,
    imageUrl: String,
    contentDescription: String
) {
    AsyncImage(
        model = imageUrl,
        contentDescription = contentDescription,
        contentScale = ContentScale.Crop
    )
}

@Composable
fun VideoPlayer(
    modifier: Modifier = Modifier,
    videoResource: ResourceDetailGetDTO
) {
    val context = LocalContext.current
    val mediaItem = MediaItem.Builder()
        .setUri(videoResource.resourceUrl)
        .setMediaId(videoResource.uuidResource)
        .setTag(videoResource)
        .setMediaMetadata(
            MediaMetadata.Builder()
                .setDisplayTitle(videoResource.resourceName)
                .build()
        ).build()
    val videoTitle = remember { mutableStateOf(videoResource.resourceName) }
    val visibleState = remember { mutableStateOf(true) }

    //Create ExoPlayer
    val exoPlayer = remember {
        ExoPlayer.Builder(context).build().apply {
            this.setMediaItem(mediaItem)
            this.prepare()
            this.playWhenReady = true
            addListener(
                object : Player.Listener {
                    override fun onEvents(
                        player: Player,
                        events: Player.Events
                    ) {
                        super.onEvents(player, events)
                        // hide title only when player duration is at least 200ms
                        if (player.currentPosition >= 200)
                            visibleState.value = false
                    }

                    override fun onMediaItemTransition(
                        mediaItem: MediaItem?,
                        reason: Int
                    ) {
                        super.onMediaItemTransition(
                            mediaItem,
                            reason
                        )
                        // everytime the media item changes show the title
                        visibleState.value = true
                        videoTitle.value =
                            mediaItem?.mediaMetadata
                                ?.displayTitle.toString()
                    }
                }
            )
        }
    }
    Column {
        if (visibleState.value) {
            Text(text = videoTitle.value)
        }
        DisposableEffect(
            AndroidView(
                modifier = Modifier
                    .testTag("VideoPlayer"),
                factory = {
                    StyledPlayerView(context).apply {
                        player = exoPlayer
                        layoutParams =
                            FrameLayout.LayoutParams(
                                ViewGroup.LayoutParams.MATCH_PARENT,
                                ViewGroup.LayoutParams.MATCH_PARENT
                            )
                    }
                })
        ) {
            onDispose {
                exoPlayer.release()
            }
        }
    }
}
