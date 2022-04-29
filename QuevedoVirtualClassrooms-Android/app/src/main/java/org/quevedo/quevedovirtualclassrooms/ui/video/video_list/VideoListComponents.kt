package org.quevedo.quevedovirtualclassrooms.ui.video

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import org.quevedo.quevedovirtualclassrooms.data.models.VideoInfo


@Composable
fun VideoList(
    videos: List<VideoInfo>,
    onClick: (selectedVideo: VideoInfo) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        items(videos) { videos ->
            VideoItem(videoInfo = videos, selectVideo = onClick, modifier = modifier)
        }
    }
}

@Composable
fun VideoItem(
    videoInfo: VideoInfo,
    selectVideo: (selectedVideo: VideoInfo) -> Unit,
    modifier: Modifier = Modifier
) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { selectVideo(videoInfo) }
    ){
        val(title, date) = createRefs()
        Text(
            text = videoInfo.name,
            style = MaterialTheme.typography.h3.copy(fontSize = 18.sp),
            modifier = Modifier
                .constrainAs(title){
                    linkTo(
                        start = parent.start,
                        startMargin = 16.dp,
                        end = date.start,
                        endMargin = 16.dp
                    )
                    linkTo(
                        top = parent.top,
                        bottom = parent.bottom
                    )
                }
        )
        Text(
            text = videoInfo.uploadTime.toString(),
            style = MaterialTheme.typography.body1,
            modifier = Modifier
                .constrainAs(date){
                    linkTo(
                        start = title.end,
                        startMargin = 16.dp,
                        end = parent.end,
                        endMargin = 16.dp
                    )
                    linkTo(
                        top = parent.top,
                        bottom = parent.bottom
                    )
                }
        )
    }
}