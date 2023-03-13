package com.example.app_mxh_manga.module

class Rating {
    var score: Short = 0
    var id_user: String = ""
    constructor()

    constructor(score: Short, id_user: String) {
        this.score = score
        this.id_user = id_user
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
