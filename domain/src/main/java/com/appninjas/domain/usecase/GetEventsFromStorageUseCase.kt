package com.appninjas.domain.usecase

import com.appninjas.domain.model.Event
import com.appninjas.domain.repository.EventsRepository

class GetEventsFromStorageUseCase(private val repository: EventsRepository) {
    suspend fun invoke(): List<Event> = repository.getEventsFromStorage()
}