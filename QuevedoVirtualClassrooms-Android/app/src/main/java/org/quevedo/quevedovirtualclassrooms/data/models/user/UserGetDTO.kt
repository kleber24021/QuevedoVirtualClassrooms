package org.quevedo.quevedovirtualclassrooms.data.models.user

import org.quevedo.quevedovirtualclassrooms.data.models.common.UserType

data class UserGetDTO (
    private val username: String,
    private val name: String,
    private val surname: String,
    private val email: String,
    private val profileImage: String,
    private val userType: UserType
        )