package com.example.app_mxh_manga.homePage.component.story

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.RecyclerView
import com.example.app_mxh_manga.R
import com.example.app_mxh_manga.component.GetData
import com.example.app_mxh_manga.component.ModeDataSaveSharedPreferences
import com.example.app_mxh_manga.component.Notification
import com.example.app_mxh_manga.component.OnItemClick
import com.example.app_mxh_manga.module.Story
import com.example.app_mxh_manga.module.Story_Get
import com.google.android.material.floatingactionbutton.FloatingActionButton
const val IDStory = "id_story"
class Activity_creative_zone : AppCompatActivity() {
    private var idUser = ""
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
    private var listStory = ArrayList<Story_Get>()

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

        val dialog = Notification(this).dialogLoading("Loading...")
        dialog.show()
        GetData().getStoryByIdUser(idUser){ storys ->
            dialog.dismiss()
            if ( storys!= null){
                listStory.addAll(storys)
                Log.d("GetData", "creative: ${storys.toArray()}")
                adapterRvStoryCompose = Adapter_RV_Story_compose(listStory, object : OnItemClick{
                    override fun onItemClick(position: Int) {
                        val i = Intent(this@Activity_creative_zone, Activity_MyStory::class.java)
                        val bundle = Bundle()
                        bundle.putString(IDStory, listStory[position].id_story)
                        i.putExtras(bundle)
                        startActivity(i)
                    }
                })
                rv_story.adapter = adapterRvStoryCompose
            }
        }


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