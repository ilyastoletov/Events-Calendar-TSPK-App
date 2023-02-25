package com.appninjas.domain.usecase

import com.appninjas.domain.model.Event
import com.appninjas.domain.repository.EventsRepository

class GetEventsListUseCase(private val repository: EventsRepository) {
    suspend fun invoke(): Map<String, List<Event>> = repository.getEventsList()
}