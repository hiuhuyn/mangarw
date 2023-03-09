package com.example.app_mxh_manga.homePage.component.home

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView

import com.example.app_mxh_manga.R
import com.example.app_mxh_manga.component.adaters.Adapter_LV_Posts_Other
import com.example.app_mxh_manga.homePage.Activity_homePage
import com.example.app_mxh_manga.homePage.component.search.Activity_Search
import com.example.app_mxh_manga.module.DataTest
import com.example.app_mxh_manga.module.Posts


class Fragment_Home : Fragment() {
    private lateinit var listView: ListView
    private lateinit var activity_homePage: Activity_homePage
    private var listPosts = ArrayList<Posts>()
    private lateinit var viewSearch: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }
    init {
        listPosts.addAll(DataTest().getPosts())
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        viewSearch = view.findViewById(R.id.view_search)
        listView = view.findViewById(R.id.listView)
        activity_homePage = activity as Activity_homePage
        val listSpinner = arrayListOf("Xóa")
        val adapterLv = Adapter_LV_Posts_Other(activity_homePage, listPosts)
        listView.adapter = adapterLv

        viewSearch.setOnClickListener {
            startActivity(Intent(activity_homePage, Activity_Search::class.java))
        }

        return view
    }



    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Fragment_Home().apply {
                arguments = Bundle().apply {

                }
            }
    }
}