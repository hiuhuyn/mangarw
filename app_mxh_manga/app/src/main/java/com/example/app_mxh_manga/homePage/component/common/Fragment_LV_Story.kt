package com.example.app_mxh_manga.homePage.component.common

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import androidx.fragment.app.Fragment
import com.example.app_mxh_manga.R
import com.example.app_mxh_manga.homePage.Activity_homePage
import com.example.app_mxh_manga.homePage.component.common.search.component.Adapter_Lv_Search_Story
import com.example.app_mxh_manga.module.Story
import com.example.app_mxh_manga.module.User

import java.util.Objects

const val KEY_STORY = "liststorykey"
const val ONE_STORY = "oneStory"


class Fragment_LV_Story : Fragment() {
    private var list = ArrayList<Story>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                list = it.getParcelableArrayList(KEY_STORY, Story::class.java) as ArrayList<Story>
            }else{
                list = it.getParcelableArrayList<Story>(KEY_STORY) as ArrayList<Story>
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_listview, container, false)
        val listView = view.findViewById<ListView>(R.id.listView)
        val adapter = context?.let { Adapter_Lv_Search_Story(it, list) }
        listView.adapter = adapter
        return view
    }
    companion object {
        @JvmStatic
        fun newInstance(listStory: ArrayList<Story>) =
            Fragment_LV_Story().apply {
                arguments = Bundle().apply {
                    putParcelableArrayList(KEY_STORY, listStory )
                }
            }
    }
}