package com.example.app_mxh_manga.homePage.component.common

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.app_mxh_manga.R
import com.example.app_mxh_manga.component.GetData
import com.example.app_mxh_manga.component.OnItemClick
import com.example.app_mxh_manga.component.adaters.Adapter_RV_Chapter
import com.example.app_mxh_manga.component.adaters.Adapter_RV_Content_Image
import com.example.app_mxh_manga.homePage.component.story.IDCHAPTER
import com.example.app_mxh_manga.homePage.component.story.IDStory
import com.example.app_mxh_manga.module.Chapter
import com.example.app_mxh_manga.module.Chapter_Get
import com.google.android.material.bottomsheet.BottomSheetDialog

class Activity_readingStory : AppCompatActivity() {
    private lateinit var id_chapter: String
    private lateinit var chapter_get:Chapter_Get
    private lateinit var toolbar: Toolbar
    private lateinit var tv_content: TextView
    private lateinit var rv_content: RecyclerView
    private lateinit var ib_prev: ImageButton
    private lateinit var ib_next: ImageButton
    private lateinit var ib_listChap: ImageButton
    private lateinit var ib_like: ImageButton
    private lateinit var ib_cmt: ImageButton
    private lateinit var tv_title: TextView
    private var listChapter : ArrayList<Chapter_Get> = ArrayList()
    private var index = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reading_story)
        toolbar = findViewById(R.id.toolbar)
        tv_content = findViewById(R.id.tv_content)
        rv_content = findViewById(R.id.recyclerView_content)
        ib_prev = findViewById(R.id.ib_prev)
        ib_next = findViewById(R.id.ib_next)
        ib_listChap = findViewById(R.id.ib_listChap)
        ib_like = findViewById(R.id.ib_like)
        ib_cmt  = findViewById(R.id.ib_cmt)
        tv_title = findViewById(R.id.tv_title)
        val bundle = intent.extras
        if (bundle != null) {
            id_chapter = bundle.getString(IDCHAPTER).toString()
        }else{
            Toast.makeText(this, "Lỗi òi...", Toast.LENGTH_SHORT).show()
            finish()
        }
        toolbar.setNavigationOnClickListener {
            finish()
        }


        GetData().getChapterByIDChapter(id_chapter){ chapter ->
            if (chapter!=null){
                chapter_get = chapter
                tv_title.setText(chapter_get.chapter.title)
                GetData().getChapterByIdStory(chapter_get.chapter.id_story){
                    if (it!=null){
                        listChapter.addAll(it)
                        index = listChapter.indexOfFirst {
                            it.id_chapter == chapter_get.id_chapter
                        }
                        if (index <= 0){
                            ib_next.visibility = View.GONE
                        }else{
                            ib_next.visibility = View.VISIBLE
                        }

                        if (index >= listChapter.size-1){
                            ib_prev.visibility = View.GONE
                        }
                        ib_prev.setOnClickListener {
                            if (index < listChapter.size-1){
                                val i = Intent(this, Activity_readingStory::class.java)
                                val bundle = Bundle()
                                bundle.putString(IDCHAPTER, listChapter[index+1].id_chapter)
                                i.putExtras(bundle)
                                startActivity(i)
                            }
                        }
                        ib_next.setOnClickListener {
                            if (index > 0){
                                val i = Intent(this, Activity_readingStory::class.java)
                                val bundle = Bundle()
                                bundle.putString(IDCHAPTER, listChapter[index-1].id_chapter)
                                i.putExtras(bundle)
                                startActivity(i)
                            }

                        }
                        ib_listChap.setOnClickListener {
                            val bottomSheetDialog = BottomSheetDialog(this)
                            bottomSheetDialog.setContentView(R.layout.layout_bottom_rv)
                            val recyclerView = bottomSheetDialog.findViewById<RecyclerView>(R.id.recyclerView)
                            if (recyclerView != null) {
                                recyclerView.adapter = Adapter_RV_Chapter(listChapter, object : OnItemClick{
                                    override fun onItemClick(position: Int) {
                                        val i = Intent(this@Activity_readingStory, Activity_readingStory::class.java)
                                        val bundle = Bundle()
                                        bundle.putString(IDCHAPTER, listChapter[position].id_chapter)
                                        i.putExtras(bundle)
                                        startActivity(i)
                                    }
                                })
                                recyclerView.layoutManager = LinearLayoutManager(
                                    this,
                                    LinearLayoutManager.VERTICAL,
                                    false
                                )
                            }
                            bottomSheetDialog.show()

                        }


                    }
                }
                GetData().getStoryByID(chapter_get.chapter.id_story){storyGet ->
                    if (storyGet!=null){
                        if (storyGet.story.type){
                            tv_content.visibility = View.VISIBLE
                            rv_content.visibility = View.GONE
                            tv_content.setText(chapter_get.chapter.content_novel)
                        }else{
                            rv_content.visibility = View.VISIBLE
                            tv_content.visibility = View.GONE
                            rv_content.adapter = Adapter_RV_Content_Image(chapter_get.chapter.images)
                        }
                        addEvent()
                        toolbar.title = storyGet.story.name
                    }
                }
            }
        }



    }

    private fun addEvent() {


        ib_like.setOnClickListener {

        }
        ib_cmt.setOnClickListener {
            val bottomSheetDialog = BottomSheetDialog(this)
            bottomSheetDialog.setContentView(R.layout.layout_bottom_sheeet_listview)
            val listView = bottomSheetDialog.findViewById<ListView>(R.id.listView)

        }


    }





}