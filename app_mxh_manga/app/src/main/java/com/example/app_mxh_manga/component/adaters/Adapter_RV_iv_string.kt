package com.example.app_mxh_manga.component.adaters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.app_mxh_manga.R
import com.example.app_mxh_manga.component.OnItemClick
import com.example.app_mxh_manga.module.system.Image_String

class Adapter_RV_iv_string(context: Context, val list: List<Image_String>, val onItemClick: OnItemClick): RecyclerView.Adapter<Adapter_RV_iv_string.ItemViewHolder>() {

    class ItemViewHolder(view: View): RecyclerView.ViewHolder(view)



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_bs_listview, parent, false))
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.itemView.apply {
            val iv_icon = findViewById<ImageView>(R.id.iv_icon)
            val tv_title = findViewById<TextView>(R.id.tv_title)
            iv_icon.setImageURI(list[position].image)
            tv_title.text = list[position].str
            this.setOnClickListener {
                onItemClick.onItemClick(position)
            }
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }
}