package com.example.app_mxh_manga.homePage.component.bookcase.component

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.app_mxh_manga.R
import com.example.app_mxh_manga.component.GetData
import com.example.app_mxh_manga.component.ModeDataSaveSharedPreferences
import com.example.app_mxh_manga.homePage.Activity_homePage
import com.example.app_mxh_manga.homePage.component.bookcase.component.adapter.Adapter_RV_Story_History
import com.example.app_mxh_manga.module.History_Get


class Fragment_history : Fragment() {


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
        val view =  inflater.inflate(R.layout.fragment_rv, container, false)
        val tv = view.findViewById<TextView>(R.id.tv)
        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)
        tv.text = "Lịch sử đọc truyện"
        tv.visibility = View.VISIBLE
        val listHistory = ArrayList<History_Get>()

        val id_user = ModeDataSaveSharedPreferences(activity as Activity_homePage).getIdUser()

        val adapter = Adapter_RV_Story_History(listHistory)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(
            view.context,
            LinearLayoutManager.VERTICAL,
            false
        )
        GetData().getHistoryByUser(id_user){
            if (it!=null){
                listHistory.addAll(it)
                adapter.notifyDataSetChanged()
            }else{
                tv.text = "Bạn cần đọc 1 chương truyện thì mới có lịch sử đọc"
            }
        }


        return view
    }

}