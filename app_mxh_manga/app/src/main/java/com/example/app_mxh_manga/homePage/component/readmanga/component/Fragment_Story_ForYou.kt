package com.example.app_mxh_manga.homePage.component.readmanga.component

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.app_mxh_manga.R
import com.example.app_mxh_manga.component.OnItemClick
import com.example.app_mxh_manga.homePage.Activity_homePage
import com.example.app_mxh_manga.homePage.component.common.showStory.Activity_showStory
import com.example.app_mxh_manga.homePage.component.readmanga.component.common.Adapter_RV_Story_2
import com.example.app_mxh_manga.module.Story

public const val TYPE_STORY = "type_story"
public const val TYPE_NOVEL = "typ_novel"
public const val TYPE_MANGA = "type_manga"


class Fragment_Story_ForYou : Fragment() {
    private lateinit var type:String
    private val listStory = ArrayList<Story>()
    private lateinit var recyclerView: RecyclerView
    private lateinit var activityHomePage : Activity_homePage
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            type = it.getString(TYPE_STORY, TYPE_NOVEL)
        }
        if (type==TYPE_NOVEL){
            // get data
//            listStory.addAll(DataTest().getStory())
        }else if (type == TYPE_MANGA){
            //
//            listStory.addAll(DataTest().getStory())
        }


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_rv, container, false)
        recyclerView = view.findViewById(R.id.recyclerView)
        activityHomePage = activity as Activity_homePage
        recyclerView.adapter = Adapter_RV_Story_2(listStory, object : OnItemClick{
            override fun onItemClick(index: Int) {
                val i = Intent(activityHomePage, Activity_showStory::class.java)
                val bundle = Bundle()
//                bundle.putParcelable("story", listStory[index])
                i.putExtras(bundle)
                startActivity(i)
            }
        })
        val gridLayoutManager = GridLayoutManager(
            this.context,
            2,
            GridLayoutManager.VERTICAL,
            false
        )

        recyclerView.layoutManager = gridLayoutManager


        return view
    }

    companion object {
        @JvmStatic
        fun newInstance(type: String) =
            Fragment_Story_ForYou().apply {
                arguments = Bundle().apply {
                    putString(TYPE_STORY, type)
                }
            }
    }
}