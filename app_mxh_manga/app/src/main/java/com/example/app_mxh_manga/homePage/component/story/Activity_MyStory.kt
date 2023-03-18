package com.example.app_mxh_manga.homePage.component.story

import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.RecyclerView
import com.example.app_mxh_manga.R
import com.example.app_mxh_manga.component.GetData
import com.example.app_mxh_manga.component.Notification
import com.example.app_mxh_manga.component.OnItemClick
import com.example.app_mxh_manga.component.adaters.Adapter_RV_Chapter
import com.example.app_mxh_manga.homePage.component.common.Activity_readingChapter
import com.example.app_mxh_manga.module.Chapter_Get
import com.example.app_mxh_manga.module.Story_Get
import com.squareup.picasso.Picasso

const val IDCHAPTER = "id_chapter"

class Activity_MyStory : AppCompatActivity() {
    private lateinit var toolbar: Toolbar
    private lateinit var iv_avt: ImageView
    private lateinit var tv_name: TextView
    private lateinit var tv_censorship: TextView
    private lateinit var tv_count_chapter: TextView
    private lateinit var btn_edit: Button
    private lateinit var btn_stats: Button
    private lateinit var btn_newChap: Button
    private lateinit var rv_chap: RecyclerView
    private lateinit var story_get: Story_Get
    private lateinit var id_story: String
    private var listChapter = ArrayList<Chapter_Get>()
    private lateinit var notification: Notification
    private lateinit var dialog: Dialog

    private lateinit var adapterRvChapter: Adapter_RV_Chapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_story)
        toolbar = findViewById(R.id.toolbar)
        iv_avt = findViewById(R.id.iv_avt)
        tv_name = findViewById(R.id.tv_name)
        tv_censorship = findViewById(R.id.tv_censorship)
        tv_count_chapter = findViewById(R.id.tv_count_chapter)
        btn_edit = findViewById(R.id.btn_edit)
        btn_stats = findViewById(R.id.btn_stats)
        btn_newChap = findViewById(R.id.btn_newChap)
        rv_chap = findViewById(R.id.rv_chap)
        notification = Notification(this)
        dialog = notification.dialogLoading("Loading...")
        val bundle = intent.extras
        if (bundle!=null){
            id_story = bundle.getString(IDStory).toString()
        }else{
            notification.toastCustom("Hì như app không nhận được id của truyện\nthử lại đi").show()
            finish()
        }
        dialog.show()
        GetData().getStoryByID(id_story){ storyGet ->
            dialog.dismiss()
            if (storyGet!= null){
                story_get = storyGet
                GetData().getImage(story_get.story.cover_image){
                    if(it!=null){
                        Picasso.with(this).load(it).into(iv_avt)
                    }
                }
                tv_name.setText(story_get.story.name)
                if (story_get.story.type){
                    tv_censorship.setText("Đã kiểm duyệt")
                }else{
                    tv_censorship.setText("Kiểm duyệt")
                }
                addEvent()
            }
        }
        adapterRvChapter = Adapter_RV_Chapter(listChapter, object : OnItemClick {
            override fun onItemClick(position: Int) {
                val i = Intent(this@Activity_MyStory, Activity_readingChapter::class.java)
                val bundle = Bundle()
                bundle.putString(IDCHAPTER, listChapter[position].id_chapter)
                bundle.putString(IDStory, id_story)
                i.putExtras(bundle)
                startActivity(i)

            }
        })

        GetData().getChapterByIdStory(id_story){
            if (it!=null){
                Log.d("GetData", "myStory: ${it.toArray()}")
                listChapter.addAll(it)
                tv_count_chapter.setText("Chapter (${listChapter.size})")
                adapterRvChapter.notifyDataSetChanged()
            }else{
                Log.d("GetData", "myStory: null")
            }
        }

        toolbar.setNavigationOnClickListener {
            finish()
        }
        rv_chap.adapter = adapterRvChapter
    }

    private fun addEvent() {

        btn_newChap.setOnClickListener {
            dialog.show()
            val i = Intent(this, Activity_newChapter::class.java)
            val bundle = Bundle()
            bundle.putString(IDStory, id_story)
            bundle.putBoolean("type", story_get.story.type)
            i.putExtras(bundle)
            startActivity(i)
            dialog.dismiss()
        }

        btn_edit.setOnClickListener {

        }
        btn_stats.setOnClickListener {
            
        }

    }
}