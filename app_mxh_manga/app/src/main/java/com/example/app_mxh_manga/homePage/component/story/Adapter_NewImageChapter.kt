package com.example.app_mxh_manga.homePage.component.story

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.app_mxh_manga.R
import com.example.app_mxh_manga.component.OnItemClick

class Adapter_NewImageChapter(val list: ArrayList<Uri>, val onItemClick: OnItemClick) : RecyclerView.Adapter<Adapter_NewImageChapter.ItemViewHolder>(){
    class ItemViewHolder(view: View): RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_image_reading, parent, false))
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.itemView.apply {
            val iv_content = findViewById<ImageView>(R.id.iv_content)
            val ib_delete = findViewById<ImageButton>(R.id.ib_delete)

            iv_content.setImageURI(list[position])
            ib_delete.setOnClickListener {
                onItemClick.onItemClick(position)
                notifyDataSetChanged()
            }
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }


}