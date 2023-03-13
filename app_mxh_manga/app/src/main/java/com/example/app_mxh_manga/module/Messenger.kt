package com.example.app_mxh_manga.module

import android.net.Uri
import java.util.Calendar
import java.util.Date

class Messenger {
    var id_user1: String = ""
    var id_user2: String= ""
    var contents: ArrayList<String>  = ArrayList()
    constructor()

    constructor(id_user1: String, id_user2: String, contents: ArrayList<String>) {
        this.id_user1 = id_user1
        this.id_user2 = id_user2
        this.contents = contents
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
class Content_Messenger{
    var id_user: String="" // người gửi
    var date_submit: Date = Calendar.getInstance().time
    var content: String = ""
    constructor()
    constructor(id_user: String, date_submit: Date, content: String) {
        this.id_user = id_user
        this.date_submit = date_submit
        this.content = content
    }
}
class Content_Messenger_Get{
    var id_content: String = ""
    var contentMessenger: Content_Messenger = Content_Messenger()
    constructor()
    constructor(id_content: String, contentMessenger: Content_Messenger) {
        this.id_content = id_content
        this.contentMessenger = contentMessenger
    }


}

