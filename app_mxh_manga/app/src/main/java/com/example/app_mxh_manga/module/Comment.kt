package com.example.app_mxh_manga.module

import java.util.Calendar
import java.util.Date


class Comment_post{
    var content: String = ""
    var date_submit: Date = Calendar.getInstance().time
    var id_user: String = ""
    var id_post: String =""
    var parent_comment_id : String = ""
    constructor()
    constructor(
        content: String,
        date_submit: Date,
        id_user: String,
        id_post: String,
        parent_comment_id: String
    ) {
        this.content = content
        this.date_submit = date_submit
        this.id_user = id_user
        this.id_post = id_post
        this.parent_comment_id = parent_comment_id
    }

    constructor(content: String, date_submit: Date, id_user: String, id_post: String) {
        this.content = content
        this.date_submit = date_submit
        this.id_user = id_user
        this.id_post = id_post
    }


}
class Comment_Post_Get{
    var id_comment: String
    var comment_post: Comment_post
    constructor(id_comment: String, comment_post: Comment_post) {
        this.id_comment = id_comment
        this.comment_post = comment_post
    }
}


class Comment_chapter{
    var content: String = ""
    var date_submit: Date = Calendar.getInstance().time
    var id_user: String =""
    var id_chapter: String =""
    var parent_comment_id : String = ""
    constructor()
    constructor(
        content: String,
        date_submit: Date,
        id_user: String,
        id_chapter: String
    ) {
        this.content = content
        this.date_submit = date_submit
        this.id_user = id_user
        this.id_chapter = id_chapter
    }

    constructor(
        content: String,
        date_submit: Date,
        id_user: String,
        id_chapter: String,
        parent_comment_id: String
    ) {
        this.content = content
        this.date_submit = date_submit
        this.id_user = id_user
        this.id_chapter = id_chapter
        this.parent_comment_id = parent_comment_id
    }

}
class Comment_Chapter_Get{
    var id_comment: String
    var comment_Chapter: Comment_chapter

    constructor(id_comment: String, comment_Chapter: Comment_chapter) {
        this.id_comment = id_comment
        this.comment_Chapter = comment_Chapter
    }
}


