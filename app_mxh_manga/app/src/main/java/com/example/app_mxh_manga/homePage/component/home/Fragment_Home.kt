package com.example.app_mxh_manga.homePage.component.home

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.app_mxh_manga.offline.CheckNetwork

import com.example.app_mxh_manga.R
import com.example.app_mxh_manga.component.*
import com.example.app_mxh_manga.homePage.Activity_homePage
import com.example.app_mxh_manga.homePage.component.common.Adapter_RV_Post
import com.example.app_mxh_manga.homePage.component.search.Activity_Search
import com.example.app_mxh_manga.module.Post_Get


class Fragment_Home : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var activity_homePage: Activity_homePage
    private var listPosts = ArrayList<Post_Get>()
    private lateinit var viewSearch: View
    private lateinit var adapterLv: Adapter_RV_Post

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
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        viewSearch = view.findViewById(R.id.view_search)
        recyclerView = view.findViewById(R.id.recyclerView)
        activity_homePage = activity as Activity_homePage
        val dialog = Notification(view.context).dialogLoading("Loading...")
        dialog.show()
        val id_user = ModeDataSaveSharedPreferences(activity_homePage).getIdUser()
        adapterLv = Adapter_RV_Post(listPosts, id_user)
        recyclerView.adapter = adapterLv
        GetData().getUserByID(id_user){ userGet ->
            dialog.dismiss()
            if (userGet!=null){
                // lấy các id user đang follow
                for (item in userGet.user.follow_users){
                    GetData().getPost_IdUser(item){ postsGet ->
                        if (postsGet!=null){
                            listPosts.addAll(postsGet)
                            adapterLv.notifyDataSetChanged()
                        }
                    }
                }
            }
        }
        viewSearch.setOnClickListener {
            startActivity(Intent(activity_homePage, Activity_Search::class.java))
        }

        if (!CheckNetwork().isNetworkAvailable(view.context)){
            dialog.dismiss()
            Notification(view.context).toastCustom("Không có mạng").show()
        }
        return view
    }



}