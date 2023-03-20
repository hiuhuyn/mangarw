package com.example.app_mxh_manga.homePage.component.readmanga.component.common

import android.content.Intent
import android.graphics.Color
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
import com.example.app_mxh_manga.component.NumberData
import com.example.app_mxh_manga.homePage.component.profile.component.Activity_profile
import com.example.app_mxh_manga.module.User_Get
import com.squareup.picasso.Picasso

class Adapter_RV_sort_order(val list: ArrayList<User_Get>): RecyclerView.Adapter<Adapter_RV_sort_order.UserItemViewHolder>() {
    class UserItemViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserItemViewHolder {
        return UserItemViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_top_user, parent, false))
    }

    override fun onBindViewHolder(holder: UserItemViewHolder, position: Int) {
        holder.itemView.apply {
            val iv_avt = findViewById<ImageView>(R.id.iv_avt)
            val tv_name = findViewById<TextView>(R.id.tv_name)
            val tv_sort_order = findViewById<TextView>(R.id.tv_sort_order)
            val tv_level = findViewById<TextView>(R.id.tv_level)

            NumberData().formatLevel(list[position].user.score){ score, color ->
                tv_level.text = "LV $score"
                tv_level.setBackgroundColor(Color.parseColor(color))
            }
            tv_name.text = list[position].user.name
            GetData().getImage(list[position].user.uri_avt){
                if (it!=null){
                    Picasso.with(context).load(it).into(iv_avt)
                }
            }
            tv_sort_order.text = "${position+1}"
            val colorList = arrayOf("#D9534F", "#FF00FF0A", "#FFDA00FF", "#FFFFEB3B", "#FFFF9800", "#FF6200EE", "#4cae4c", "#FF03A9F4", "#FF00BCD4", "#C18F8F8F")

            if (position<colorList.size){
                val color  = Color.parseColor(colorList[position])
                tv_sort_order.setTextColor(color)
            }
            iv_avt.setOnClickListener {
                val i = Intent(this.context, Activity_profile::class.java)
                val bundle = Bundle()
                bundle.putString(IDUSER, list[position].id_user)
                i.putExtras(bundle)
                this.context.startActivity(i)
            }
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }


}