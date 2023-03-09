package com.example.app_mxh_manga.homePage.component.common.search.component

import android.content.Context
import android.content.Intent
import android.os.Bundle
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
import com.example.app_mxh_manga.component.GetData_id
import com.example.app_mxh_manga.component.GetNumberData
import com.example.app_mxh_manga.component.adaters.Adapter_RV_Genre
import com.example.app_mxh_manga.homePage.component.common.showStory.Activity_showStory
import com.example.app_mxh_manga.module.Genre
import com.example.app_mxh_manga.module.Story
import com.example.app_mxh_manga.module.User
import java.util.*
import kotlin.collections.ArrayList

class Adapter_Lv_Search_Story(val activity: Context, val list:ArrayList<Story>): ArrayAdapter<Story>(activity, R.layout.item_story){
    private var listFilter: ArrayList<Story> = ArrayList()
    init {
        listFilter.addAll(list)
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

        img_story.setImageURI(listFilter[position].cover_image)
        tv_nameStory.setText(listFilter[position].name)
        tv_describe.setText(listFilter[position].describe)

        tv_user.setText(GetData_id().getUser(listFilter[position].id_user).name)
        tv_numberFollow.setText("${GetNumberData().numberFollow_Story(listFilter[position].id_story)}")
        tv_numberLike.setText("${GetNumberData().numberLike_Story(listFilter[position].id_story)}")
        tv_numberChapter.setText("${GetNumberData().numberChapter(listFilter[position].id_story)}")
        val adapterRvGenre = Adapter_RV_Genre(GetData_id().getListGenre(listFilter[position].id_story))
        recyclerView.adapter = adapterRvGenre
        recyclerView.layoutManager = LinearLayoutManager(
            parent.context,
            LinearLayoutManager.HORIZONTAL,
            false)

        view.setOnClickListener {
            val i = Intent(activity, Activity_showStory::class.java)
            val bundle = Bundle()
            bundle.putParcelable("story", listFilter[position])
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
                    val list2 = ArrayList<Story>()
                    for (item in listFilter){
                        if (item.name.toLowerCase(Locale.ROOT).contains(strSearch.toLowerCase(Locale.ROOT))){
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
                    listFilter.addAll(results.values as Collection<Story>)
                }

                notifyDataSetChanged()
            }

        }
    }
}


class Adapter_Lv_Search_Author(val activity: AppCompatActivity, val list: ArrayList<User>): ArrayAdapter<User>(activity, R.layout.item_author_search) {
    private var listFilter: ArrayList<User> = ArrayList()
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
        imageAvt.setImageURI(listFilter[position].uri_avt)
        tv_name.setText(listFilter[position].name)
        tvSumStory.setText("Tổng cộng ${getNumberStory(listFilter[position].id_user)} tác phẩm.")


        return view
    }

    private fun getNumberStory(idUser: Int): Int {

        return 19
    }

    override fun getFilter(): Filter {
        return object : Filter(){
            override fun performFiltering(constraint: CharSequence?): FilterResults? {
                val strSearch = constraint.toString()
                val results = FilterResults()

                if (strSearch.isEmpty()){
                    results.values = list
                }else{
                    val list2 = ArrayList<User>()
                    for (item in listFilter){
                        if (item.name.toLowerCase(Locale.ROOT).contains(strSearch.toLowerCase(Locale.ROOT))){
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
                    listFilter.addAll(results.values as Collection<User>)
                }

                notifyDataSetChanged()
            }

        }
    }
}

