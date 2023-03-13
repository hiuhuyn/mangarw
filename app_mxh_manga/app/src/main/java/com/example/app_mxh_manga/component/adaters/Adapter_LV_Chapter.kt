package com.example.app_mxh_manga.component.adaters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.app_mxh_manga.R
import com.example.app_mxh_manga.component.NumberData
import com.example.app_mxh_manga.component.OnItemClick
import com.example.app_mxh_manga.module.Chapter
import com.example.app_mxh_manga.module.Chapter_Get
import java.text.SimpleDateFormat

class Adapter_RV_Chapter(val list: ArrayList<Chapter_Get>, val onItemClick: OnItemClick): RecyclerView.Adapter<Adapter_RV_Chapter.ItemViewHolder>() {
    class ItemViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_chapter, parent, false))
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.itemView.apply {
            val tv_title = findViewById<TextView>(R.id.tv_title)
            val tv_date = findViewById<TextView>(R.id.tv_date)
            val tv_numLike = findViewById<TextView>(R.id.tv_numLike)
            val tv_numCmt = findViewById<TextView>(R.id.tv_numCmt)
            val tv_numView = findViewById<TextView>(R.id.tv_numView)
            tv_title.setText(list[position].chapter.title)
            tv_date.setText(NumberData().formatTime(list[position].chapter.date_submit))
            setOnClickListener {
                onItemClick.onItemClick(position)
            }
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

}