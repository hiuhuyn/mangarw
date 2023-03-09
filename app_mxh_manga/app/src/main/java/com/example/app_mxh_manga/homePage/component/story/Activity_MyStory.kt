package com.example.app_mxh_manga.homePage.component.story

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.RecyclerView
import com.example.app_mxh_manga.R
import com.example.app_mxh_manga.component.GetData_id
import com.example.app_mxh_manga.component.GetNumberData
import com.example.app_mxh_manga.component.OnItemClick
import com.example.app_mxh_manga.component.adaters.Adapter_RV_Chapter
import com.example.app_mxh_manga.module.Chapter
import com.example.app_mxh_manga.module.Story

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
    private lateinit var story: Story
    private var listChapter = ArrayList<Chapter>()
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
        val bundle = intent.extras
        if (bundle!=null){
            story = bundle.getParcelable<Story>("story") as Story
        }else{
            Toast.makeText(this, "Đã xảy ra lỗi", Toast.LENGTH_SHORT).show()
            finish()
        }
        iv_avt.setImageURI(story.cover_image)
        tv_name.setText(story.name)

        if (story.type){
            tv_censorship.setText("Đã kiểm duyệt")
        }else{
            tv_censorship.setText("Kiểm duyệt")
        }

        listChapter.addAll( GetData_id().getListChapter(story.id_story))
        tv_count_chapter.setText("Chapter (${listChapter.size})")
        adapterRvChapter = Adapter_RV_Chapter(listChapter, object : OnItemClick{
            override fun onItemClick(position: Int) {

            }
        })
        rv_chap.adapter = adapterRvChapter

        addEvent()
    }

    private fun addEvent() {
        toolbar.setNavigationOnClickListener {
            finish()
        }
        btn_newChap.setOnClickListener {

        }

        btn_edit.setOnClickListener {

        }
        btn_stats.setOnClickListener {
            
        }

    }
}