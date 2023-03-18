package com.example.app_mxh_manga.module

import java.util.Calendar
import java.util.Date

class History {
    var id_user = ""
    var id_story = ""
    var id_chapter = ""
    var time_reading: Date = Calendar.getInstance().time
    constructor()
    constructor(id_user: String, id_story: String, id_chapter: String, time_reading: Date) {
        this.id_user = id_user
        this.id_story = id_story
        this.id_chapter = id_chapter
        this.time_reading = time_reading
    }

    constructor(id_user: String, id_story: String, id_chapter: String) {
        this.id_user = id_user
        this.id_story = id_story
        this.id_chapter = id_chapter
    }


}
class History_Get {
    var id_history = ""
    var history: History = History()
    constructor(id_history: String, history: History) {
        this.id_history = id_history
        this.history = history
    }
}