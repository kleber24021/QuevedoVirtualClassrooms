package org.quevedo.quevedovirtualclassrooms.ui.login

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import org.quevedo.quevedovirtualclassrooms.data.sources.remote.SessionManager
import org.quevedo.quevedovirtualclassrooms.usecases.users.GetUserDetail
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val getUserDetail: GetUserDetail,
    private val sessionManager: SessionManager
) : ViewModel(){
    private val _uiState: MutableStateFlow<LoginContract.State> by lazy {
        MutableStateFlow(LoginContract.State())
    }
    val uiState: StateFlow<LoginContract.State> get() = _uiState

    fun handleEvent(event: LoginContract.Event){
        when(event){
            LoginContract.Event.DoLogin -> {

            }
            is LoginContract.Event.SetUsernamePassword -> {
                
            }
            LoginContract.Event.ErrorMostrado -> {

            }
        }
    }
}