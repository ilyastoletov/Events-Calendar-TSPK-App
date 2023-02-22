package com.appninjas.domain.usecase

import com.appninjas.domain.model.User
import com.appninjas.domain.repository.UserRepository

class RegisterUserUseCase(private val repository: UserRepository) {
    suspend fun invoke(user: User, onSuccess: () -> Unit, onFailure: () -> Unit) = repository.registerUser(user, onSuccess, onFailure)
}