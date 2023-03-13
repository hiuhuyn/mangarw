package com.example.app_mxh_manga.module

import android.net.Uri
import android.os.Parcel
import android.os.Parcelable
import java.util.Date



class User{

    var name: String =""
    var birthday: String = ""
    var sex: Boolean = true
    var email: String = ""
    var score: Int = 0
    var point: Int = 0
    var story: String = ""
    var uri_avt: String = ""
    var uri_cover: String = ""
    var password: String =""
    var follow_users: ArrayList<String>  = ArrayList()
    var follow_storys: ArrayList<String> = ArrayList()
    constructor()



    constructor(name: String, birthday: String, sex: Boolean, email: String, password: String) {
        this.name = name
        this.birthday = birthday
        this.sex = sex
        this.email = email
        this.password = password
    }

    constructor(
        name: String,
        birthday: String,
        sex: Boolean,
        email: String,
        score: Int,
        point: Int,
        story: String,
        uri_avt: String,
        uri_cover: String,
        password: String,
        follow_users: ArrayList<String>,
        follow_storys: ArrayList<String>
    ) {
        this.name = name
        this.birthday = birthday
        this.sex = sex
        this.email = email
        this.score = score
        this.point = point
        this.story = story
        this.uri_avt = uri_avt
        this.uri_cover = uri_cover
        this.password = password
        this.follow_users = follow_users
        this.follow_storys = follow_storys
    }

}

class User_Get{
    var id_user: String = ""
    var user: User = User()
    constructor()
    constructor(id_user: String, user: User) {
        this.id_user = id_user
        this.user = user
    }


}

