package com.example.harisont.librery

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query

@Dao
interface BookDAO {

    @Insert
    fun saveBookData(book: BookEntity)

    @Query("SELECT * FROM BookEntity")
    fun selectAll() : List<BookEntity>
}