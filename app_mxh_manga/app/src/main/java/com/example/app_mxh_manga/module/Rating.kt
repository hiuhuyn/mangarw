package com.example.app_mxh_manga.module

class Rating {
    var id_rating: Int
    var score: Short
    var id_story: Int
    var id_user: Int

    constructor(id_rating: Int, score: Short, id_story: Int, id_user: Int) {
        this.id_rating = id_rating
        this.score = score
        this.id_story = id_story
        this.id_user = id_user
    }
}