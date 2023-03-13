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
import com.example.app_mxh_manga.component.GetData
import com.example.app_mxh_manga.component.adaters.Adapter_LV_ShowAll_ImagesPost

class Activity_ShowImagePost : AppCompatActivity() {
    private lateinit var idPosts: String
    private lateinit var adapter:Adapter_LV_ShowAll_ImagesPost

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_image_post)
        val intent = intent
        val bundle = intent.extras

        if(bundle != null){
            idPosts = bundle.getString(IDPOST).toString()
        }else{
            idPosts = ""
        }

        val listView = findViewById<ListView>(R.id.listView)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        val listUri: ArrayList<Uri> = ArrayList()
        GetData().getPost(idPosts){
            if (it!=null){
                for (item in it.posts.images){
                    GetData().getImage(item){
                        if (it != null) {
                            listUri.add(it)
                            adapter.notifyDataSetChanged()
                        }
                    }
                }
            }
        }
        adapter = Adapter_LV_ShowAll_ImagesPost(this, listUri)
        toolbar.setNavigationOnClickListener {
            finish()
        }
        listView.adapter =adapter


    }
}