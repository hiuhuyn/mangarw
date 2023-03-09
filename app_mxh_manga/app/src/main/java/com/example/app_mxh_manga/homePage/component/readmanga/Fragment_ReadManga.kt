package com.example.app_mxh_manga.homePage.component.readmanga

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.viewpager2.widget.ViewPager2
import com.example.app_mxh_manga.R
import com.example.app_mxh_manga.homePage.Activity_homePage
import com.example.app_mxh_manga.homePage.component.search.Activity_Search
import com.example.app_mxh_manga.homePage.component.readmanga.component.Adapter_VP2_ReadManga
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class Fragment_ReadManga : Fragment() {
    private lateinit var adapterVp2Readmanga: Adapter_VP2_ReadManga
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
        val img_rating = view.findViewById<ImageView>(R.id.img_rating)
        val viewPager2 = view.findViewById<ViewPager2>(R.id.viewPage2)
        homePage = activity as Activity_homePage
        adapterVp2Readmanga = Adapter_VP2_ReadManga(this)
        viewPager2.adapter = adapterVp2Readmanga

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
        img_rating.setOnClickListener {
            // open fragment xem xếp hạng
        }



        return view
    }

}