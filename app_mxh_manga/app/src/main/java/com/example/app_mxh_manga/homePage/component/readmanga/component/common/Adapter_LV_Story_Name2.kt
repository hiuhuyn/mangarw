package com.example.app_mxh_manga.homePage.component.readmanga.component.common

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.app_mxh_manga.R
import com.example.app_mxh_manga.component.GetNumberData
import com.example.app_mxh_manga.homePage.component.common.showStory.Activity_showStory
import com.example.app_mxh_manga.module.Story

class Adapter_LV_Story_Name2(val activity: AppCompatActivity, val list: ArrayList<Story>, val size: Int): ArrayAdapter<Story>(activity, R.layout.item_story_name2) {
    override fun getCount(): Int {
        if(list.size <= size){
            return list.size
        }else{
            return size
        }
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = activity.layoutInflater.inflate(R.layout.item_story_name2, parent, false)
        val tv_rating = view.findViewById<TextView>(R.id.tv_raring)
        val imageAvt = view.findViewById<ImageView>(R.id.imageAvt)
        val tv_name = view.findViewById<TextView>(R.id.tv_name)
        val tv_likes = view.findViewById<TextView>(R.id.tv_likes)
        tv_rating.setText("${position}")
        imageAvt.setImageURI(list[position].cover_image)
        tv_name.setText(list[position].name)
        tv_likes.setText(GetNumberData().numberLike_Story(list[position].id_story))
        view.setOnClickListener {
            val i = Intent(activity, Activity_showStory::class.java)
            val bundle = Bundle()
            bundle.putParcelable("story", list[position])
            i.putExtras(bundle)
            activity.startActivity(i)
        }

        return view
    }



}