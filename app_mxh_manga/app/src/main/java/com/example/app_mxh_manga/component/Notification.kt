package com.example.app_mxh_manga.component

import android.R.layout
import android.app.Dialog
import android.content.Context
import android.os.Build
import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.test.core.app.ApplicationProvider.getApplicationContext
import com.example.app_mxh_manga.R


class Notification(val context: Context) {
    fun toastCustom(text: String): Toast {
        val view = LayoutInflater.from(context).inflate(R.layout.item_toast, null)
        val tv = view.findViewById<TextView>(R.id.tv)
        tv.setText(text)
        val toast = Toast(context)
        toast.duration = Toast.LENGTH_SHORT
        toast.view = view
        return toast
    }
    fun dialogLoading (message: String): Dialog{
        val dialog = Dialog(context)
        dialog.setContentView(R.layout.dialog_loading)
        dialog.findViewById<TextView>(R.id.tv).text = message
        dialog.setCancelable(false)
        dialog.window?.setBackgroundDrawableResource(R.drawable.dialog_bg)
        return dialog
    }

}


