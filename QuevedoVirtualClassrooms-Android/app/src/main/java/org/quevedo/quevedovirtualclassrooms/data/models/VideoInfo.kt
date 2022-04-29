package org.quevedo.quevedovirtualclassrooms.data.models

import java.time.LocalDateTime

data class VideoInfo(
    val id: String,
    val name: String,
    val url: String,
    val uploadTime: LocalDateTime
)
