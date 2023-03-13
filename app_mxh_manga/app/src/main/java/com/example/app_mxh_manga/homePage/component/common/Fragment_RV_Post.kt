package com.example.app_mxh_manga.homePage.component.common

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.app_mxh_manga.IDUSER
import com.example.app_mxh_manga.R
import com.example.app_mxh_manga.component.GetData
import com.example.app_mxh_manga.component.OnItemClick
import com.example.app_mxh_manga.module.Post_Get
import com.example.app_mxh_manga.module.Posts
import com.google.android.gms.common.api.GoogleApi

const val IDPOST = "id_post"
class Fragment_RV_Post : Fragment() {
    private var listPost = ArrayList<Post_Get>()
    private var listIdPost= ArrayList<String>()
    private var idUser: String = ""
    private lateinit var adapter: Adapter_RV_Post
    private lateinit var tv_noPost: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            idUser = it.getString(IDUSER).toString()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_rv, container, false)
        tv_noPost = view.findViewById(R.id.tv_noPost)
        tv_noPost.visibility = View.VISIBLE


        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(
            context,
            LinearLayoutManager.VERTICAL,
            false
        )
        adapter = Adapter_RV_Post(listPost,idUser, object : OnItemClick{
            override fun onItemClick(position: Int) {

            }

        })
        GetData().getPost_IdUser(idUser){ postGet ->
            if (postGet != null) {
                listPost.addAll(postGet)
                adapter.notifyDataSetChanged()
                if (postGet.size <= 0){
                    tv_noPost.visibility = View.VISIBLE
                }else{
                    tv_noPost.visibility = View.GONE
                }
            }
        }
        recyclerView.adapter = adapter
        return view
    }

    companion object {
        @JvmStatic
        fun newInstance(idUser: String) =
            Fragment_RV_Post().apply {
                arguments = Bundle().apply {
                    putString(IDUSER, idUser)
                }
            }
    }
}