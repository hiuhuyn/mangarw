package com.example.app_mxh_manga.module

import java.util.*

class Chat {
    var id_user1: String = ""
    var id_user2: String = ""
    var last_messenger: String =""
    var last_messenger_time:Date? = null
    constructor()
    constructor(
        id_user1: String,
        id_user2: String,
        last_messenger: String,
        last_messenger_time: Date?
    ) {
        this.id_user1 = id_user1
        this.id_user2 = id_user2
        this.last_messenger = last_messenger
        this.last_messenger_time = last_messenger_time
    }

    constructor(id_user1: String, id_user2: String) {
        this.id_user1 = id_user1
        this.id_user2 = id_user2
    }
}
class Chat_Get {
    var id_chat = ""
    var chat = Chat()
    constructor()
    constructor(id_chat: String, chat: Chat) {
        this.id_chat = id_chat
        this.chat = chat
    }
}