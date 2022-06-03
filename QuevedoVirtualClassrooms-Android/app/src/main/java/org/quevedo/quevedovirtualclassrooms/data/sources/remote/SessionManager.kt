package org.quevedo.quevedovirtualclassrooms.data.sources.remote

class SessionManager (){
    var username: String = ""
    var password: String = ""
    private var authToken: String? = null

    fun saveAuthToken(token: String){
        authToken = token
    }

    fun fetchAuthToken(): String? {
       return authToken;
    }

    fun isLogged(): Boolean{
        return authToken != null
    }
}