package com.example.app_mxh_manga.homePage.component.bookcase

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.example.app_mxh_manga.R
import com.example.app_mxh_manga.homePage.Activity_homePage
import com.example.app_mxh_manga.homePage.component.bookcase.component.Fragment_history
import com.example.app_mxh_manga.component.adaters.Adapter_VP2_ListFragment
import com.example.app_mxh_manga.homePage.component.bookcase.component.download.Fragment_downloaded_bk
import com.example.app_mxh_manga.homePage.component.common.Fragment_LV_Story
import com.example.app_mxh_manga.module.DataTest
import com.example.app_mxh_manga.module.Story
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator


class Fragment_BookCase : Fragment() {
    private var listStory_saved = ArrayList<Story>()
    private var listStory_download = ArrayList<Story>()

    init {
        listStory_saved.addAll(DataTest().getStory())
        listStory_download.addAll(DataTest().getStory())
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
        val activityHomepage = activity as Activity_homePage
        val listFragment = ArrayList<Fragment>()
        listFragment.add(Fragment_history())
        listFragment.add(Fragment_LV_Story.newInstance(listStory_saved))
        listFragment.add(Fragment_downloaded_bk.newInstance(listStory_download))

        val adapterVP2 = Adapter_VP2_ListFragment(activityHomepage, listFragment)
        viewPager2.adapter = adapterVP2
        val tabMedia = TabLayoutMediator(tabLayout, viewPager2){ tab, position ->
            when(position){
                0 -> {
                    tab.setText("Lịch sử")
                }
                1 -> {
                    tab.setText("Đã lưu")
                }
                2 -> {
                    tab.setText("Tải xuống")
                }
            }
        }
        tabMedia.attach()




        return view
    }
}