package com.appninjas.domain.usecase

import com.appninjas.domain.repository.EventsRepository

class SendNotificationUseCase(private val repository: EventsRepository) {
    suspend fun invoke(title: String, body: String) = repository.sendNotification(title, body)
}