package org.quevedo.quevedovirtualclassrooms.ui.login

import org.quevedo.quevedovirtualclassrooms.data.models.user.LoginRequest

class LoginContract {
    sealed class Event{
        data class DoLogin(val loginInfo: LoginRequest) : Event()
        data class SetUserText(val user: String): Event()
        data class SetUserPass(val pass: String): Event()
        object ErrorMostrado : Event()
    }

    data class State(
        val user: String = "",
        val incorrectEmail: Boolean = false,
        val password: String = "",
        val emptyPassword: Boolean = true,
        val userHasLoggued: Boolean = false,
        val error: String? = null
    )
}