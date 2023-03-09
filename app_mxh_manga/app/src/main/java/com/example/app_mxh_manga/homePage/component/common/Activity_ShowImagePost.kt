package com.example.app_mxh_manga.homePage.component.common

import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.net.Uri
import android.os.Build
import android.os.Build.VERSION
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.Toolbar
import com.example.app_mxh_manga.R
import com.example.app_mxh_manga.component.adaters.Adapter_LV_ShowAll_ImagesPost

class Activity_ShowImagePost : AppCompatActivity() {
    private lateinit var listImage: ArrayList<Uri>
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_image_post)
        val intent = intent
        val bundle = intent.extras

        if(bundle != null){
            if (VERSION.SDK_INT >= 33){
                listImage = bundle.getParcelableArrayList("", Uri::class.java) as ArrayList<Uri>
            }else{
                listImage = bundle.getParcelableArrayList<Uri>("listImagePost") as ArrayList<Uri>
            }
        }else{
            listImage = ArrayList()
        }



        val listView = findViewById<ListView>(R.id.listView)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)

        listView.adapter = Adapter_LV_ShowAll_ImagesPost(this, listImage)
        toolbar.setNavigationOnClickListener {
            finish()
        }


    }
}