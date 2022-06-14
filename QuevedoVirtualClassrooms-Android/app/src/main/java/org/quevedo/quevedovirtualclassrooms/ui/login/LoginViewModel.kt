package org.quevedo.quevedovirtualclassrooms.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.quevedo.quevedovirtualclassrooms.data.models.NetworkResult
import org.quevedo.quevedovirtualclassrooms.data.sources.remote.SessionManager
import org.quevedo.quevedovirtualclassrooms.usecases.users.GetUserDetail
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val getUserDetail: GetUserDetail,
    private val sessionManager: SessionManager
) : ViewModel() {
    private val _uiState: MutableStateFlow<LoginContract.State> by lazy {
        MutableStateFlow(LoginContract.State())
    }
    val uiState: StateFlow<LoginContract.State> get() = _uiState

    fun handleEvent(event: LoginContract.Event) {
        when (event) {
            is LoginContract.Event.DoLogin -> {
                if (event.loginInfo.username.isBlank()){
                    _uiState.update {
                        it.copy(
                            error = "Usuario vacío",
                            incorrectEmail = true
                        )
                    }
                    return
                }
                if (event.loginInfo.password.isBlank()){
                    _uiState.update {
                        it.copy(
                            error = "La contraseña no puede estar vacía",
                            incorrectEmail = true
                        )
                    }
                    return
                }
                viewModelScope.launch {
                    sessionManager.username = event.loginInfo.username
                    sessionManager.password = event.loginInfo.password
                    getUserDetail
                        .invoke(event.loginInfo.username)
                        .catch(action = { cause ->
                            _uiState.update {
                                it.copy(
                                    error = cause.message ?: "Login error"
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
                                            userHasLoggued = true
                                        )
                                    }
                                }
                            }
                        }
                }
            }
            LoginContract.Event.ErrorMostrado -> {
                _uiState.update {
                    it.copy(error = null)
                }
            }
            is LoginContract.Event.SetUserText -> {
                _uiState.update {
                    it.copy(
                        user = event.user,
                        incorrectEmail = false,
                    )
                }
            }
            is LoginContract.Event.SetUserPass -> {
                _uiState.update {
                    it.copy(
                        password = event.pass,
                        emptyPassword = false
                    )
                }
            }
        }
    }
}