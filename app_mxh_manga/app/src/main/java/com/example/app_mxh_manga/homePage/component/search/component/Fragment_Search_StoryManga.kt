package com.example.app_mxh_manga.homePage.component.search.component


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import com.example.app_mxh_manga.R
import com.example.app_mxh_manga.homePage.component.common.search.component.Adapter_Lv_Search_Story
import com.example.app_mxh_manga.homePage.component.search.Activity_Search
import com.example.app_mxh_manga.module.DataTest
import com.example.app_mxh_manga.module.Story


class Fragment_Search_StoryManga : Fragment() {
    private lateinit var adapter: Adapter_Lv_Search_Story
    private var listStory = ArrayList<Story>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }
    init {
        for (item in  DataTest().getStory() ){
            if(!item.type){
                listStory.add(item)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =inflater.inflate(R.layout.fragment_listview, container, false)
        val listView =view.findViewById<ListView>(R.id.listView)
        val activitySearch = activity as Activity_Search
        adapter = Adapter_Lv_Search_Story(activitySearch, listStory)
        listView.adapter = adapter
        return view
    }
    fun updateDataSearch(search: String){
        adapter.filter.filter(search)
    }


}