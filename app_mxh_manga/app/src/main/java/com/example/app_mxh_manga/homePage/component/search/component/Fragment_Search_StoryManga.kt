package com.example.app_mxh_manga.homePage.component.search.component


import android.app.ProgressDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import com.example.app_mxh_manga.R
import com.example.app_mxh_manga.component.GetData
import com.example.app_mxh_manga.homePage.component.common.search.component.Adapter_Lv_Search_Story
import com.example.app_mxh_manga.homePage.component.search.Activity_Search
import com.example.app_mxh_manga.module.Story
import com.example.app_mxh_manga.module.Story_Get


class Fragment_Search_StoryManga : Fragment() {
    private lateinit var adapter: Adapter_Lv_Search_Story
    private var listStory = ArrayList<Story_Get>()

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
        adapter = Adapter_Lv_Search_Story(activitySearch, listStory)

        val progressDialog = ProgressDialog(context)
        progressDialog.setMessage("Loading...")
        progressDialog.setCancelable(false)
        progressDialog.show()

        listView.adapter = adapter
        GetData().getAllStory_Type(false){ itSG ->
            progressDialog.dismiss()
            if (itSG!=null){
                for (item in itSG){
                    if (item.story.status){
                        listStory.add(item)
                    }
                }
                adapter.update(listStory)
            }
        }
        return view
    }
    fun updateDataSearch(search: String){
        adapter.filter.filter(search)
    }


}