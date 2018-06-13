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

    @Query("SELECT * FROM BookEntity WHERE read = 1")   // TODO: just select data shown in RecyclerView
    fun selectHaveRead(): List<BookEntity>

    @Query("SELECT * FROM BookEntity WHERE read = 0")   // TODO: just select data shown in RecyclerView
    fun selectToRead(): List<BookEntity>

    @Query("SELECT * from BookEntity WHERE id = :id")
    fun selectBook(id: String): BookEntity



}

