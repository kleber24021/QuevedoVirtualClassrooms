package org.quevedo.quevedovirtualclassrooms.ui.login

import org.quevedo.quevedovirtualclassrooms.data.models.user.LoginRequest

class LoginContract {
    sealed class Event{
        object DoLogin : Event()
        object ErrorMostrado : Event()
        data class SetUsernamePassword(val loginInfo: LoginRequest): Event()
    }

    data class State(
        val incorrectEmail: Boolean = false,
        val emptyPassword: Boolean = true,
        val userHasLoggued: Boolean = false,
        val error: String? = null
    )
}