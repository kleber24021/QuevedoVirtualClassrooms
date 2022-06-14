package org.quevedo.quevedovirtualclassrooms.ui.resource.resource_watcher

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.quevedo.quevedovirtualclassrooms.data.models.NetworkResult
import org.quevedo.quevedovirtualclassrooms.usecases.resource.GetResourceDetailById
import javax.inject.Inject

class ResourceWatcherViewModel @Inject constructor(
    private val getResourceDetailById: GetResourceDetailById
) : ViewModel() {
    private val _uiState: MutableStateFlow<ResourceWatcherContract.State> by lazy {
        MutableStateFlow(ResourceWatcherContract.State())
    }

    val uiState: StateFlow<ResourceWatcherContract.State> get() = _uiState

    fun handleEvent(event: ResourceWatcherContract.Event) {
        when (event) {
            is ResourceWatcherContract.Event.SetResourceId -> {
                _uiState.update {
                    it.copy(resourceId = event.resourceUUID)
                }
                handleEvent(ResourceWatcherContract.Event.GetObject)
            }

            is ResourceWatcherContract.Event.GetObject -> {
                viewModelScope.launch {
                    getResourceDetailById
                        .invoke(uiState.value.resourceId)
                        .catch(action = { cause ->
                            _uiState.update {
                                it.copy(
                                    error = cause.message ?: "Unexpected error"
                                )
                            }
                        })
                        .collect { result ->
                            when (result) {
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
            is ResourceWatcherContract.Event.ErrorMostrado -> {
                _uiState.update {
                    it.copy(error = null)
                }
            }
        }
    }
}