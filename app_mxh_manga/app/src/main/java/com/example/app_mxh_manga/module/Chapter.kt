package com.example.app_mxh_manga.module

import android.net.Uri
import java.util.Calendar
import java.util.Date

class Chapter {
    var title: String =""
    var date_submit: Date = Calendar.getInstance().time
    var id_story: String =""
    var views: Int = 0
    var images: ArrayList<String> = ArrayList()
    var content_novel: String = ""
    var comments: ArrayList<String> = ArrayList()
    var likes: ArrayList<String> = ArrayList()
    constructor()

    constructor(
        title: String,
        date_submit: Date,
        id_story: String,
        views: Int,
        images: ArrayList<String>,
        content_novel: String,
        comments: ArrayList<String>,
        likes: ArrayList<String>
    ) {
        this.title = title
        this.date_submit = date_submit
        this.id_story = id_story
        this.views = views
        this.images = images
        this.content_novel = content_novel
        this.comments = comments
        this.likes = likes
    }

    constructor(title: String, id_story: String, content_novel: String) {
        this.title = title
        this.id_story = id_story
        this.content_novel = content_novel
    }


}

class Chapter_Get{
    var id_chapter: String = ""
    var chapter: Chapter = Chapter()
    constructor()
    constructor(id_chapter: String, chapter: Chapter) {
        this.id_chapter = id_chapter
        this.chapter = chapter
    }

}
