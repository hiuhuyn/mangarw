package com.example.app_mxh_manga.homePage.component.search.component

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import androidx.fragment.app.Fragment
import com.example.app_mxh_manga.IDUSER
import com.example.app_mxh_manga.R
import com.example.app_mxh_manga.component.GetData
import com.example.app_mxh_manga.homePage.component.common.search.component.Adapter_Lv_Search_Author
import com.example.app_mxh_manga.homePage.component.profile.component.Activity_profile
import com.example.app_mxh_manga.homePage.component.search.Activity_Search
import com.example.app_mxh_manga.module.User
import com.example.app_mxh_manga.module.User_Get

class Fragment_Search_Author: Fragment() {
    private lateinit var adapter: Adapter_Lv_Search_Author
    private var listUser = ArrayList<User_Get>()

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
        val progressDialog = ProgressDialog(context)
        progressDialog.setMessage("Loading...")
        progressDialog.setCancelable(false)
        progressDialog.show()
        adapter = Adapter_Lv_Search_Author(activitySearch, listUser)
        listView.adapter = adapter
        GetData().getAllUser {
            progressDialog.dismiss()
            if (it!=null){
                listUser.addAll(it)
                adapter.update(listUser)
            }
        }
        listView.setOnItemClickListener { parent, view, position, id ->
            val i = Intent(activitySearch, Activity_profile::class.java)
            val bundle = Bundle()
            bundle.putString(IDUSER, listUser[position].id_user)
            i.putExtras(bundle)
            startActivity(i)
        }


        return view
    }
    fun updateDataSearch(search: String){
        adapter.filter.filter(search)
    }
}