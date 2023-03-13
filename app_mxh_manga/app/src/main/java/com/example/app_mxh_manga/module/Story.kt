package com.example.app_mxh_manga.module

import android.net.Uri
import android.os.Parcel
import android.os.Parcelable
import java.util.Date


class Story{
    var name: String = ""
    var author: String = ""
    var id_user: String = ""
    var describe: String = ""
    var status: Boolean = false
    var cover_image: String = ""
    var type: Boolean = true// t = novel, f = manga
    var genres: ArrayList<String> = ArrayList()
    constructor()



    constructor(
        name: String,
        author: String,
        id_user: String,
        describe: String,
        status: Boolean,
        cover_image: String,
        type: Boolean,
        genres: ArrayList<String>
    ) {
        this.name = name
        this.author = author
        this.id_user = id_user
        this.describe = describe
        this.status = status
        this.cover_image = cover_image
        this.type = type
        this.genres = genres
    }

}
class Story_Get{
    var id_story: String = ""
    var story: Story = Story()
    constructor()
    constructor(id_story: String, story: Story) {
        this.id_story = id_story
        this.story = story
    }


}
