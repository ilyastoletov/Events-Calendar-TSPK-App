package com.appninjas.data.storage

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "events")
data class EventDbModel(
    @PrimaryKey
    val eventId: Int,
    val eventPictureUrl: String,
    val eventName: String,
    val eventDescription: String,
    val date: String,
    val category: String,
    val status: String
)