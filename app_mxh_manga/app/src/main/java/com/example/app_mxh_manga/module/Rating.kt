package com.example.app_mxh_manga.module

class Rating {
    var score: Int = 0
    var id_user: String = ""
    var id_story: String = ""
    constructor()
    constructor(score: Int, id_user: String, id_story: String) {
        this.score = score
        this.id_user = id_user
        this.id_story = id_story
    }

}

class Rating_Get{
    var id_rating: String = ""
    var rating: Rating = Rating()
    constructor()
    constructor(id_rating: String, rating: Rating) {
        this.id_rating = id_rating
        this.rating = rating
    }

}
