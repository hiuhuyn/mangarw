package com.example.app_mxh_manga.homePage.component.search.component

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class Adapter_VP2_Search(val activity: AppCompatActivity): FragmentStateAdapter(activity) {
    override fun getItemCount(): Int {
        return listFragment.size
    }
    private val listFragment = listOf(
        Fragment_Search_StoryNovel(),
        Fragment_Search_StoryManga(),
        Fragment_Search_Post(),
        Fragment_Search_Author()
    )

    override fun createFragment(position: Int): Fragment {
        return listFragment[position]
    }
}