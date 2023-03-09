package com.example.app_mxh_manga.homePage.component.genre

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.example.app_mxh_manga.R
import com.example.app_mxh_manga.component.GetData_id
import com.example.app_mxh_manga.component.adaters.Adapter_VP2_ListFragment
import com.example.app_mxh_manga.homePage.Activity_homePage
import com.example.app_mxh_manga.homePage.component.genre.component.Fragment_LV_Genre
import com.example.app_mxh_manga.module.Genre
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

// TODO: Rename parameter arguments, choose names that match


class Fragment_Genre : Fragment() {
    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager2: ViewPager2
    private lateinit var activityHomepage: Activity_homePage

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_genre, container, false)
        activityHomepage = activity as Activity_homePage
        tabLayout = view.findViewById(R.id.tabLayout)
        viewPager2 = view.findViewById(R.id.viewPage2)
        var listFragment = ArrayList<Fragment>()
        var listGenre = ArrayList<Genre>()
        listGenre.addAll(GetData_id().getAllGenre())
        for (i in listGenre){
            listFragment.add(Fragment_LV_Genre.newInstance(i.id_genre))
        }


        viewPager2.adapter = Adapter_VP2_ListFragment(activityHomepage, listFragment)

        val tabLayoutMediator = TabLayoutMediator(tabLayout, viewPager2) { tab, viewpager ->
            tab.setText(listGenre[viewpager].name)

        }
        tabLayoutMediator.attach()



        return view
    }


}