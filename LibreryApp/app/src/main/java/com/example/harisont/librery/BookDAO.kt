package com.example.harisont.librery

import android.arch.persistence.room.*

@Dao
interface BookDAO {

    @Query("SELECT * FROM BookEntity")
    fun selectAll(): List<BookEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveBookData(book: BookEntity)

    @Delete
    fun deleteBookData(book: BookEntity)

    @Query("SELECT title, authors, thumbnailURL FROM BookEntity WHERE read = 1")
    fun selectHaveRead(): List<BookEntity>

    @Query("SELECT title, authors, thumbnailURL FROM BookEntity WHERE read = 0")
    fun selectToRead(): List<BookEntity>

    @Query("SELECT * from BookEntity WHERE id = :id")
    fun selectBook(id: String)



}

