package com.example.harisont.librery

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query

@Dao
interface BookDAO {

    @Query("SELECT * FROM BookEntity")
    fun selectAll(): List<BookEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveBookData(book: BookEntity)

    @Query("SELECT title, authors, thumbnailURL FROM BookEntity WHERE read = 1")
    fun selectHaveRead(): List<BookEntity>

    @Query("SELECT title, authors, thumbnailURL FROM BookEntity WHERE read = 0")
    fun selectToRead(): List<BookEntity>

    @Query("SELECT * from BookEntity WHERE id = :id")
    fun selectBook(id: String)

}

