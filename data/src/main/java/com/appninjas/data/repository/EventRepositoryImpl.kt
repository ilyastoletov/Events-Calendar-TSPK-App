package com.appninjas.data.repository

import android.content.Context
import com.appninjas.data.mapper.EventMapper
import com.appninjas.data.storage.EventDatabase
import com.appninjas.domain.model.Event
import com.appninjas.domain.repository.EventsRepository
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

class EventRepositoryImpl(context: Context): EventsRepository {

    private val mapper = EventMapper()

    private val firebaseDb = Firebase.firestore

    private val eventDatabase = EventDatabase.getDatabaseInstance(context)
    private val eventDao = eventDatabase.getProductsDao()

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
    }

}