package com.example.app_mxh_manga.offline

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.app_mxh_manga.R
import com.example.app_mxh_manga.module.Story_Get

class Adapter_story_offline(val list: ArrayList<Story_Get>): RecyclerView.Adapter<Adapter_story_offline.StoryViewHolder>() {
    class StoryViewHolder(view: View): RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoryViewHolder {
        return StoryViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_story_off, parent, false))
    }

    override fun onBindViewHolder(holder: StoryViewHolder, position: Int) {



    }

    override fun getItemCount(): Int {
        return list.size
    }


}