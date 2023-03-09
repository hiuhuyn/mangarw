package com.example.app_mxh_manga.homePage.component.story

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.RecyclerView
import com.example.app_mxh_manga.R
import com.example.app_mxh_manga.component.ModeDataSaveSharedPreferences
import com.example.app_mxh_manga.component.OnItemClick
import com.example.app_mxh_manga.module.DataTest
import com.example.app_mxh_manga.module.Story
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.floatingactionbutton.FloatingActionButton

class Activity_creative_zone : AppCompatActivity() {
    private var idUser: Int = 0
    private lateinit var toolbar: Toolbar
    private lateinit var rv_story: RecyclerView
    private lateinit var tv_view: TextView
    private lateinit var tv_like: TextView
    private lateinit var tv_cmt: TextView
    private lateinit var tv_follow: TextView
    private lateinit var tv_drawable: TextView
    private lateinit var tv_income: TextView
    private lateinit var float_btn: FloatingActionButton
    private lateinit var adapterRvStoryCompose: Adapter_RV_Story_compose
    private var listStory = ArrayList<Story>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_creative_zone)
        idUser = ModeDataSaveSharedPreferences(this).getIdUser()
        toolbar = findViewById(R.id.toolbar)
        rv_story = findViewById(R.id.rv_story)
        tv_view = findViewById(R.id.tv_view)
        tv_like = findViewById(R.id.tv_like)
        tv_cmt = findViewById(R.id.tv_cmt)
        tv_follow = findViewById(R.id.tv_follow)
        tv_drawable = findViewById(R.id.tv_drawable)
        tv_income = findViewById(R.id.tv_income)
        float_btn = findViewById(R.id.float_btn)
        listStory.addAll(DataTest().getStory())
        adapterRvStoryCompose = Adapter_RV_Story_compose(listStory, object : OnItemClick{
            override fun onItemClick(position: Int) {
                val i = Intent(this@Activity_creative_zone, Activity_MyStory::class.java)
                val bundle = Bundle()
                bundle.putParcelable("story", listStory[position])
                i.putExtras(bundle)

                startActivity(i)

            }

        })
        rv_story.adapter = adapterRvStoryCompose

        addEvent()

    }

    private fun addEvent() {
        toolbar.setNavigationOnClickListener {
            finish()
        }
        float_btn.setOnClickListener {
            startActivity(Intent(this, Activity_New_Story::class.java))
        }


    }
}