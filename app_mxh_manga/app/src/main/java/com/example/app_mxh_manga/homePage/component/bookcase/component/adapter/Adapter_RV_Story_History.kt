package com.example.app_mxh_manga.homePage.component.bookcase.component.adapter

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.app_mxh_manga.R
import com.example.app_mxh_manga.component.GetData
import com.example.app_mxh_manga.component.NumberData
import com.example.app_mxh_manga.homePage.component.common.Activity_readingChapter
import com.example.app_mxh_manga.homePage.component.common.showStory.Activity_showStory
import com.example.app_mxh_manga.homePage.component.story.IDCHAPTER
import com.example.app_mxh_manga.homePage.component.story.IDStory
import com.example.app_mxh_manga.module.History_Get
import com.squareup.picasso.Picasso

class Adapter_RV_Story_History(val list: ArrayList<History_Get>): RecyclerView.Adapter<Adapter_RV_Story_History.StoryViewHolder>() {
    class StoryViewHolder(view:View): RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoryViewHolder {
        return StoryViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_story_history, parent, false))
    }

    override fun onBindViewHolder(holder: StoryViewHolder, position: Int) {
        holder.itemView.apply {
            val iv_story = findViewById<ImageView>(R.id.iv_story)
            val tv_name = findViewById<TextView>(R.id.tv_name)
            val tv_timeReading = findViewById<TextView>(R.id.tv_timeReading)
            val btn_continue = findViewById<Button>(R.id.btn_continue)
            GetData().getChapterByIDChapter(list[position].history.id_chapter){
                if (it!=null){
                    btn_continue.text = "Đọc tiếp: ${it.chapter.title}"
                }
            }
            tv_timeReading.text = NumberData().formatTime(list[position].history.time_reading)
            GetData().getStoryByID(list[position].history.id_story){ storyGet ->
                if (storyGet!=null){
                    tv_name.text = storyGet.story.name
                    GetData().getImage(storyGet.story.cover_image){
                        if (it!=null){
                            Picasso.with(context).load(it).into(iv_story)
                        }
                    }
                }
            }
            btn_continue.setOnClickListener {
                val i = Intent(this.context, Activity_readingChapter::class.java)
                val bundle = Bundle()
                bundle.putString(IDCHAPTER, list[position].history.id_chapter)
                i.putExtras(bundle)
                context.startActivity(i)
            }
            iv_story.setOnClickListener {
                val i = Intent(this.context, Activity_showStory::class.java)
                val bundle = Bundle()
                bundle.putString(IDStory, list[position].history.id_story)
                i.putExtras(bundle)
                context.startActivity(i)
            }

        }
    }

    override fun getItemCount(): Int {
        return list.size
    }


}