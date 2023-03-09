package com.example.app_mxh_manga.module

import android.net.Uri
import java.util.Date

class Messenger {
    var id_messenger: Int
    var id_user1: Int
    var id_user2: Int

    constructor(id_messenger: Int, id_user1: Int, id_user2: Int) {
        this.id_messenger = id_messenger
        this.id_user1 = id_user1
        this.id_user2 = id_user2
    }
}

class Content_Messenger{
    var id_content: Int
    var id_messenger: Int
    var id_user: Int // người gửi
    var date_submit: Date
    var content: String

    constructor(
        id_content: Int,
        id_messenger: Int,
        id_user: Int,
        date_submit: Date,
        content: String
    ) {
        this.id_content = id_content
        this.id_messenger = id_messenger
        this.id_user = id_user
        this.date_submit = date_submit
        this.content = content
    }
}

class Image_Content_Messenger{
    var id_content: Int
    var uri_img: Uri

    constructor(id_content: Int, uri_img: Uri) {
        this.id_content = id_content
        this.uri_img = uri_img
    }
}
