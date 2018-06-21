package com.example.harisont.librery.db

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import org.jetbrains.annotations.NotNull

// LOCAL DATABASE TABLE

@Entity
class BookEntity constructor(
        id: String,
        title: String?,
        authors: String?,
        publisher: String?,
        publishedDate: String?,
        thumbnailURL: String?,
        read: Boolean,
        rating: Float,
        notes: String?) {

    @PrimaryKey
    var id: String = id

    @ColumnInfo
    var title: String? = title

    @ColumnInfo
    var authors: String? = authors

    @ColumnInfo
    var publisher: String? = publisher

    @ColumnInfo
    var publishedDate: String? = publishedDate   // TODO: change data type

    @ColumnInfo
    var thumbnailURL: String? = thumbnailURL

    @ColumnInfo @NotNull
    var read: Boolean = read

    @ColumnInfo // TODO: add constraints
    var rating: Float = rating

    @ColumnInfo
    var notes: String? = notes
}