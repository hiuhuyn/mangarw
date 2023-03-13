package com.example.app_mxh_manga.module

import java.util.Calendar
import java.util.Date

class Comment {
    var content: String = ""
    var date_submit: Date = Calendar.getInstance().time
    var id_user: String =""
    constructor()
    constructor(content: String, date_submit: Date, id_user: String) {
        this.content = content
        this.date_submit = date_submit
        this.id_user = id_user
    }
}
class Comment_Get{
    var id_comment: String = ""
    var comment: Comment = Comment()
    constructor()
    constructor(id_comment: String, comment: Comment) {
        this.id_comment = id_comment
        this.comment = comment
    }
}