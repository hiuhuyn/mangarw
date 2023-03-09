package com.example.app_mxh_manga.module

import android.net.Uri
import android.os.Parcel
import android.os.Parcelable
import java.util.Date


class Story(
    var id_story: Int,
    var name: String,
    var author: String,
    var describe: String,
    var status: Boolean,
    var cover_image: Uri,
    var id_user: Int,
    var type: Boolean // t = novel, f = manga
) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readByte() != 0.toByte(),
        parcel.readParcelable(Uri::class.java.classLoader)!!,
        parcel.readInt(),
        parcel.readByte() != 0.toByte()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id_story)
        parcel.writeString(name)
        parcel.writeString(author)
        parcel.writeString(describe)
        parcel.writeByte(if (status) 1 else 0)
        parcel.writeParcelable(cover_image, flags)
        parcel.writeInt(id_user)
        parcel.writeByte(if (type) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Story> {
        override fun createFromParcel(parcel: Parcel): Story {
            return Story(parcel)
        }

        override fun newArray(size: Int): Array<Story?> {
            return arrayOfNulls(size)
        }
    }
}

class Follow_Story{
    var id_user: Int
    var id_story: Int

    constructor(id_user: Int, id_story: Int) {
        this.id_user = id_user
        this.id_story = id_story
    }
}
