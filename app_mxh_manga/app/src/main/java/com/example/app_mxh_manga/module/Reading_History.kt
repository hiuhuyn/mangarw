package com.example.app_mxh_manga.module

import java.util.Date

class Reading_History {
    var id_history: Int
    var id_story: Int
    var id_user: Int
    var id_chapter: Int
    var time_read: Date

    constructor(id_history: Int, id_story: Int, id_user: Int, id_chapter: Int, time_read: Date) {
        this.id_history = id_history
        this.id_story = id_story
        this.id_user = id_user
        this.id_chapter = id_chapter
        this.time_read = time_read
    }
}