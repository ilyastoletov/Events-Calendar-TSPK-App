package com.appninjas.data.mapper

import com.appninjas.data.storage.EventDbModel
import com.appninjas.domain.model.Event

class EventMapper {

    fun eventModelToDbModel(model: Event): EventDbModel = EventDbModel(
        eventId = model.eventId,
        eventName = model.eventName,
        eventDescription = model.eventDescription,
        eventPictureUrl = model.eventPictureUrl,
        category = model.category,
        date = model.date,
        status = model.status
    )

    fun dbModelToModel(dbModel: List<EventDbModel>): List<Event> = dbModel.map { dbModel ->
        Event(
            eventId = dbModel.eventId,
            eventName = dbModel.eventName,
            eventDescription = dbModel.eventDescription,
            eventPictureUrl = dbModel.eventPictureUrl,
            category = dbModel.category,
            date = dbModel.date,
            status = dbModel.status
        )
    }

    fun mapEventDocumentToEntity(documentObject: Map<String, Any>): Event = Event(
        eventId = documentObject["eventId"].toString().toInt(),
        eventPictureUrl = documentObject["eventPictureUrl"].toString(),
        eventName = documentObject["eventName"].toString(),
        eventDescription = documentObject["eventDescription"].toString(),
        date = documentObject["date"].toString(),
        category = documentObject["category"].toString(),
        status = when(documentObject["status"].toString().toBoolean()) {
            true -> "Завершено"
            false -> "Не завершено"
        }
    )

}