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
import com.example.app_mxh_manga.component.OnItemClick
import com.example.app_mxh_manga.component.adaters.Adapter_RV_Story
import com.example.app_mxh_manga.homePage.component.common.Fragment_LV_Story
import com.example.app_mxh_manga.homePage.component.common.KEY_STORY
import com.example.app_mxh_manga.homePage.component.common.search.component.Adapter_Lv_Search_Story
import com.example.app_mxh_manga.module.Story

class Fragment_RV_Story: Fragment() {
    private var list = ArrayList<Story>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
//                list = it.getParcelableArrayList(KEY_STORY, Story::class.java) as ArrayList<Story>
            }else{
//                list = it.getParcelableArrayList<Story>(KEY_STORY) as ArrayList<Story>
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_rv, container, false)
        val rv = view.findViewById<RecyclerView>(R.id.recyclerView)
        val adapter = Adapter_RV_Story(list, object : OnItemClick{
            override fun onItemClick(position: Int) {
                val i = Intent(activity, Activity_showStory::class.java)
                val bundle = Bundle()
//                bundle.putParcelable("story", list[position])
                i.putExtras(bundle)
                startActivity(i)
            }

        })
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
        fun newInstance(listStory: ArrayList<Story>) =
            Fragment_RV_Story().apply {
                arguments = Bundle().apply {
//                    putParcelableArrayList(KEY_STORY, listStory )
                }
            }
    }

}