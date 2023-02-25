package com.appninjas.domain.usecase

import com.appninjas.domain.model.User
import com.appninjas.domain.repository.UserRepository

class GetUserDataUseCase(private val repository: UserRepository) {
    suspend fun invoke(): User = repository.getUserData()
}