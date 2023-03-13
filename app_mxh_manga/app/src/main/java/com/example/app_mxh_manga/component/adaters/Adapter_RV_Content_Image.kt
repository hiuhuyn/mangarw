package com.example.app_mxh_manga.component.adaters

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.recyclerview.widget.RecyclerView
import com.example.app_mxh_manga.R
import com.example.app_mxh_manga.component.GetData
import com.squareup.picasso.Picasso

class Adapter_RV_Content_Image(val list: ArrayList<String>): RecyclerView.Adapter<Adapter_RV_Content_Image.ViewHolder>() {
    class ViewHolder(view: View): RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_iv_chapter, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.apply {
            val image = findViewById<ImageView>(R.id.iv)
            val progressBar = findViewById<ProgressBar>(R.id.progressBar)
            progressBar.visibility = View.VISIBLE
            GetData().getImage(list[position]){
                if (it!=null){
                    Picasso.with(context).load(it).into(image)
                    progressBar.visibility = View.GONE
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }


}