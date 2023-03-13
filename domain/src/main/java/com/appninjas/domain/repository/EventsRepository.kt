package com.appninjas.domain.repository

import com.appninjas.domain.model.Event

interface EventsRepository {
    suspend fun getEventsList(): Map<String, List<Event>>
    suspend fun saveEventToStorage(eventModel: Event)
    suspend fun getEventsFromStorage(): List<Event>
    suspend fun makeEventEnded(event: Event)
    suspend fun addEvent(event: Event)
    suspend fun sendNotification(title: String, body: String)
}