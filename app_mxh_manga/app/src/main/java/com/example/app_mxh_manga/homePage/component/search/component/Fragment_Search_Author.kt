package com.example.app_mxh_manga.homePage.component.search.component

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import androidx.fragment.app.Fragment
import com.example.app_mxh_manga.R
import com.example.app_mxh_manga.homePage.component.common.search.component.Adapter_Lv_Search_Author
import com.example.app_mxh_manga.homePage.component.profile.component.Activity_profile
import com.example.app_mxh_manga.homePage.component.search.Activity_Search
import com.example.app_mxh_manga.module.DataTest
import com.example.app_mxh_manga.module.User

class Fragment_Search_Author: Fragment() {
    private lateinit var adapter: Adapter_Lv_Search_Author
    private var listUser = ArrayList<User>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }
    init {
        listUser.addAll(DataTest().getUsers())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =inflater.inflate(R.layout.fragment_listview, container, false)
        val listView =view.findViewById<ListView>(R.id.listView)
        val activitySearch = activity as Activity_Search
        adapter = Adapter_Lv_Search_Author(activitySearch, listUser)
        listView.adapter = adapter

        listView.setOnItemClickListener { parent, view, position, id ->
            val i = Intent(activitySearch, Activity_profile::class.java)
            val bundle = Bundle()
            bundle.putInt("id_user", listUser[position].id_user)
            i.putExtras(bundle)
            startActivity(i)

        }


        return view
    }
    fun updateDataSearch(search: String){
        adapter.filter.filter(search)
    }
}