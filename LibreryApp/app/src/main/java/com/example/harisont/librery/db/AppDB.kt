package com.example.harisont.librery.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context

@Database(entities = [(BookEntity::class)], version = 1)
abstract class AppDB: RoomDatabase() {

    abstract fun bookDAO(): BookDAO

    companion object {
        private var INSTANCE: AppDB? = null

        fun getInstance(context: Context): AppDB? {
            if (INSTANCE == null) {
                synchronized(AppDB::class) {
                    INSTANCE = Room.databaseBuilder(
                            context.applicationContext,
                            AppDB::class.java,
                            "books.db").build()
                    println("DATABASE created")
                }
            }
            return INSTANCE
        }

        fun destroyInstance() {
            INSTANCE = null
        }
    }
}