package com.example.app_mxh_manga.homePage.component.common

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract.Data
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.app_mxh_manga.R
import com.example.app_mxh_manga.component.GetData_id
import com.example.app_mxh_manga.component.OnItemClick
import com.example.app_mxh_manga.component.adaters.Adapter_RV_Chapter
import com.example.app_mxh_manga.component.adaters.Adapter_RV_Content_Image
import com.example.app_mxh_manga.module.Chapter
import com.example.app_mxh_manga.module.DataTest
import com.google.android.material.bottomsheet.BottomSheetDialog

class Activity_readingStory : AppCompatActivity() {
    private lateinit var chapter:Chapter
    private lateinit var toolbar: Toolbar
    private lateinit var tv_content: TextView
    private lateinit var rv_content: RecyclerView
    private lateinit var ib_prev: ImageButton
    private lateinit var ib_next: ImageButton
    private lateinit var ib_listChap: ImageButton
    private lateinit var ib_like: ImageButton
    private lateinit var ib_cmt: ImageButton
    private lateinit var tv_title: TextView
    private lateinit var listChapter : ArrayList<Chapter>
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
        var idChapter = 0
        if (bundle != null) {
            idChapter = bundle.getInt("id_chapter")

        }

        chapter = GetData_id().getChapter(idChapter)
        tv_title.setText(chapter.title)

        val story = GetData_id().getStory(chapter.id_story)
        toolbar.title = story.name
        listChapter = GetData_id().getListChapter(story.id_story)
        index = listChapter.indexOf(chapter)
//        if (index <= 0){
//            ib_prev.visibility
//        }

        if (story.type){
            tvContent()
        }else{
            rvContent()
        }
        addEvent()


    }

    private fun addEvent() {
        toolbar.setNavigationOnClickListener {
            finish()
        }
        ib_prev.setOnClickListener {
            if (index > 0){
                val i = Intent(this, Activity_readingStory::class.java)
                val bundle = Bundle()
                bundle.putInt("id_chapter", listChapter[index-1].id_chapter)
                i.putExtras(bundle)
                startActivity(i)
            }
        }
        ib_next.setOnClickListener {
            val i = Intent(this, Activity_readingStory::class.java)
            val bundle = Bundle()
            bundle.putInt("id_chapter", listChapter[index+1].id_chapter)
            i.putExtras(bundle)
            startActivity(i)
        }
        ib_like.setOnClickListener {

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
                        bundle.putInt("id_chapter", listChapter[position].id_chapter)
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

        }

        ib_cmt.setOnClickListener {
            val bottomSheetDialog = BottomSheetDialog(this)
            bottomSheetDialog.setContentView(R.layout.layout_bottom_sheeet_listview)
            val listView = bottomSheetDialog.findViewById<ListView>(R.id.listView)



        }


    }

    private fun rvContent(){
        rv_content.visibility = View.VISIBLE
        tv_content.visibility = View.GONE
        val listIV = DataTest().getChapter_manga()
        rv_content.adapter = Adapter_RV_Content_Image(listIV)

    }
    private fun tvContent(){
        tv_content.visibility = View.VISIBLE
        rv_content.visibility = View.GONE
        val chapterNovel = DataTest().getChapter_Novel()
        tv_content.setText(chapterNovel.content)

    }



}