package com.example.app_mxh_manga.homePage.component.common

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.app_mxh_manga.R
import com.example.app_mxh_manga.component.*
import com.example.app_mxh_manga.component.adaters.Adapter_RV_Chapter
import com.example.app_mxh_manga.component.adaters.Adapter_RV_Content_Image
import com.example.app_mxh_manga.homePage.Activity_homePage
import com.example.app_mxh_manga.homePage.component.story.IDCHAPTER
import com.example.app_mxh_manga.module.Chapter_Get
import com.example.app_mxh_manga.module.History
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlin.collections.ArrayList

class Activity_readingChapter : AppCompatActivity() {
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
    private var timer: CountDownTimer? = null
    private var shouldCountDown = true
    private var id_mainUser = ""
    var checkLikeStory = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reading_chapter)
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
        id_mainUser = ModeDataSaveSharedPreferences(this).getIdUser()

        toolbar.setOnMenuItemClickListener {
            when(it.itemId){
                R.id.item_home -> {
                    startActivity(Intent(this, Activity_homePage::class.java))
                    finishAffinity()
                }
            }
            false
        }
        GetData().getChapterByIDChapter(id_chapter){ chapterGet ->
            if (chapterGet!=null){
                chapter_get = chapterGet
                GetData().getChapterByIdStory(chapter_get.chapter.id_story){
                    if (it!=null){
                        listChapter.addAll(it)
                        addEvent()
                    }
                }
                tv_title.text = chapter_get.chapter.title
                tv_content.text = chapter_get.chapter.content_novel
                rv_content.adapter = Adapter_RV_Content_Image(chapter_get.chapter.images)

                GetData().getStoryByID(chapterGet.chapter.id_story){
                    if (it!=null){
                        toolbar.title = it.story.name
                        if (it.story.type){
                            tv_content.visibility = View.VISIBLE
                            rv_content.visibility = View.GONE
                        }else{
                            rv_content.visibility = View.VISIBLE
                            tv_content.visibility = View.GONE

                        }
                    }
                }
            }
        }
    }

    private fun addEvent() {
        index = listChapter.indexOfFirst {
            it.id_chapter == chapter_get.id_chapter
        }
        checkIndex(index)
        checkLike(index)
        startTimer()
        ib_prev.setOnClickListener {
            if (index < listChapter.size-1){
                index++
                chapter_get = listChapter[index]
                tv_title.text = chapter_get.chapter.title
                rv_content.adapter = Adapter_RV_Content_Image(chapter_get.chapter.images)
                tv_content.text = chapter_get.chapter.content_novel
                checkIndex(index)
                checkLike(index)
                startTimer()
                startTimer()
            }
        }
        ib_next.setOnClickListener {
            if (index > 0){
                index--
                checkIndex(index)
                chapter_get = listChapter[index]
                tv_title.text = chapter_get.chapter.title
                rv_content.adapter = Adapter_RV_Content_Image(chapter_get.chapter.images)
                tv_content.text = chapter_get.chapter.content_novel
                checkLike(index)
                startTimer()
            }

        }
        ib_listChap.setOnClickListener {
            setTheme(R.style.Theme_transparent)
            val bottomSheetDialog = BottomSheetDialog(this)
            bottomSheetDialog.setContentView(R.layout.layout_bottom_rv)
            val recyclerView = bottomSheetDialog.findViewById<RecyclerView>(R.id.recyclerView)
            if (recyclerView != null) {
                recyclerView.adapter = Adapter_RV_Chapter(listChapter, object : OnItemClick{
                    override fun onItemClick(position: Int) {
                        rv_content.adapter = Adapter_RV_Content_Image(listChapter[position].chapter.images)
                        tv_content.text = listChapter[position].chapter.content_novel
                        bottomSheetDialog.dismiss()
                        index=position
                        chapter_get = listChapter[position]
                        tv_title.text = chapter_get.chapter.title
                        checkIndex(index)
                        checkLike(index)
                        startTimer()
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

    private fun checkIndex(index: Int){
        if (index <= 0){
            ib_next.visibility = View.GONE
        }else{
            ib_next.visibility = View.VISIBLE
        }
        if (index >= listChapter.size-1){
            ib_prev.visibility = View.GONE
        }else{
            ib_prev.visibility = View.VISIBLE
        }
    }

    private fun checkLike(index: Int){
        var chapter = listChapter[index]
        checkLikeStory = false
        for (i in chapter.chapter.likes){
            if (id_mainUser == i){
                checkLikeStory = true
                break
            }
        }
        if (checkLikeStory){
            ib_like.setImageResource(R.drawable.ic_baseline_favorite_40)
        }else{
            ib_like.setImageResource(R.drawable.ic_baseline_favorite_border_40)
        }
        ib_like.setOnClickListener {
            if (!checkLikeStory){
                checkLikeStory = true
                ib_like.setImageResource(R.drawable.ic_baseline_favorite_40)
                UpdateData().newLike_Chapter(id_mainUser, chapter.id_chapter){
                    if (it){
                        listChapter[index].chapter.likes.add(id_mainUser)
                    }
                }
            }else{
                checkLikeStory = false
                ib_like.setImageResource(R.drawable.ic_baseline_favorite_border_40)
                UpdateData().removeLike_Chapter(id_mainUser, chapter.id_chapter){
                    if (it){
                        listChapter[index].chapter.likes.remove(id_mainUser)
                    }
                }
            }
        }


    }

    fun startTimer() {
        // Bắt đầu đếm thời gian đọc
        timer?.cancel()
        timer = object : CountDownTimer(10000, 1000) { // đếm ngược 10 giây với khoảng thời gian 1 giây
            override fun onTick(millisUntilFinished: Long) {
                if (shouldCountDown) { // kiểm tra xem có nên đếm thời gian hay không
                    // cập nhật UI
                }
            }
            override fun onFinish() {
                if (shouldCountDown) { // kiểm tra xem có nên đếm thời gian hay không
                    // cập nhật UI
                    UpdateData().updateViewChapter(chapter_get.id_chapter){
                        cancel() // Hủy bỏ timer
                        UpdateData().updateViewStory(chapter_get.chapter.id_story){

                        }
                        UpdateData().updateScoreUser(id_mainUser){

                        }

                        GetData().getHistoryByUser_Story(id_mainUser, chapter_get.chapter.id_story){
                            if (it!=null){
                                val historyGet = it
                                historyGet.history.id_chapter = chapter_get.id_chapter
                                Log.d(TAGGET, "getHistoryByUser_Story id: ${historyGet.id_history}")
                                UpdateData().history(historyGet){
                                }
                            }else{
                                AddData().newHistory(History(id_mainUser, chapter_get.chapter.id_story, chapter_get.id_chapter)){
                                }
                            }
                        }


                    }
                }
            }
        }
        timer?.start()
    }
    override fun onDestroy() {
        super.onDestroy()
        shouldCountDown = false
        timer?.cancel()
    }


}