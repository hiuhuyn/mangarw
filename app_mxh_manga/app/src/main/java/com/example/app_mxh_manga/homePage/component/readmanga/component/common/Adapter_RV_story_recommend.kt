package com.example.app_mxh_manga.homePage.component.readmanga.component.common

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.app_mxh_manga.R
import com.example.app_mxh_manga.component.OnItemClick
import com.example.app_mxh_manga.module.Story



class Adapter_RV_story_recommend(val list: ArrayList<Story>, val size: Int ,val onItemClick: OnItemClick): RecyclerView.Adapter<Adapter_RV_story_recommend.StoryItemHolder>() {
    class StoryItemHolder(itemView: View): RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoryItemHolder {
        return StoryItemHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_story_name1, parent, false))
    }

    override fun onBindViewHolder(holder: StoryItemHolder, position: Int) {
        holder.itemView.apply {
            val imageView = findViewById<ImageView>(R.id.imageView)
            val tv_name = findViewById<TextView>(R.id.tv_name)

            imageView.setImageURI(list[position].cover_image)
            tv_name.setText(list[position].name)
            imageView.setOnClickListener {
                onItemClick.onItemClick(position)
            }
        }
    }

    override fun getItemCount(): Int {
        if (list.size <= size){
            return list.size
        }else{
            return size
        }
    }


}
