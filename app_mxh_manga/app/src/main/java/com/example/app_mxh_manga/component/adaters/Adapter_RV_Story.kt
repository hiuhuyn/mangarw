package com.example.app_mxh_manga.component.adaters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.app_mxh_manga.R
import com.example.app_mxh_manga.component.GetData
import com.example.app_mxh_manga.component.NumberData
import com.example.app_mxh_manga.component.OnItemClick
import com.example.app_mxh_manga.module.Genre_Get
import com.example.app_mxh_manga.module.Story
import com.example.app_mxh_manga.module.Story_Get
import com.squareup.picasso.Picasso


class Adapter_RV_Story(val list: ArrayList<Story_Get>, val onItemClick: OnItemClick) : RecyclerView.Adapter<Adapter_RV_Story.ViewHolder>() {
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

            GetData().getImage(list[position].story.cover_image){
                if (it!=null){
                    Picasso.with(context).load(it).into(img_story)
                }
            }

            tv_nameStory.setText(list[position].story.name)
            tv_describe.setText(list[position].story.describe)

            GetData().getUserByID(list[position].story.id_user){
                if (it!=null){
                    tv_user.setText(it.user.name)
                }else{
                    tv_user.setText("null")
                }
            }
            GetData().userFollowStory(list[position].id_story){
                if (it!=null){
                    Log.d("GetData", "userFollowStory ${it.size}")
                    tv_numberFollow.setText(NumberData().formatInt(it.size))
                }else{
                    tv_numberFollow.setText("0")
                }
            }

            GetData().getChapterByIdStory(list[position].id_story){
                if (it!=null){
                    tv_numberChapter.setText(NumberData().formatInt(it.size))
                    var count = 0
                    for (item in it){
                        count += item.chapter.likes.size
                    }
                    tv_numberLike.setText(NumberData().formatInt(count))
                }else{
                    tv_numberChapter.setText("0")
                    tv_numberLike.setText("0")
                }
            }

            val listGenre_Get = ArrayList<Genre_Get>()
            val adapterRvGenre = Adapter_RV_Genre(listGenre_Get)
            for (i in list[position].story.genres){
                GetData().getGenreByIdGenre(i){
                    if (it!=null){
                        listGenre_Get.add(it)
                        adapterRvGenre.notifyDataSetChanged()
                    }
                }
            }

            recyclerView.adapter = adapterRvGenre
            recyclerView.layoutManager = LinearLayoutManager(
                context,
                LinearLayoutManager.HORIZONTAL,
                false)

            setOnClickListener {

                onItemClick.onItemClick(position)
            }
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

}