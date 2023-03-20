package com.example.app_mxh_manga.homePage.component.readmanga

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.app_mxh_manga.R
import com.example.app_mxh_manga.component.GetData
import com.example.app_mxh_manga.component.adaters.Adapter_VP2_ListFragment
import com.example.app_mxh_manga.homePage.Activity_homePage
import com.example.app_mxh_manga.homePage.component.readmanga.component.common.Adapter_RV_story_name_chapter
import com.example.app_mxh_manga.homePage.component.readmanga.component.common.Adapter_RV_story_sortview
import com.example.app_mxh_manga.homePage.component.readmanga.component.common.Adapter_sort_order
import com.example.app_mxh_manga.homePage.component.search.Activity_Search
import com.example.app_mxh_manga.module.Story_Get
import com.example.app_mxh_manga.module.User_Get
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class Fragment_ReadManga : Fragment() {
    private lateinit var adpterVP2: Adapter_VP2_ListFragment
    private lateinit var homePage: Activity_homePage
    private lateinit var search: View
    private lateinit var ib_rating: ImageButton
    private lateinit var view1: View
    private lateinit var view2: View
    private lateinit var view3: View
    private lateinit var view4: View


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
        search = view.findViewById<View>(R.id.search)
        ib_rating = view.findViewById<ImageButton>(R.id.ib_rating)
        view1 = view.findViewById(R.id.view1)
        view2 = view.findViewById(R.id.view2)
        view3 = view.findViewById(R.id.view3)
        view4 = view.findViewById(R.id.view4)

        homePage = activity as Activity_homePage

        view1()
        view2()
        view3()
        view4()

        addEvent()
        return view
    }







    private fun view1() {
        val tv_title = view1.findViewById<TextView>(R.id.tv_title)
        val btn_showAll = view1.findViewById<Button>(R.id.btn_showAll)
        val rv = view1.findViewById<RecyclerView>(R.id.recyclerView)
        tv_title.text = "Truyện đề xuất"
        val listStory = ArrayList<Story_Get>()



        btn_showAll.setOnClickListener {

        }





    }
    private fun view2() {
        val tv_title = view2.findViewById<TextView>(R.id.tv_title)
        val btn_showAll = view2.findViewById<Button>(R.id.btn_showAll)
        val rv = view2.findViewById<RecyclerView>(R.id.recyclerView)
        tv_title.text = "Truyện mới cập nhật"
        val listStory = ArrayList<Story_Get>()
        val adapter = Adapter_RV_story_name_chapter(listStory)
        rv.layoutManager = GridLayoutManager(
            context,
            2,
            GridLayoutManager.HORIZONTAL,
            false
        )
        rv.adapter = adapter
        GetData().getStoryIDNewUpdate { strings ->
            if (strings!=null){
                for (i in strings){
                    GetData().getStoryByID(i){
                        if (it!=null && it.story.status){
                            listStory.add(it)
                            adapter.notifyDataSetChanged()
                        }
                    }
                }
            }
        }




        btn_showAll.setOnClickListener {

        }
    }
    private fun view3() {
        val tv_title = view3.findViewById<TextView>(R.id.tv_title)
        val btn_showAll = view3.findViewById<Button>(R.id.btn_showAll)
        val rv = view3.findViewById<RecyclerView>(R.id.recyclerView)
        tv_title.text = "Truyện nhiều lượt xem"
        val listStory = ArrayList<Story_Get>()
        val adapter = Adapter_RV_story_sortview(listStory)
        rv.adapter = adapter
        rv.layoutManager = GridLayoutManager(
            context,
            2,
            GridLayoutManager.HORIZONTAL,
            false
        )

        btn_showAll.setOnClickListener {

        }
        GetData().getStorySort_view {
            if (it!=null){
                for (i in it){
                    if (i.story.status){
                        listStory.add(i)
                        adapter.notifyDataSetChanged()
                    }
                }
            }
        }




    }
    private fun view4() {
        val tv_title = view4.findViewById<TextView>(R.id.tv_title)
        val btn_showAll = view4.findViewById<Button>(R.id.btn_showAll)
        val rv = view4.findViewById<RecyclerView>(R.id.recyclerView)
        tv_title.text = "Top thành viên"
        val listUser = ArrayList<User_Get>()
        val adapter = Adapter_sort_order(listUser)
        rv.adapter = adapter
        rv.layoutManager = LinearLayoutManager(
            view4.context,
            LinearLayoutManager.VERTICAL,
            false
        )
        GetData().getUserSortScore {
            if (it!=null){
                for (i in 0..4){
                    listUser.add(it[i])
                }
                adapter.notifyDataSetChanged()
            }
        }


        btn_showAll.setOnClickListener {

        }

    }

    private fun addEvent() {
        search.setOnClickListener {
            startActivity(Intent(homePage, Activity_Search::class.java))
        }
        ib_rating.setOnClickListener {

        }
    }

}