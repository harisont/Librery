package com.example.harisont.librery.db

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import org.jetbrains.annotations.NotNull

// LOCAL DATABASE TABLE

@Entity
class BookEntity (
        @PrimaryKey var id: String,
        @ColumnInfo var title: String?,
        @ColumnInfo var authors: String?,
        @ColumnInfo var publisher: String?,
        @ColumnInfo var publishedDate: String?,
        @ColumnInfo var thumbnailURL: String?,
        @ColumnInfo @NotNull var read: Boolean,
        // TODO: add constraints
        @ColumnInfo var rating: Float,
        @ColumnInfo var notes: String?)