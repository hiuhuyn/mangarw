package com.example.app_mxh_manga.module

import android.net.Uri
import java.util.Date

class Chapter {
    var id_chapter: Int
    var title: String
    var date_submit: Date
    var id_story: Int
    var views: Int

    constructor(id_chapter: Int, title: String, date_submit: Date, id_story: Int, views: Int) {
        this.id_chapter = id_chapter
        this.title = title
        this.date_submit = date_submit
        this.id_story = id_story
        this.views = views
    }
}

class Chapter_novel{
    var id_chapter: Int
    var content: String

    constructor(id_chapter: Int, content: String) {
        this.id_chapter = id_chapter
        this.content = content
    }
}

class Chapter_manga{
    var id_chapter: Int
    var image: Uri

    constructor(id_chapter: Int, image: Uri) {
        this.id_chapter = id_chapter
        this.image = image
    }
}

class Comment_chapter{
    var id_cmt_chapter: Int
    var content: String
    var date_submit: Date
    var id_chapter:Int
    var id_user: Int
    constructor(
        id_cmt_chapter: Int,
        content: String,
        date_submit: Date,
        id_chapter: Int,
        id_user: Int
    ) {
        this.id_cmt_chapter = id_cmt_chapter
        this.content = content
        this.date_submit = date_submit
        this.id_chapter = id_chapter
        this.id_user = id_user
    }
}
