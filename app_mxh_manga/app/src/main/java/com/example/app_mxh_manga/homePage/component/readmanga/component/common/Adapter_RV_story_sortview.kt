package com.example.app_mxh_manga.homePage.component.readmanga.component.common

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.app_mxh_manga.R
import com.example.app_mxh_manga.component.GetData
import com.example.app_mxh_manga.component.NumberData
import com.example.app_mxh_manga.homePage.component.common.showStory.Activity_showStory
import com.example.app_mxh_manga.homePage.component.story.IDStory
import com.example.app_mxh_manga.module.Story_Get
import com.squareup.picasso.Picasso



class Adapter_RV_story_sortview(val list: ArrayList<Story_Get>): RecyclerView.Adapter<Adapter_RV_story_sortview.StoryItemHolder>() {
    class StoryItemHolder(itemView: View): RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoryItemHolder {
        return StoryItemHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_story_sortview, parent, false))
    }

    override fun onBindViewHolder(holder: StoryItemHolder, position: Int) {
        holder.itemView.apply {
            val imageView = findViewById<ImageView>(R.id.imageView)
            val tv_name = findViewById<TextView>(R.id.tv_name)
            val tv_chapter = findViewById<TextView>(R.id.tv_chapter)
            val tv_countView = findViewById<TextView>(R.id.tv_countView)

            tv_countView.text = NumberData().formatInt(list[position].story.views)

            tv_name.text = list[position].story.name
            GetData().getImage(list[position].story.cover_image){
                if (it!=null){
                    Picasso.with(context).load(it).into(imageView)
                }
            }
            GetData().getChapterByIdStory(list[position].id_story){
                if (it!=null){
                    tv_chapter.text = "${it.first().chapter.title}\n${NumberData().formatTime(it.first().chapter.date_submit)}"
                }
            }
            imageView.setOnClickListener {
                val i = Intent(this.context, Activity_showStory::class.java)
                val bundle = Bundle()
                bundle.putString(IDStory, list[position].id_story)
                i.putExtras(bundle)
                context.startActivity(i)
            }
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }


}