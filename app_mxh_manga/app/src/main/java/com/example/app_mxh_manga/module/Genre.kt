package com.example.app_mxh_manga.module

class Genre {
    var id_genre: Int
    var name: String

    constructor(id_genre: Int, name: String) {
        this.id_genre = id_genre
        this.name = name
    }
}

class Story_genre{
    var id_story: Int
    var id_genre: Int

    constructor(id_story: Int, id_genre: Int) {
        this.id_story = id_story
        this.id_genre = id_genre
    }
}