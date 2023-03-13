package com.example.app_mxh_manga.homePage.component.story

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.app_mxh_manga.R
import com.example.app_mxh_manga.component.OnItemClick_2
import com.example.app_mxh_manga.module.Genre
import com.example.app_mxh_manga.module.Genre_Get

class Adapter_RV_Genre2(val list: ArrayList<Genre_Get>, val onItemClick: OnItemClick_2): RecyclerView.Adapter<Adapter_RV_Genre2.ItemViewHolder>(){
    class ItemViewHolder(view: View): RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_genre_2, parent, false))
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.itemView.apply {
            val tv_genre = findViewById<TextView>(R.id.tv_genre)
            tv_genre.setText(list[position].genre.name)
            var checkOnClick = true
            tv_genre.setOnClickListener {
                if (checkOnClick){
                    checkOnClick = false
                    tv_genre.setBackgroundResource(R.drawable.shape_genre_off)
                }else{
                    checkOnClick = true
                    tv_genre.setBackgroundResource(R.drawable.shape_genre_on)
                }
                onItemClick.onItemClick2(position, checkOnClick)
            }
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }


}