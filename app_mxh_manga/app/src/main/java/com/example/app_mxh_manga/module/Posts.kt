package com.example.app_mxh_manga.module

import java.util.Calendar
import java.util.Date

class Posts {
    var id_user: String = ""
    var content: String= ""
    var date_submit: Date = Calendar.getInstance().time
    var images: ArrayList<String> = ArrayList()
    var likes: ArrayList<String> = ArrayList()
    constructor()

    constructor(
        id_user: String,
        content: String,
        date_submit: Date,
        images: ArrayList<String>,
        likes: ArrayList<String>
    ) {
        this.id_user = id_user
        this.content = content
        this.date_submit = date_submit
        this.images = images
        this.likes = likes
    }

    constructor(id_user: String, content: String, images: ArrayList<String>) {
        this.id_user = id_user
        this.content = content
        this.images = images
    }

    constructor(id_user: String, content: String) {
        this.id_user = id_user
        this.content = content
    }
}
class Post_Get{
    var id_post: String = ""
    var posts: Posts = Posts()
    constructor()
    constructor(id_post: String, posts: Posts) {
        this.id_post = id_post
        this.posts = posts
    }


}
