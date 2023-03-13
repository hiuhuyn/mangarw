package com.example.app_mxh_manga.component.adaters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.app_mxh_manga.R
import com.example.app_mxh_manga.component.OnItemClick
import com.example.app_mxh_manga.module.Story


class Adapter_RV_Story(val list: ArrayList<Story>, val onItemClick: OnItemClick) : RecyclerView.Adapter<Adapter_RV_Story.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_story, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.apply {
            val img_story = findViewById<ImageView>(R.id.imageStory)
            val tv_nameStory = findViewById<TextView>(R.id.tv_nameStory)
            val recyclerView = findViewById<RecyclerView>(R.id.recyclerView_genre)
            val tv_describe = findViewById<TextView>(R.id.tv_describe)
            val tv_user = findViewById<TextView>(R.id.tv_user)
            val tv_numberFollow = findViewById<TextView>(R.id.tv_numberFollow)
            val tv_numberLike = findViewById<TextView>(R.id.tv_numberLike)
            val tv_numberChapter = findViewById<TextView>(R.id.tv_numberChapter)

//            img_story.setImageURI(list[position].cover_image)
//            tv_nameStory.setText(list[position].name)
//            tv_describe.setText(list[position].describe)
//
//            tv_user.setText(GetData_id().getUser(list[position].id_user).name)
//            tv_numberFollow.setText("${GetNumberData().numberFollow_Story(list[position].id_story)}")
//            tv_numberLike.setText("${GetNumberData().numberLike_Story(list[position].id_story)}")
//            tv_numberChapter.setText("${GetNumberData().numberChapter(list[position].id_story)}")
//            val adapterRvGenre = Adapter_RV_Genre(GetData_id().getListGenre(list[position].id_story))
//            recyclerView.adapter = adapterRvGenre
//            recyclerView.layoutManager = LinearLayoutManager(
//                context,
//                LinearLayoutManager.HORIZONTAL,
//                false)

            setOnClickListener {

                onItemClick.onItemClick(position)
            }
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

}