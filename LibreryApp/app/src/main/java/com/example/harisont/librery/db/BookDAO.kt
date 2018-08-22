package com.example.harisont.librery.db

import android.arch.persistence.room.*

@Dao
interface BookDAO {

    @Query("SELECT * FROM BookEntity")
    fun selectAll(): List<BookEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveBookData(book: BookEntity)

    @Delete
    fun deleteBookData(book: BookEntity)

    @Query("SELECT * FROM BookEntity WHERE read = :read ORDER BY title")   // TODO: just select data shown in RecyclerView
    fun selectBookList(read: Boolean): List<BookEntity>

    @Query("SELECT * FROM BookEntity WHERE id = :id")
    fun selectBook(id: String): BookEntity?

    @Query(value = "SELECT * FROM BookEntity WHERE title LIKE :tit OR authors LIKE :aut")
    fun searchByTitleOrAuthor(tit: String, aut: String): BookEntity
}

