package com.example.app_mxh_manga.homePage.component.story

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.app_mxh_manga.R
import com.example.app_mxh_manga.component.GetNumberData
import com.example.app_mxh_manga.component.OnItemClick
import com.example.app_mxh_manga.module.Story


class Adapter_RV_Story_compose(val list: ArrayList<Story>, val onItemClick: OnItemClick): RecyclerView.Adapter<Adapter_RV_Story_compose.ItemViewHolder>(){
    class ItemViewHolder(view: View): RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_story_compose, parent, false))
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.itemView.apply {
            val iv_avt = findViewById<ImageView>(R.id.iv_avt)
            val tv_name = findViewById<TextView>(R.id.tv_name)
            val tv_censorship = findViewById<TextView>(R.id.tv_censorship)
            val tv_view = findViewById<TextView>(R.id.tv_view)
            val tv_like = findViewById<TextView>(R.id.tv_like)
            val tv_cmt = findViewById<TextView>(R.id.tv_cmt)
            val tv_chap = findViewById<TextView>(R.id.tv_chap)

            iv_avt.setImageURI(list[position].cover_image)
            tv_name.setText(list[position].name)

            if(list[position].status){
                tv_censorship.setText("Đã kiểm duyệt")
            }else{
                tv_censorship.setText("Kiểm duyệt")
            }
            tv_view.setText("${GetNumberData().numberLike_Story(list[position].id_story)}")
            tv_chap.setText("${GetNumberData().numberChapter(list[position].id_story)}")
            tv_like.setText("${GetNumberData().numberLike_Story(list[position].id_story)}")
            tv_cmt.setText("${GetNumberData().numberCmt_Story(list[position].id_story)}")

            setOnClickListener {
                onItemClick.onItemClick(position)
            }


        }
    }

    override fun getItemCount(): Int {
        return list.size
    }


}