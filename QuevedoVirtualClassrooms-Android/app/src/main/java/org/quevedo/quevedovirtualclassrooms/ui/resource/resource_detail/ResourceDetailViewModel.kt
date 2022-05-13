package org.quevedo.quevedovirtualclassrooms.ui.resource.resource_detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import org.quevedo.quevedovirtualclassrooms.data.models.NetworkResult
import org.quevedo.quevedovirtualclassrooms.usecases.resource.GetResourceDetailById
import javax.inject.Inject

@HiltViewModel
class ResourceDetailViewModel @Inject constructor(
    private val getResourceDetailById: GetResourceDetailById
): ViewModel(){
    private val _uiState: MutableStateFlow<ResourceDetailContract.State> by lazy {
        MutableStateFlow(ResourceDetailContract.State())
    }

    val uiState: StateFlow<ResourceDetailContract.State> get() = _uiState

    fun handleEvent(event: ResourceDetailContract.Event){
        when(event){
            is ResourceDetailContract.Event.SetResourceId -> {
                _uiState.update {
                    it.copy(resourceId = event.resourceId)
                }
                handleEvent(ResourceDetailContract.Event.GetObject)
            }

            is ResourceDetailContract.Event.GetObject -> {
                viewModelScope.launch {
                    getResourceDetailById
                        .invoke(uiState.value.resourceId)
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
                                            resource = result.data
                                        )
                                    }
                                }
                            }
                        }
                }
            }
            is ResourceDetailContract.Event.ErrorMostrado -> {
                _uiState.update {
                    it.copy(error = null)
                }
            }
        }
    }
}