package com.example.app_mxh_manga.module.system

import android.net.Uri

class Image_String {
    var image: Uri
    var str: String

    constructor(image: Uri, str: String) {
        this.image = image
        this.str = str
    }
}