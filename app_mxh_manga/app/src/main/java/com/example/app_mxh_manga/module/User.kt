package com.example.app_mxh_manga.module

import android.net.Uri
import android.os.Parcel
import android.os.Parcelable
import java.util.Date



class User{
    var id_user: Int
    var name: String
    var birthday: Date
    var sex: Boolean
    var email: String
    var score: Int
    var point: Int
    var story: String
    var uri_avt: Uri
    var uri_cover: Uri
    var password: String

    constructor(
        id_user: Int,
        name: String,
        birthday: Date,
        sex: Boolean,
        email: String,
        score: Int,
        point: Int,
        story: String,
        uri_avt: Uri,
        uri_cover: Uri,
        password: String
    ) {
        this.id_user = id_user
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
    }
}