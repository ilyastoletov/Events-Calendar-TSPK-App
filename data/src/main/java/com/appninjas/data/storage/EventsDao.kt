package com.appninjas.data.storage

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface EventsDao {
    @Insert(entity = EventDbModel::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEvent(event: EventDbModel)

    @Query("SELECT * FROM events")
    fun getEvents(): List<EventDbModel>
}