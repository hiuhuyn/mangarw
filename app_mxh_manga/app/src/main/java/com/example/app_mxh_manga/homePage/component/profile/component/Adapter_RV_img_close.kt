package com.example.app_mxh_manga.homePage.component.profile.component

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.app_mxh_manga.R
import com.example.app_mxh_manga.component.OnItemClick

class Adapter_RV_img_close(val list: ArrayList<Uri>, val onItemClick: OnItemClick): RecyclerView.Adapter<Adapter_RV_img_close.ItemViewHolder>(){
    class ItemViewHolder(view: View): RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_image_close, parent, false))
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.itemView.apply {
            val iv_uri = findViewById<ImageView>(R.id.iv_uri)
            val ib_close = findViewById<ImageButton>(R.id.ib_close)
            iv_uri.setImageURI(list[position])
            ib_close.setOnClickListener {
                onItemClick.onItemClick(position)
            }
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }


}