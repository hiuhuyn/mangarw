package com.example.app_mxh_manga.homePage.component.readmanga.component

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.app_mxh_manga.homePage.component.readmanga.Fragment_ReadManga

class Adapter_VP2_ReadManga(val activity: Fragment_ReadManga): FragmentStateAdapter(activity) {
    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        when(position){
            0 -> {
                return Fragment_Recommend()
            }
            1 -> {
                return Fragment_Story_ForYou.newInstance(TYPE_NOVEL)
            }
            2 -> {
                return Fragment_Story_ForYou.newInstance(TYPE_MANGA)
            }
            else -> {
                return Fragment_Recommend()
            }

        }
    }
}