package org.quevedo.quevedovirtualclassrooms.usecases.users

import org.quevedo.quevedovirtualclassrooms.data.repositories.UserRepository
import javax.inject.Inject

class GetUserDetail @Inject constructor(
    private val userRepository: UserRepository
){
    fun invoke(username: String) = userRepository.getUserDetailByUsername(username)
}