package com.example.app_mxh_manga.homePage.component.readmanga

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.viewpager2.widget.ViewPager2
import com.example.app_mxh_manga.R
import com.example.app_mxh_manga.component.adaters.Adapter_VP2_ListFragment
import com.example.app_mxh_manga.homePage.Activity_homePage
import com.example.app_mxh_manga.homePage.component.search.Activity_Search
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class Fragment_ReadManga : Fragment() {
    private lateinit var adpterVP2: Adapter_VP2_ListFragment
    private lateinit var homePage: Activity_homePage

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }
    init {

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_read_manga, container, false)
        val tabLayout = view.findViewById<TabLayout>(R.id.tabLayout)
        val search = view.findViewById<View>(R.id.search)
        val ib_rating = view.findViewById<ImageButton>(R.id.ib_rating)
        val viewPager2 = view.findViewById<ViewPager2>(R.id.viewPage2)
        homePage = activity as Activity_homePage

        val listFragment = ArrayList<Fragment>()

        val adpterVP2 = Adapter_VP2_ListFragment(activity as Activity_homePage,listFragment )

        viewPager2.adapter = adpterVP2

        val tabMedia = TabLayoutMediator(tabLayout, viewPager2){ tab, position ->
            when(position){
                0 -> {
                    tab.setText("Đề xuất")
                }
                1 -> {
                    tab.setText("Truyện chữ")
                }
                2 -> {
                    tab.setText("Truyện tranh")
                }
            }
        }
        tabMedia.attach()
        search.setOnClickListener {
            startActivity(Intent(homePage, Activity_Search::class.java))
        }
        ib_rating.setOnClickListener {

        }



        return view
    }

}