package com.example.app_mxh_manga.homePage.component.genre

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.app_mxh_manga.R
import com.example.app_mxh_manga.component.GetData
import com.example.app_mxh_manga.component.OnItemClick
import com.example.app_mxh_manga.component.TAGGET
import com.example.app_mxh_manga.component.adaters.Adapter_RV_Story
import com.example.app_mxh_manga.homePage.Activity_homePage
import com.example.app_mxh_manga.homePage.component.common.showStory.Activity_showStory
import com.example.app_mxh_manga.homePage.component.genre.component.Adapter_Spinner_Genre
import com.example.app_mxh_manga.homePage.component.genre.component.Adapter_Spinner_TextView
import com.example.app_mxh_manga.homePage.component.story.IDStory
import com.example.app_mxh_manga.module.Genre_Get
import com.example.app_mxh_manga.module.Story_Get

// TODO: Rename parameter arguments, choose names that match


class Fragment_Genre : Fragment() {
    private lateinit var activityHomepage: Activity_homePage
    private lateinit var spinner_genre: Spinner
    private lateinit var spinner_sort: Spinner
    private lateinit var rv_story: RecyclerView
    private lateinit var tv_descGenre: TextView
    private lateinit var adapterRvStory: Adapter_RV_Story
    private var listGenre = ArrayList<Genre_Get>()
    private var listStoryAll = ArrayList<Story_Get>()
    private var listStory_Genre = ArrayList<Story_Get>()
    private var listSort = ArrayList<String>()
    init {
        listSort = arrayListOf(
            "Mới cập nhật",
            "Lượt xem",
            "Số chương",
            "Lượt like"
        )
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_genre, container, false)
        activityHomepage = activity as Activity_homePage
        val adapterSpinnerGenre = Adapter_Spinner_Genre(view.context, listGenre)
        GetData().getAllGenre {
            if(it!=null){
                listGenre.addAll(it)
                adapterSpinnerGenre.notifyDataSetChanged()
            }
        }
        spinner_genre = view.findViewById(R.id.spinner_genre)
        spinner_sort = view.findViewById(R.id.spinner_sort)
        tv_descGenre = view.findViewById(R.id.tv_descGenre)
        rv_story = view.findViewById(R.id.rv_story)
        adapterRvStory = Adapter_RV_Story(listStory_Genre)
        GetData().getAllStory {
            if (it!=null){
                for (i in it){
                    if (i.story.status){
                        listStoryAll.add(i)
                        listStory_Genre.add(i)
                    }
                }
                adapterRvStory.notifyDataSetChanged()
            }
        }
        rv_story.adapter = adapterRvStory
        rv_story.layoutManager = LinearLayoutManager(
            view.context,
            LinearLayoutManager.VERTICAL,
            false
        )
        spinner_genre.adapter = adapterSpinnerGenre
        spinner_genre.onItemSelectedListener = object : OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                if (listGenre[position].genre.describe != ""){
                    tv_descGenre.text = listGenre[position].genre.describe
                }else{
                    tv_descGenre.text = listGenre[position].genre.name
                }
                filterStoryByGenre(listGenre[position].id_genre)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }


        spinner_sort.adapter = Adapter_Spinner_TextView(view.context, listSort)
        spinner_sort.onItemSelectedListener = object : OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {

            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

        }



        return view
    }

    private fun filterStoryByGenre(id_genre: String){
        listStory_Genre.clear()
        for ( i in listStoryAll ){
            for (itemGenre in i.story.genres){
                if (itemGenre == id_genre){
                    listStory_Genre.add(i)
                }
            }
        }
        adapterRvStory.notifyDataSetChanged()
    }





}