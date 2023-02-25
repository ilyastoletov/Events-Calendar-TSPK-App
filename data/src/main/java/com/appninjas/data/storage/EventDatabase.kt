package com.appninjas.data.storage

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [EventDbModel::class], version = 2, exportSchema = false)
abstract class EventDatabase: RoomDatabase() {

    abstract fun getProductsDao(): EventsDao

    companion object {
        private var productsDatabase: EventDatabase ?= null

        @Synchronized
        fun getDatabaseInstance(context: Context): EventDatabase = if(productsDatabase == null) {
            productsDatabase = Room.databaseBuilder(context, EventDatabase::class.java, "products_db").fallbackToDestructiveMigration().build()
            productsDatabase as EventDatabase
        } else {
            productsDatabase as EventDatabase
        }
    }
}