package com.example.app_mxh_manga.homePage.component.profile

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.widget.Toolbar
import com.example.app_mxh_manga.IDUSER
import com.example.app_mxh_manga.R
import com.example.app_mxh_manga.component.*
import com.example.app_mxh_manga.component.adaters.Adapter_LV_iv_string

import com.example.app_mxh_manga.homePage.Activity_homePage
import com.example.app_mxh_manga.homePage.component.profile.component.Activity_EditProfile
import com.example.app_mxh_manga.homePage.component.profile.component.Activity_profile
import com.example.app_mxh_manga.homePage.component.story.Activity_creative_zone
import com.example.app_mxh_manga.login.Activity_Login
import com.example.app_mxh_manga.messenger.Activity_chat
import com.example.app_mxh_manga.module.User_Get
import com.example.app_mxh_manga.module.system.Image_String
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.squareup.picasso.Picasso


class Fragment_Profile : Fragment() {
    private lateinit var user: User_Get
    private lateinit var activityHomepage: Activity_homePage
    private lateinit var toolbar: Toolbar
    private lateinit var iv_avt: ImageView
    private lateinit var floatBtn: FloatingActionButton
    private lateinit var listView: ListView
    private lateinit var idUser:String
    private lateinit var tv_name:TextView
    private lateinit var tv_level:TextView
    private lateinit var tv_followers:TextView
    private lateinit var tv_following:TextView
    private lateinit var notification: Notification
    private lateinit var dialog: Dialog
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
        toolbar = view.findViewById(R.id.toolbar)
        iv_avt = view.findViewById(R.id.iv_avt)
        floatBtn = view.findViewById(R.id.float_btn)
        listView = view.findViewById(R.id.listView)
        tv_name = view.findViewById(R.id.tv_name)
        tv_level = view.findViewById(R.id.tv_level)
        tv_followers = view.findViewById(R.id.tv_followers)
        tv_following = view.findViewById(R.id.tv_following)
        activityHomepage = activity as Activity_homePage
        listView.adapter = Adapter_LV_iv_string(activityHomepage, listIv_Str)

        notification = Notification(view.context)
        dialog = notification.dialogLoading("Loading...")
        dialog.show()
        idUser = ModeDataSaveSharedPreferences(activityHomepage).getIdUser()
        GetData().getUserByID(idUser){ userGet ->
            dialog.dismiss()
            if (userGet != null) {
                tv_name.text = userGet.user.name
                tv_following.setText("${NumberData().formatInt(userGet.user.follow_users.size)}")
                NumberData().formatLevel(userGet.user.score){ score, color ->
                    tv_level.text = "LV $score"
                    tv_level.setBackgroundColor(Color.parseColor(color))
                }
                GetData().usersFollowUser(idUser){
                    if (it!=null){
                        tv_followers.setText(NumberData().formatInt(it.size))
                    }
                }

                GetData().getImage(userGet.user.uri_avt){ uri ->
                    if (uri != null){
                        Picasso.with(activityHomepage).load(uri).into(iv_avt)
                    }
                }

                addEvent()
            }else{
                notification.toastCustom("Mất kết nối!").show()
            }
        }
        return view
    }

    private fun addEvent() {
        floatBtn.setOnClickListener {
            startActivity(Intent(activityHomepage, Activity_creative_zone::class.java))
        }
        iv_avt.setOnClickListener {
            val i = Intent(activityHomepage, Activity_profile::class.java)
            val bundle = Bundle()
            bundle.putString(IDUSER, idUser)
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
                R.id.item_messenger -> {
                    startActivity(Intent(context, Activity_chat::class.java))
                }
            }
            true
        }
        listView.setOnItemClickListener { parent, view, position, id ->
            when(position){
                0 -> {
                    val i = Intent(activityHomepage, Activity_profile::class.java)
                    val bundle = Bundle()
                    bundle.putString(IDUSER, idUser)
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