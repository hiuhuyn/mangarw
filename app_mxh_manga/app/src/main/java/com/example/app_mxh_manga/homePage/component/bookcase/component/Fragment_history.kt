package com.example.app_mxh_manga.homePage.component.bookcase.component

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.example.app_mxh_manga.R
import com.example.app_mxh_manga.component.adaters.Adapter_VP2_ListFragment
import com.example.app_mxh_manga.homePage.Activity_homePage
import com.example.app_mxh_manga.module.Story
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator


class Fragment_history : Fragment() {
    private var list_all_story = ArrayList<Story>()
    private var list_novel_story = ArrayList<Story>()
    private var list_manga_story = ArrayList<Story>()


    init {
//        list_all_story.addAll(DataTest().getStory())
//        list_novel_story.addAll(DataTest().getStory())
//        list_manga_story.addAll(DataTest().getStory())
    }

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
        val view =  inflater.inflate(R.layout.fragment_tablayout_vp2, container, false)
        val tabLayout = view.findViewById<TabLayout>(R.id.tabLayout)
        val viewPager2 = view.findViewById<ViewPager2>(R.id.viewPage2)
//        val listFragment = arrayListOf(
////            Fragment_LV_Story.newInstance(list_all_story),
////            Fragment_LV_Story.newInstance(list_novel_story),
////            Fragment_LV_Story.newInstance(list_manga_story),
//        )
//        viewPager2.adapter = Adapter_VP2_ListFragment(activity as Activity_homePage, listFragment)
//
//         val tabLayoutMediator = TabLayoutMediator(tabLayout, viewPager2){tab, position ->
//             when(position){
//                 0 -> {
//                     tab.setText("All")
//                 }
//                 1 -> {
//                     tab.setText("Truyện chữ")
//                 }
//                 2 -> {
//                     tab.setText("Truyện Tranh")
//                 }
//
//             }
//         }
//        tabLayoutMediator.attach()




        return view
    }

}