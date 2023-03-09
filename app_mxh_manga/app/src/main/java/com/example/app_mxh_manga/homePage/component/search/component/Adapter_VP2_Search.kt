package com.example.app_mxh_manga.homePage.component.search.component

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class Adapter_VP2_Search(val activity: AppCompatActivity): FragmentStateAdapter(activity) {
    override fun getItemCount(): Int {
        return 4
    }

    override fun createFragment(position: Int): Fragment {
        when(position){
            0 -> {
                return Fragment_Search_StoryNovel()
            }
            1 -> {
                return Fragment_Search_StoryManga()
            }
            2 -> {
                return Fragment_Search_Post()
            }
            3 -> {
                return Fragment_Search_Author()
            }
            else -> {
                return Fragment_Search_StoryNovel()
            }
        }
    }
}