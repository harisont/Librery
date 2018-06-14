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

    @Query("SELECT * FROM BookEntity WHERE read = :read")   // TODO: just select data shown in RecyclerView
    fun selectBookList(read: Boolean): List<BookEntity>

    @Query("SELECT * from BookEntity WHERE id = :id")
    fun selectBook(id: String): BookEntity?

}

