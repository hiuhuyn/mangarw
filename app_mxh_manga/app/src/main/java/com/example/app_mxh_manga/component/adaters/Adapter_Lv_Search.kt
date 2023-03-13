package com.example.app_mxh_manga.homePage.component.common.search.component

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Filter
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.app_mxh_manga.R
import com.example.app_mxh_manga.component.GetData
import com.example.app_mxh_manga.component.NumberData
import com.example.app_mxh_manga.component.adaters.Adapter_RV_Genre
import com.example.app_mxh_manga.homePage.component.common.showStory.Activity_showStory
import com.example.app_mxh_manga.homePage.component.story.IDStory
import com.example.app_mxh_manga.module.*
import com.squareup.picasso.Picasso
import java.util.*
import kotlin.collections.ArrayList

class Adapter_Lv_Search_Story(val activity: Context, val list:ArrayList<Story_Get>): ArrayAdapter<Story>(activity, R.layout.item_story){
    private var listFilter: ArrayList<Story_Get> = ArrayList()
    init {
        listFilter.addAll(list)
    }
    fun update(list: ArrayList<Story_Get>){
        listFilter.addAll(list)
        notifyDataSetChanged()
    }
    override fun getCount(): Int {
        return listFilter.size
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = LayoutInflater.from(activity).inflate(R.layout.item_story, parent, false)
        val img_story = view.findViewById<ImageView>(R.id.imageStory)
        val tv_nameStory = view.findViewById<TextView>(R.id.tv_nameStory)
        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView_genre)
        val tv_describe = view.findViewById<TextView>(R.id.tv_describe)
        val tv_user = view.findViewById<TextView>(R.id.tv_user)
        val tv_numberFollow = view.findViewById<TextView>(R.id.tv_numberFollow)
        val tv_numberLike = view.findViewById<TextView>(R.id.tv_numberLike)
        val tv_numberChapter = view.findViewById<TextView>(R.id.tv_numberChapter)
        val story = listFilter[position].story
        GetData().getImage(listFilter[position].story.cover_image){
            if (it!=null){
                Picasso.with(context).load(it).into(img_story)
            }
        }

        tv_nameStory.setText(story.name)
        tv_describe.setText(story.describe)

        GetData().getUserByID(story.id_user){
            if (it!=null){
                tv_user.setText(it.user.name)
            }else{
                tv_user.setText("null")
            }
        }
        GetData().userFollowStory(listFilter[position].id_story){
            if (it!=null){
                Log.d("GetData", "userFollowStory ${it.size}")
                tv_numberFollow.setText(NumberData().formatInt(it.size))
            }else{
                tv_numberFollow.setText("0")
            }
        }

        GetData().getChapterByIdStory(listFilter[position].id_story){
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
        val listGenre_get = ArrayList<Genre_Get>()
        val adapterRvGenre = Adapter_RV_Genre(listGenre_get)
        for (item in story.genres){
            GetData().getGenreByIdGenre(item){
                if (it!=null){
                    listGenre_get.add(it)
                    adapterRvGenre.notifyDataSetChanged()
                }
            }
        }

        recyclerView.adapter = adapterRvGenre
        recyclerView.layoutManager = LinearLayoutManager(
            parent.context,
            LinearLayoutManager.HORIZONTAL,
            false)

        view.setOnClickListener {
            val i = Intent(activity, Activity_showStory::class.java)
            val bundle = Bundle()
            bundle.putString(IDStory, listFilter[position].id_story)
            i.putExtras(bundle)
            activity.startActivity(i)
        }
        return view
    }

    override fun getFilter(): Filter {
        return object : Filter(){
            override fun performFiltering(constraint: CharSequence?): FilterResults? {
                val strSearch = constraint.toString()
                val results = FilterResults()

                if (strSearch.isEmpty()){
                    results.values = list
                }else{
                    val list2 = ArrayList<Story_Get>()
                    for (item in listFilter){
                        if (item.story.name.toLowerCase(Locale.ROOT).contains(strSearch.toLowerCase(Locale.ROOT))){
                            list2.add(item)
                        }
                    }
                    results.values = list2
                }
                return results
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                listFilter.clear()
                if (results != null) {
                    listFilter.addAll(results.values as Collection<Story_Get>)
                }

                notifyDataSetChanged()
            }

        }
    }
}


class Adapter_Lv_Search_Author(val activity: AppCompatActivity, val list: ArrayList<User_Get>): ArrayAdapter<User>(activity, R.layout.item_author_search) {
    private var listFilter: ArrayList<User_Get> = ArrayList()
    fun update(list: ArrayList<User_Get>){
        listFilter.addAll(list)
        notifyDataSetChanged()
    }

    init {
        listFilter.addAll(list)
    }
    override fun getCount(): Int {
        return listFilter.size
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = activity.layoutInflater.inflate(R.layout.item_author_search, parent, false)
        val imageAvt = view.findViewById<ImageView>(R.id.imageAvt)
        val tv_name = view.findViewById<TextView>(R.id.tv_nameUser)
        val tvSumStory = view.findViewById<TextView>(R.id.tv_sumStory)
        GetData().getImage(listFilter[position].user.uri_avt){
            Picasso.with(context).load(it).into(imageAvt)
        }
        tv_name.setText(listFilter[position].user.name)
        GetData().getStoryByIdUser(list[position].id_user){
            if (it!=null){
                tvSumStory.setText("Tổng cộng ${NumberData().formatInt(it.size)} tác phẩm.")
            }
        }



        return view
    }
    override fun getFilter(): Filter {
        return object : Filter(){
            override fun performFiltering(constraint: CharSequence?): FilterResults? {
                val strSearch = constraint.toString()
                val results = FilterResults()

                if (strSearch.isEmpty()){
                    results.values = list
                }else{
                    val list2 = ArrayList<User_Get>()
                    for (item in listFilter){
                        if (item.user.name.lowercase(Locale.ROOT).contains(strSearch.lowercase(Locale.ROOT))){
                            list2.add(item)
                        }
                    }
                    results.values = list2
                }
                return results
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                listFilter.clear()
                if (results != null) {
                    listFilter.addAll(results.values as Collection<User_Get>)
                }

                notifyDataSetChanged()
            }

        }
    }
}

