package com.example.app_mxh_manga.homePage.component.readmanga.component.common

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class Adapter_VP2_BXH(val activity: AppCompatActivity): FragmentStateAdapter(activity) {
    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        when(position){
            0 -> {
                return Fragment_LV_Story_name2.newInstance(0)
            }
            1 -> {
                return Fragment_LV_Story_name2.newInstance(1)
            }
            2 -> {
                return Fragment_LV_Story_name2.newInstance(2)
            }
            else -> {
                return Fragment_LV_Story_name2.newInstance(0)
            }
        }
    }
}