package com.appninjas.domain.usecase

import com.appninjas.domain.repository.UserRepository

class LogoutUseCase(private val repository: UserRepository) {
    suspend fun invoke() = repository.logoutUser()
}