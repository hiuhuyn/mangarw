package com.example.app_mxh_manga.homePage.component.common.showStory

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.app_mxh_manga.R
import com.example.app_mxh_manga.component.GetData
import com.example.app_mxh_manga.component.OnItemClick
import com.example.app_mxh_manga.component.adaters.Adapter_RV_Story
import com.example.app_mxh_manga.homePage.component.common.Fragment_LV_Story
import com.example.app_mxh_manga.homePage.component.common.KEY_STORY
import com.example.app_mxh_manga.homePage.component.common.search.component.Adapter_Lv_Search_Story
import com.example.app_mxh_manga.homePage.component.story.IDStory
import com.example.app_mxh_manga.module.Story
import com.example.app_mxh_manga.module.Story_Get

const val LIST_ID_STORY = "list_id_story"

class Fragment_RV_Story: Fragment() {
    private var list = ArrayList<String>()
    private lateinit var adapter: Adapter_RV_Story
    private var listStoryGet = ArrayList<Story_Get>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            list = it.getStringArrayList(LIST_ID_STORY) as ArrayList<String>
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_rv, container, false)
        val rv = view.findViewById<RecyclerView>(R.id.recyclerView)
        adapter = Adapter_RV_Story(listStoryGet, object : OnItemClick{
            override fun onItemClick(position: Int) {
                val i = Intent(activity, Activity_showStory::class.java)
                val bundle = Bundle()
                bundle.putString(IDStory, listStoryGet[position].id_story)
                i.putExtras(bundle)
                startActivity(i)
            }
        })
        for (item in list){
            GetData().getStoryByID(item){
                if (it != null) {
                    listStoryGet.add(it)
                    adapter.notifyDataSetChanged()
                }
            }
        }
        rv.adapter = adapter
        rv.layoutManager = LinearLayoutManager(
            context,
            LinearLayoutManager.VERTICAL,
            false
        )
        return view
    }
    companion object {
        @JvmStatic
        fun newInstance(listStory: ArrayList<String>) =
            Fragment_RV_Story().apply {
                arguments = Bundle().apply {
                    putStringArrayList(LIST_ID_STORY, listStory)
                }
            }
    }

}