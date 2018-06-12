package com.example.harisont.librery

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase

@Database(entities = [(BookEntity::class)], version = 1)
abstract class AppDB: RoomDatabase() {
    abstract fun bookDAO(): BookDAO
}