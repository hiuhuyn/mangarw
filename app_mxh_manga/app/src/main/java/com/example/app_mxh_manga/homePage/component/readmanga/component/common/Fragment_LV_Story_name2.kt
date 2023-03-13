package com.example.app_mxh_manga.homePage.component.readmanga.component.common

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import com.example.app_mxh_manga.R
import com.example.app_mxh_manga.homePage.Activity_homePage
import com.example.app_mxh_manga.module.Story


private const val ARG_TYPE = "type"


class Fragment_LV_Story_name2 : Fragment() {
    private var type:Int = 0
    private var listStory = ArrayList<Story>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            type = it.getInt(ARG_TYPE)
            // lấy list ở đây
//            listStory.addAll(DataTest().getStory())
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_listview, container, false)
        val listView = view.findViewById<ListView>(R.id.listView)
        val activity = activity as Activity_homePage

        val adapter = Adapter_LV_Story_Name2(activity, listStory, 4)
        listView.adapter = adapter
        listView.setOnItemClickListener { parent, view, position, id ->
            //
        }


        return view
    }

    companion object {
        @JvmStatic
        fun newInstance(type: Int) =
            Fragment_LV_Story_name2().apply {
                arguments = Bundle().apply {
                    putInt(ARG_TYPE, type)
                }
            }
    }
}