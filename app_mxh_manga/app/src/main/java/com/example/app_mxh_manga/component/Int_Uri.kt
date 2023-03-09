package com.example.app_mxh_manga.component

import android.net.Uri

class Int_Uri {
    fun convertUri( drawable :Int ): Uri{
        val packageName = "com.example.app_mxh_manga"
        val imageUri = Uri.parse("android.resource://" + packageName + "/" + drawable)
        return imageUri
    }
}