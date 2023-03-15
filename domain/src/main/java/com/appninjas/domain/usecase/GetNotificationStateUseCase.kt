package com.appninjas.domain.usecase

import com.appninjas.domain.repository.UserRepository

class GetNotificationStateUseCase(private val repository: UserRepository) {
    suspend fun invoke(): Boolean = repository.getNotificationState()
}