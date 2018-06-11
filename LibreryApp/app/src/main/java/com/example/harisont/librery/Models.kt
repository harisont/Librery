package com.example.harisont.librery

// CLASSES USED TO PARSE JSON

class SearchResults(val items: List<Book>)

class Book(val id: String,
           val volumeInfo: VolumeInfo)

class VolumeInfo(val title: String,
                 val authors: List<String>,
                 val publisher: String,
                 val publishedDate: String,
                 val imageLinks:ImageLinks)

class ImageLinks(val smallThumbnail: String)