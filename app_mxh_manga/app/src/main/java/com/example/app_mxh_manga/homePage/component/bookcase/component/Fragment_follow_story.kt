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
import com.example.app_mxh_manga.component.Notification
import com.example.app_mxh_manga.component.adaters.Adapter_RV_Story
import com.example.app_mxh_manga.homePage.Activity_homePage
import com.example.app_mxh_manga.module.Story_Get


class Fragment_follow_story : Fragment() {


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
        tv.text = "Truyện theo dõi"
        tv.visibility = View.VISIBLE
        val listStory = ArrayList<Story_Get>()

        val id_user = ModeDataSaveSharedPreferences(activity as Activity_homePage).getIdUser()

        val adapter = Adapter_RV_Story(listStory)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(
            view.context,
            LinearLayoutManager.VERTICAL,
            false
        )
        GetData().getUserByID(id_user){ userGet ->
            if (userGet!=null){
                for (item in userGet.user.follow_storys){
                    GetData().getStoryByID(item){
                        if (it!=null){
                            listStory.add(it)
                            adapter.notifyDataSetChanged()
                        }
                    }
                }
            }else{
                Notification(view.context).toastCustom("Mất kết nối").show()
            }
        }
        return view
    }

}