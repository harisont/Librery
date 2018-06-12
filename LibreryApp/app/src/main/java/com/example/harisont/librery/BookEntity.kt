package com.example.harisont.librery

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

// LOCAL DATABASE TABLE
// TODO: add constraints

@Entity
class BookEntity {

    @PrimaryKey
    var id: String = ""

    @ColumnInfo
    var title: String? = null

    @ColumnInfo
    var authors: String? = null

    @ColumnInfo
    var publisher: String? = null

    @ColumnInfo
    var publishedDate: String? = null   // TODO: change data type

    @ColumnInfo
    var thumbnailURL: String? = null

    @ColumnInfo
    var read: Boolean? = false

    @ColumnInfo
    var rating: Int? = null

    @ColumnInfo
    var notes: String? = null
}