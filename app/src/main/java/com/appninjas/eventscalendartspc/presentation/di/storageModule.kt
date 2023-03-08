package com.appninjas.eventscalendartspc.presentation.di

import androidx.room.Room
import com.appninjas.data.storage.EventDatabase
import com.appninjas.data.storage.EventsDao
import org.koin.dsl.module

val storageModule = module {

    single<EventsDao> {
        val database: EventDatabase = get()
        database.getEventsDao()
    }

    single<EventDatabase> {
        Room.databaseBuilder(context = get(), klass = EventDatabase::class.java, name = "events_db")
            .fallbackToDestructiveMigration()
            .build()
    }

}