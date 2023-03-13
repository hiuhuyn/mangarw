package com.example.app_mxh_manga.module

import java.util.Calendar
import java.util.Date

class Reading_History {
    var id_user: String = ""
    var id_chapter: String = ""
    var time_read: Date = Calendar.getInstance().time
    constructor()
    constructor(id_user: String, id_chapter: String, time_read: Date) {
        this.id_user = id_user
        this.id_chapter = id_chapter
        this.time_read = time_read
    }
}

class Reading_History_Get{
    var id_reading: String = ""
    var readingHistory: Reading_History= Reading_History()

    constructor()
    constructor(id_reading: String, readingHistory: Reading_History) {
        this.id_reading = id_reading
        this.readingHistory = readingHistory
    }

}
