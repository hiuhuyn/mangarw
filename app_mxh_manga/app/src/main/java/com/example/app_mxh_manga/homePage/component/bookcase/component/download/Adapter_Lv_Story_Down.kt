package com.example.app_mxh_manga.homePage.component.bookcase.component.download

import android.media.Image
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.app_mxh_manga.R
import com.example.app_mxh_manga.module.Story

class Adapter_Lv_Story_Down(val activity: AppCompatActivity, val list: ArrayList<Story>): ArrayAdapter<Story>(activity, R.layout.item_story_download) {
    override fun getCount(): Int {
        return list.size
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = LayoutInflater.from(activity).inflate(R.layout.item_story_download, parent, false)
        val iv_avt = view.findViewById<ImageView>(R.id.imageStory)
        val iv_close = view.findViewById<ImageView>(R.id.iv_close)
        val tv_name = view.findViewById<TextView>(R.id.tv_nameStory)
        val tv_count = view.findViewById<TextView>(R.id.tv_countChapter)
//        iv_avt.setImageURI(list[position].cover_image)
        iv_close.setOnClickListener {

        }
        tv_name.setText(list[position].name)
        tv_count.setText("Đã tải ${9} chap")

        return view
    }
}