package com.appninjas.domain.usecase

import com.appninjas.domain.repository.UserRepository

class NotificationControlUseCase(private val repository: UserRepository) {
    suspend fun invoke(toState: Boolean) = repository.controlNotifications(toState)
}