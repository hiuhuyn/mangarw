package com.example.app_mxh_manga.homePage.component.profile

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ListView
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.RecyclerView
import com.example.app_mxh_manga.R
import com.example.app_mxh_manga.component.GetData_id
import com.example.app_mxh_manga.component.Int_Uri
import com.example.app_mxh_manga.component.ModeDataSaveSharedPreferences
import com.example.app_mxh_manga.component.adaters.Adapter_LV_iv_string

import com.example.app_mxh_manga.homePage.Activity_homePage
import com.example.app_mxh_manga.homePage.component.profile.component.Activity_EditProfile
import com.example.app_mxh_manga.homePage.component.profile.component.Activity_profile
import com.example.app_mxh_manga.homePage.component.story.Activity_creative_zone
import com.example.app_mxh_manga.login.Activity_Login
import com.example.app_mxh_manga.module.User
import com.example.app_mxh_manga.module.system.Image_String
import com.google.android.material.floatingactionbutton.FloatingActionButton


class Fragment_Profile : Fragment() {
    private lateinit var user: User
    private lateinit var activityHomepage: Activity_homePage
    private lateinit var toolbar: Toolbar
    private lateinit var iv_avt: ImageView
    private lateinit var floatBtn: FloatingActionButton
    private lateinit var listView: ListView
    val listIv_Str = arrayListOf(
        Image_String(Int_Uri().convertUri(R.drawable.ic_baseline_account_box_40), "Hồ sơ"),
        Image_String(Int_Uri().convertUri(R.drawable.ic_baseline_monetization_on_40), "Nạp tiền"),
        Image_String(Int_Uri().convertUri(R.drawable.ic_baseline_priority_high_40), "Giới thiệu chúng tôi"),
        Image_String(Int_Uri().convertUri(R.drawable.ic_baseline_logout_24), "Đăng xuất"),
    )
    init {

    }

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
        val view = inflater.inflate(R.layout.fragment_profile, container, false)
        activityHomepage = activity as Activity_homePage
        val idUser = ModeDataSaveSharedPreferences(activityHomepage).getIdUser()
        user = GetData_id().getUser(idUser)

        toolbar = view.findViewById(R.id.toolbar)
        iv_avt = view.findViewById(R.id.iv_avt)
        floatBtn = view.findViewById(R.id.float_btn)
        listView = view.findViewById(R.id.listView)


        listView.adapter = Adapter_LV_iv_string(activityHomepage, listIv_Str)


        addEvent()
        return view
    }

    private fun addEvent() {
        floatBtn.setOnClickListener {
            startActivity(Intent(activityHomepage, Activity_creative_zone::class.java))
        }
        iv_avt.setOnClickListener {
            val i = Intent(activityHomepage, Activity_profile::class.java)
            val bundle = Bundle()
            bundle.putInt("id_user",user.id_user)
            i.putExtras(bundle)
            startActivity(i)
        }
        toolbar.setOnMenuItemClickListener {
            when(it.itemId){
                R.id.item_edtProfile -> {
                    startActivity(Intent(context, Activity_EditProfile::class.java))
                }
                R.id.item_setting -> {

                }


            }
            true
        }
        listView.setOnItemClickListener { parent, view, position, id ->
            when(position){
                0 -> {
                    val i = Intent(activityHomepage, Activity_profile::class.java)
                    val bundle = Bundle()
                    bundle.putInt("id_user",user.id_user)
                    i.putExtras(bundle)
                    startActivity(i)
                }
                1 -> {

                }
                2 -> {

                }
                3 -> {
                    // dăng xuất
                    ModeDataSaveSharedPreferences(activityHomepage).logout()
                    startActivity(Intent(context, Activity_Login::class.java))
                    activityHomepage.finish()
                }
            }
        }


    }




}