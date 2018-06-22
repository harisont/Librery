package com.example.harisont.librery

// CLASSES USED JUST TO PARSE JSON WITH GSON

class SearchResults(val items: List<Book>)

class Book(val id: String,
           val volumeInfo: VolumeInfo)

class VolumeInfo(val title: String,
                 val authors: List<String>,
                 val publisher: String,
                 val publishedDate: String,
                 val imageLinks:ImageLinks)

class ImageLinks(val smallThumbnail: String)


class Response(val success: Boolean)