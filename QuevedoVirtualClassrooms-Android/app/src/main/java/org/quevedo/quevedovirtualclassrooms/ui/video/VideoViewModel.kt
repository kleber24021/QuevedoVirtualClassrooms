package org.quevedo.quevedovirtualclassrooms.ui.video

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import org.quevedo.quevedovirtualclassrooms.data.models.NetworkResult
import org.quevedo.quevedovirtualclassrooms.usecases.GetAllVideos
import javax.inject.Inject

@HiltViewModel
class VideoViewModel @Inject constructor(
    private val getAllVideos: GetAllVideos
): ViewModel(){
    private val _uiState: MutableStateFlow<VideoContract.State> by lazy {
        MutableStateFlow(VideoContract.State())
    }
    val uiState: StateFlow<VideoContract.State> get() = _uiState

    init {
        handleEvent(VideoContract.Event.GetData)
    }

    fun handleEvent(event: VideoContract.Event){
        when(event){
            is VideoContract.Event.GetData -> {
                viewModelScope.launch {
                    getAllVideos
                        .invoke()
                        .catch(action = {cause ->
                            _uiState.update {
                                it.copy(
                                    error = cause.message ?: "Unexpected error"
                                )
                            }
                        })
                        .collect{ result ->
                            when(result){
                                is NetworkResult.Error -> {
                                    _uiState.update { it.copy(error = result.message) }
                                }
                                is NetworkResult.Success -> {
                                    _uiState.update { state ->
                                        state.copy(
                                            videosList = result.data
                                        )
                                    }
                                }
                            }
                        }
                }
            }

            is VideoContract.Event.SelectVideo -> {
                _uiState.update {
                    it.copy(
                        selectedVideo = event.selectedVideo
                    )
                }
            }

            is VideoContract.Event.ErrorMostrado -> {
                _uiState.update {
                    it.copy(error = null)
                }
            }
        }
    }
}