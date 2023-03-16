package com.example.app_mxh_manga.homePage.component.search

import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.widget.SearchView
import androidx.viewpager2.widget.ViewPager2
import com.example.app_mxh_manga.R
import com.example.app_mxh_manga.homePage.component.common.search.component.*
import com.example.app_mxh_manga.homePage.component.search.component.*
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class Activity_Search : AppCompatActivity() {
    private lateinit var searchView: SearchView
    private lateinit var tabLayout: TabLayout
    private lateinit var tv_huy: TextView
    private lateinit var viewPager2: ViewPager2
    private lateinit var adapterVP2: Adapter_VP2_Search
    private var search = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        searchView = findViewById(R.id.search_view)
        tabLayout = findViewById(R.id.tabLayout)
        tv_huy = findViewById(R.id.tv_huy)
        viewPager2 = findViewById(R.id.viewPage2)
        adapterVP2 = Adapter_VP2_Search(this)
        tv_huy.setOnClickListener {
            finish()
        }
        viewPager2.adapter = adapterVP2


        val tabMediator = TabLayoutMediator(tabLayout, viewPager2){ tab, position ->
            when(position){
                0 -> {
                    tab.setText("Truyện chữ")
                }
                1 -> {
                    tab.setText("Truyện tranh")
                }
                2 -> {
                    tab.setText("Bài viết")
                }
                3 -> {
                    tab.setText("Người dùng")
                }
            }
        }
        tabMediator.attach()
        viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                updateViewPager2(search)
            }
        })


        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText != null){
                    search = newText
                    updateViewPager2(newText)
                }
                return false
            }

        })






    }

    private fun updateViewPager2(search: String) {
        val fragment = supportFragmentManager.findFragmentByTag("f${viewPager2.currentItem}")

        if(fragment is Fragment_Search_StoryNovel){
            fragment.updateDataSearch(search)
        }
        if(fragment is Fragment_Search_StoryManga){
            fragment.updateDataSearch(search)
        }
        if(fragment is Fragment_Search_Post){
            fragment.updateDataSearch(search)
        }
        if(fragment is Fragment_Search_Author){
            fragment.updateDataSearch(search)
        }
//        adapterVP2.notifyDataSetChanged()
    }
}