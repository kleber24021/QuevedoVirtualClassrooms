package org.quevedo.quevedovirtualclassrooms.ui.classroom.classroom_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.quevedo.quevedovirtualclassrooms.data.models.NetworkResult
import org.quevedo.quevedovirtualclassrooms.usecases.classroom.GetAllClassroomsByUsername
import javax.inject.Inject

@HiltViewModel
class ClassroomListViewModel @Inject constructor(
    private val getAllClassroomsByUsername: GetAllClassroomsByUsername
) : ViewModel() {
    private val _uiState: MutableStateFlow<ClassroomListContract.State> by lazy {
        MutableStateFlow(ClassroomListContract.State())
    }
    val uiState: StateFlow<ClassroomListContract.State> get() = _uiState

    fun handleEvent(event: ClassroomListContract.Event){
        when(event){
            is ClassroomListContract.Event.SetUsername -> {
                _uiState.update {
                    it.copy(
                        username = event.username
                    )
                }
            }
            ClassroomListContract.Event.GetObjects -> {
                viewModelScope.launch {
                    getAllClassroomsByUsername
                        .invoke(uiState.value.username)
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
                                            classrooms = result.data
                                        )
                                    }
                                }
                            }
                        }
                }
            }
            ClassroomListContract.Event.ErrorMostrado -> {
                _uiState.update {
                    it.copy(error = null)
                }
            }
        }
    }
}