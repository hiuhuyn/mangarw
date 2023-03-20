package com.example.app_mxh_manga.messenger

import android.content.ClipData.Item
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.app_mxh_manga.IDUSER
import com.example.app_mxh_manga.R
import com.example.app_mxh_manga.component.GetData
import com.example.app_mxh_manga.component.ModeDataSaveSharedPreferences
import com.example.app_mxh_manga.component.OnItemClick
import com.example.app_mxh_manga.module.User_Get
import com.squareup.picasso.Picasso

class Adapter_RV_user_horizontal(val list: ArrayList<User_Get>, val onItemClick: OnItemClick): RecyclerView.Adapter<Adapter_RV_user_horizontal.ItemViewHolder>() {
    class ItemViewHolder(view: View): RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_user_horizontal, parent, false))
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.itemView.apply {
            val iv_avt = findViewById<ImageView>(R.id.iv_avt)
            val tv_name = findViewById<TextView>(R.id.tv_name)
            GetData().getImage(list[position].user.uri_avt){
                if (it!=null){
                    Picasso.with(this.context).load(it).into(iv_avt)
                }
            }
            tv_name.text = list[position].user.name
            setOnClickListener {
                onItemClick.onItemClick(position)
            }
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }


}