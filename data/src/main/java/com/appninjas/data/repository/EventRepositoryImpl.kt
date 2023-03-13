package com.appninjas.data.repository

import com.appninjas.data.mapper.EventMapper
import com.appninjas.data.network.ApiService
import com.appninjas.data.network.model.Notification
import com.appninjas.data.network.model.NotificationParams
import com.appninjas.data.storage.EventsDao
import com.appninjas.domain.model.Event
import com.appninjas.domain.repository.EventsRepository
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class EventRepositoryImpl(
    private val firebaseDb: FirebaseFirestore,
    private val eventDao: EventsDao,
    private val notificationApiService: ApiService,
    private val mapper: EventMapper
    ): EventsRepository {

    override suspend fun getEventsList(): Map<String, List<Event>> {
        val allEventsDocuments = firebaseDb.collection("events").get().await()
        val activeEventsList: ArrayList<Event> = arrayListOf()
        val endedEventsList: ArrayList<Event> = arrayListOf()
        for (document in allEventsDocuments.documents) {
            val documentData = document.data!!
            val modelFromDocument = mapper.mapEventDocumentToEntity(documentData)
            if (modelFromDocument.status == "Завершено") {
                endedEventsList.add(modelFromDocument)
            } else {
                activeEventsList.add(modelFromDocument)
            }
        }
        return mapOf(
            "active" to activeEventsList,
            "ended" to endedEventsList
        )
    }

    override suspend fun getEventsFromStorage(): List<Event> {
        val listFromDb = eventDao.getEvents()
        return mapper.dbModelToModel(listFromDb)
    }

    override suspend fun saveEventToStorage(eventModel: Event) {
        val mappedModel = mapper.eventModelToDbModel(eventModel)
        eventDao.insertEvent(mappedModel)
    }

    override suspend fun makeEventEnded(event: Event) {
        firebaseDb.collection("events").document(event.eventId.toString())
            .update("status", "true")
    }

    override suspend fun addEvent(event: Event) {
        firebaseDb.collection("events").document(event.eventId.toString()).set(event)
        sendNotification("Новое событие!", "Добавлено новое событие, не пропустите его")
    }

    override suspend fun sendNotification(title: String, body: String) {
        val eventNotification = Notification(
            to = "/topics/xyz",
            notification = NotificationParams(
                title = title,
                body = body,
                mutable_content = true,
                sound = "Tri-tone"
            )
        )
        notificationApiService.notifyUsers(eventNotification)
    }

}