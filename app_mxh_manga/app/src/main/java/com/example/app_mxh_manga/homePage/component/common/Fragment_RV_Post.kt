package com.example.app_mxh_manga.homePage.component.common

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.app_mxh_manga.R
import com.example.app_mxh_manga.component.GetData_id
import com.example.app_mxh_manga.component.ModeDataSaveSharedPreferences
import com.example.app_mxh_manga.component.OnclickPost
import com.example.app_mxh_manga.homePage.Activity_homePage
import com.example.app_mxh_manga.module.Posts


class Fragment_RV_Post : Fragment() {
    private var listPost = ArrayList<Posts>()
    private var idUser: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            listPost.addAll(GetData_id().getListpost(it.getInt("idUser", 0)))
            idUser = it.getInt("idUser")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_rv, container, false)
        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)
        var listSpinner = ArrayList<String>()

        recyclerView.layoutManager = LinearLayoutManager(
            context,
            LinearLayoutManager.VERTICAL,
            false
        )
        recyclerView.adapter = Adapter_RV_Post(listPost)


        return view
    }

    companion object {
        @JvmStatic
        fun newInstance(idUser: Int) =
            Fragment_RV_Post().apply {
                arguments = Bundle().apply {
                    putInt("idUser", idUser)
                }
            }
    }
}