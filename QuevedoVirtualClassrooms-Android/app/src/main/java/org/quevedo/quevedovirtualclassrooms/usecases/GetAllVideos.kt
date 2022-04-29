package org.quevedo.quevedovirtualclassrooms.usecases

import org.quevedo.quevedovirtualclassrooms.data.repositories.VideoRepository
import javax.inject.Inject

class GetAllVideos @Inject constructor(
    private val videoRepository: VideoRepository
) {
    fun invoke() = videoRepository.getAllVideos();
}