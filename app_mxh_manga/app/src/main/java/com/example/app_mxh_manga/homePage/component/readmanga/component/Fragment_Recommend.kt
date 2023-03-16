package com.example.app_mxh_manga.homePage.component.readmanga.component

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.app_mxh_manga.R
import com.example.app_mxh_manga.component.OnItemClick
import com.example.app_mxh_manga.component.adaters.Adapter_VP2_ListFragment
import com.example.app_mxh_manga.homePage.Activity_homePage
import com.example.app_mxh_manga.homePage.component.common.showStory.Activity_showStory
import com.example.app_mxh_manga.homePage.component.readmanga.component.common.Adapter_RV_story_item_story_name1
import com.example.app_mxh_manga.module.Story
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator


class Fragment_Recommend : Fragment() {
    private lateinit var story_hot: View
    private lateinit var story_bxh_month: View
    private lateinit var newStory_novel: View
    private lateinit var newStory_manga: View
    private lateinit var activityHomepage: Activity_homePage

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
        val view = inflater.inflate(R.layout.fragment_recommend, container, false)
        story_hot = view.findViewById(R.id.story_hot)
        story_bxh_month = view.findViewById(R.id.story_bxh_month)
        newStory_novel = view.findViewById(R.id.newStory_novel)
        newStory_manga = view.findViewById(R.id.newStory_manga)
        activityHomepage = activity as Activity_homePage
        dataHotStory()
        dataBXH_month()
        dataNewStory_novel()
        dataNewStory_manga()
        return view
    }

    private fun dataNewStory_novel() {
        val tv_titile = newStory_novel.findViewById<TextView>(R.id.tv_title)
        val tv_showAll = newStory_novel.findViewById<TextView>(R.id.tv_showAll)
        val recyclerView = newStory_novel.findViewById<RecyclerView>(R.id.recyclerView)
        tv_titile.setText("Truyện chữ mới")
        tv_showAll.setOnClickListener {

        }
        val listStoryHot:ArrayList<Story> = getStoryHot()

        recyclerView.adapter = Adapter_RV_story_item_story_name1(listStoryHot,9, object : OnItemClick{
            override fun onItemClick(index: Int) {
                val i = Intent(activityHomepage, Activity_showStory::class.java)
                val bundle = Bundle()
//                bundle.putParcelable("story", listStoryHot[index])
                i.putExtras(bundle)
                startActivity(i)
            }
        })
        recyclerView.layoutManager = LinearLayoutManager(
            activityHomepage,
            LinearLayoutManager.HORIZONTAL,
            false
        )
    }
    private fun dataNewStory_manga() {
        val tv_titile = newStory_manga.findViewById<TextView>(R.id.tv_title)
        val tv_showAll = newStory_manga.findViewById<TextView>(R.id.tv_showAll)
        val recyclerView = newStory_manga.findViewById<RecyclerView>(R.id.recyclerView)
        tv_titile.setText("Truyện tranh mới")
        tv_showAll.setOnClickListener {

        }
        val listStoryHot:ArrayList<Story> = getStoryHot()

        recyclerView.adapter = Adapter_RV_story_item_story_name1(listStoryHot,9, object : OnItemClick{
            override fun onItemClick(index: Int) {
                val i = Intent(activityHomepage, Activity_showStory::class.java)
                val bundle = Bundle()
//                bundle.putParcelable("story", listStoryHot[index])
                i.putExtras(bundle)
                startActivity(i)
            }
        })
        recyclerView.layoutManager = LinearLayoutManager(
            activityHomepage,
            LinearLayoutManager.HORIZONTAL,
            false
        )
    }

    private fun dataBXH_month() {
        val tv_titile = story_bxh_month.findViewById<TextView>(R.id.tv_title)
        val tv_showAll = story_bxh_month.findViewById<TextView>(R.id.tv_showAll)
        val viewPager2 = story_bxh_month.findViewById<ViewPager2>(R.id.viewPage2)
        val tabLayout = story_bxh_month.findViewById<TabLayout>(R.id.tabLayout)
        tv_titile.setText("BXH tháng")
        tv_showAll.setOnClickListener {

        }

        val listFragment = ArrayList<Fragment>()
        viewPager2.adapter = Adapter_VP2_ListFragment(activityHomepage, listFragment)
        val tabMedia = TabLayoutMediator(tabLayout, viewPager2){ tab, position ->
            when(position){
                0 -> {
                    tab.setText("Tất cả")
                }
                1 -> {
                    tab.setText("Chuyện chữ")
                }
                2 -> {
                    tab.setText("Truyện tranh")
                }
            }
        }
        tabMedia.attach()


    }

    private fun dataHotStory() {
        val tv_titile = story_hot.findViewById<TextView>(R.id.tv_title)
        val tv_showAll = story_hot.findViewById<TextView>(R.id.tv_showAll)
        val recyclerView = story_hot.findViewById<RecyclerView>(R.id.recyclerView)
        tv_titile.setText("Truyện hot")
        tv_showAll.setOnClickListener {

        }
        val listStoryHot:ArrayList<Story> = getStoryHot()

        recyclerView.adapter = Adapter_RV_story_item_story_name1(listStoryHot,9, object : OnItemClick{
            override fun onItemClick(index: Int) {
                val i = Intent(activityHomepage, Activity_showStory::class.java)
                val bundle = Bundle()
//                bundle.putParcelable("story", listStoryHot[index])
                i.putExtras(bundle)
                startActivity(i)
            }
        })
        recyclerView.layoutManager = GridLayoutManager(
            activityHomepage,
            3,
            GridLayoutManager.VERTICAL,
            false
        )
    }

    private fun getStoryHot(): ArrayList<Story> {
        val list = ArrayList<Story>()
//        list.addAll(DataTest().getStory())
        return list
    }

}