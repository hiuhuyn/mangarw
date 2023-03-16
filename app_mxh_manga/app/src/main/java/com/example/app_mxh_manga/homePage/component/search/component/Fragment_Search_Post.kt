package com.example.app_mxh_manga.homePage.component.search.component

import android.app.ProgressDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import androidx.fragment.app.Fragment
import com.example.app_mxh_manga.R
import com.example.app_mxh_manga.component.GetData
import com.example.app_mxh_manga.component.Notification

import com.example.app_mxh_manga.component.adaters.Adapter_LV_Posts_Other
import com.example.app_mxh_manga.homePage.component.search.Activity_Search
import com.example.app_mxh_manga.module.Post_Get
import com.example.app_mxh_manga.module.Posts

class Fragment_Search_Post: Fragment() {
    private lateinit var adapter: Adapter_LV_Posts_Other
    private var listPosts = ArrayList<Post_Get>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

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
        adapter = Adapter_LV_Posts_Other(activitySearch, listPosts)
        listView.adapter = adapter
        val dialog = Notification(view.context).dialogLoading("Loading...")
        dialog.show()

        GetData().getAllPosts {
            dialog.dismiss()
            if (it != null) {
                for (i in it){
                    listPosts.add(i)
                    adapter.update(listPosts)
                }
            }else{
                Notification(view.context).toastCustom("Không có nội dung để hiển thị").show()
            }
        }
        return view
    }
    fun updateDataSearch(search: String){
        adapter.filter.filter(search)
    }


}