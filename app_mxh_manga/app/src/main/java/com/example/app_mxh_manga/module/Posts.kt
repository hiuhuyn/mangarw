package com.example.app_mxh_manga.module

import android.net.Uri
import android.provider.ContactsContract
import java.util.Calendar
import java.util.Date

class Posts {
    var id_post: Int
    var id_user: Int
    var content: String
    var data_submit: Date
    constructor(id_post: Int, id_user: Int, content: String, data_submit: Date) {
        this.id_post = id_post
        this.id_user = id_user
        this.content = content
        this.data_submit = data_submit
    }
}
class Post_images {
    var id_post : Int;
    var uri_img: Uri
    constructor(id_post: Int, uri_img: Uri) {
        this.id_post = id_post
        this.uri_img = uri_img
    }
}

class Favorites_post {
    var id_post: Int
    var id_user: Int
    var date_submit: Date

    constructor(id_post: Int, id_user: Int, date_submit: Date) {
        this.id_post = id_post
        this.id_user = id_user
        this.date_submit = date_submit
    }
}

class Comment_post {
    var id_cmt: Int
    var content: String
    var date_submit: Date
    var id_post: Int
    var id_user: Int
    var uri_img: Uri

    constructor(
        id_cmt: Int,
        content: String,
        date_submit: Date,
        id_post: Int,
        id_user: Int,
        uri_img: Uri
    ) {
        this.id_cmt = id_cmt
        this.content = content
        this.date_submit = date_submit
        this.id_post = id_post
        this.id_user = id_user
        this.uri_img = uri_img
    }
}
