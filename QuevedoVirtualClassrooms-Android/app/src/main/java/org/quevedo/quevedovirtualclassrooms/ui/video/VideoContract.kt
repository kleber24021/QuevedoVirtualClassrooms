package org.quevedo.quevedovirtualclassrooms.ui.video

import org.quevedo.quevedovirtualclassrooms.data.models.VideoInfo

interface VideoContract {
    sealed class Event {
        object GetData : Event()
        object ErrorMostrado : Event()
        data class SelectVideo(val selectedVideo: VideoInfo) : Event()
    }

    data class State(
        val videosList: List<VideoInfo>? = emptyList(),
        val selectedVideo: VideoInfo? = null,
        val error: String? = null
    )
}