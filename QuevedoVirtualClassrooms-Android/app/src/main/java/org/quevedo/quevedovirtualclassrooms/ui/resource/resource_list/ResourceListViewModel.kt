package org.quevedo.quevedovirtualclassrooms.ui.resource.resource_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import org.quevedo.quevedovirtualclassrooms.data.models.NetworkResult
import org.quevedo.quevedovirtualclassrooms.usecases.resource.GetAllResourcesByClassroomId
import javax.inject.Inject

@HiltViewModel
class ResourceListViewModel @Inject constructor(
    private val getAllResourcesByClassroomId: GetAllResourcesByClassroomId
): ViewModel(){
    private val _uiState: MutableStateFlow<ResourceListContract.State> by lazy {
        MutableStateFlow(ResourceListContract.State())
    }
    val uiState: StateFlow<ResourceListContract.State> get() = _uiState

    fun handleEvent(event: ResourceListContract.Event){
        when(event){
            is ResourceListContract.Event.GetData -> {
                viewModelScope.launch {
                    getAllResourcesByClassroomId
                        .invoke(uiState.value.actualClassroomId)
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
            is ResourceListContract.Event.SetClassroomId ->{
                _uiState.update {
                    it.copy(
                        actualClassroomId = event.classroomId
                    )
                }
            }

            is ResourceListContract.Event.SelectVideo -> {
                _uiState.update {
                    it.copy(
                        selectedVideo = event.selectedVideo
                    )
                }
            }

            is ResourceListContract.Event.ErrorMostrado -> {
                _uiState.update {
                    it.copy(error = null)
                }
            }
        }
    }
}