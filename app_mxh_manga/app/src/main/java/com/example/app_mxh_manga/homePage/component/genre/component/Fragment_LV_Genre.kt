package com.example.app_mxh_manga.homePage.component.genre.component

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import androidx.fragment.app.Fragment
import com.example.app_mxh_manga.R
import com.example.app_mxh_manga.module.Story

class Fragment_LV_Genre: Fragment() {
    private var id_genre: Int = 0
    private var listStory = ArrayList<Story>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            id_genre = it.getInt("id_genre")

        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_listview, container, false)
        val listView = view.findViewById<ListView>(R.id.listView)
//        listStory.addAll(GetData_id().getListStoryInGenre(id_genre))
//        listView.adapter = Adapter_Lv_Search_Story(view.context, listStory)



        return view
    }
    companion object {
        @JvmStatic
        fun newInstance(id_genre: Int) =
            Fragment_LV_Genre().apply {
                arguments = Bundle().apply {
//                    putInt("id_genre", id_genre)
                }
            }
    }
}