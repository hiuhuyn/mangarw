package com.example.app_mxh_manga.component.adaters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.app_mxh_manga.R
import com.example.app_mxh_manga.module.Genre

class Adapter_RV_Genre(val list: ArrayList<Genre>): RecyclerView.Adapter<Adapter_RV_Genre.NameGenreViewHolder>() {
    class NameGenreViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NameGenreViewHolder {
        return NameGenreViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_genre, parent, false))
    }

    override fun onBindViewHolder(holder: NameGenreViewHolder, position: Int) {
        holder.itemView.apply {
            val tv_genre = findViewById<TextView>(R.id.tv_genre)
            tv_genre.setText(list[position].name)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

}