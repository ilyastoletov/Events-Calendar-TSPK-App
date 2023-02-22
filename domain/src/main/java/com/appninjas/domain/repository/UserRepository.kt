package com.appninjas.domain.repository

import com.appninjas.domain.model.User

interface UserRepository {
    suspend fun registerUser(user: User, onSuccess: () -> Unit, onFailure: () -> Unit)
    suspend fun loginUser(user: User, onSuccess: () -> Unit, onFailure: () -> Unit)
    suspend fun logoutUser()
}