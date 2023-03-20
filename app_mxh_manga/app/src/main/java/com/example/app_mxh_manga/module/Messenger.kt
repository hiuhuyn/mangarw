package com.example.app_mxh_manga.module

import android.net.Uri
import java.util.Calendar
import java.util.Date


class Messenger {
    var id_chat = ""
    var sender: String = ""
    var receiver: String= ""
    var images: ArrayList<String>  = ArrayList()
    var content: String = ""
    var date_submit: Date = Calendar.getInstance().time
    constructor()



    constructor(id_chat: String, sender: String, receiver: String, content: String) {
        this.id_chat = id_chat
        this.sender = sender
        this.receiver = receiver
        this.content = content
    }

    constructor(
        id_chat: String,
        sender: String,
        receiver: String,
        images: ArrayList<String>,
        content: String,
        date_submit: Date
    ) {
        this.id_chat = id_chat
        this.sender = sender
        this.receiver = receiver
        this.images = images
        this.content = content
        this.date_submit = date_submit
    }
}


class Messenger_Get {
    var id_messenger: String = ""
    var messenger: Messenger = Messenger()
    constructor()
    constructor(id_messenger: String, messenger: Messenger) {
        this.id_messenger = id_messenger
        this.messenger = messenger
    }

}


