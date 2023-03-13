package com.example.app_mxh_manga.module

class Genre {
    var name: String = ""
    var describe: String = ""
    constructor()
    constructor(name: String, describe: String) {
        this.name = name
        this.describe = describe
    }
}
class Genre_Get {
    var id_genre: String = ""
    var genre: Genre = Genre()
    constructor()
    constructor(id_genre: String, genre: Genre) {
        this.id_genre = id_genre
        this.genre = genre
    }


}

