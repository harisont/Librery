package com.example.harisont.librery.utils

// CLASSES USED JUST TO PARSE JSON WITH GSON

class SearchResults(val items: List<Book>)

class Book(val id: String,
           val volumeInfo: VolumeInfo)

class VolumeInfo(val title: String,
                 val authors: List<String>,
                 val publisher: String,
                 val publishedDate: String,
                 val imageLinks: ImageLinks)

class ImageLinks(val smallThumbnail: String)


class InsertResponse(val success: Int)

class Recommendations(val success: Int, val data: List<Recommendation>)

class Recommendation(val libro: String,
                     val commento: String,
                     val valutazione: Float)