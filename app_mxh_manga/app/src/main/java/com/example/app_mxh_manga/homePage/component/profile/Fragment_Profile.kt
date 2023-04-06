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
import androidx.recyclerview.widget.RecyclerView
import com.example.app_mxh_manga.IDUSER
import com.example.app_mxh_manga.R
import com.example.app_mxh_manga.component.*
import com.example.app_mxh_manga.component.adaters.Adapter_RV_iv_string

import com.example.app_mxh_manga.homePage.Activity_homePage
import com.example.app_mxh_manga.homePage.component.profile.component.Activity_EditProfile
import com.example.app_mxh_manga.homePage.component.profile.component.Activity_profile
import com.example.app_mxh_manga.homePage.component.story.Activity_creative_zone
import com.example.app_mxh_manga.login.Activity_Login
import com.example.app_mxh_manga.messenger.Activity_chat
import com.example.app_mxh_manga.module.User_Get
import com.example.app_mxh_manga.module.system.Image_String
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputLayout
import com.squareup.picasso.Picasso


class Fragment_Profile : Fragment() {
    private lateinit var user: User_Get
    private lateinit var activityHomepage: Activity_homePage
    private lateinit var toolbar: Toolbar
    private lateinit var iv_avt: ImageView
    private lateinit var rv: RecyclerView
    private lateinit var idUser:String
    private lateinit var tv_name:TextView
    private lateinit var tv_level:TextView
    private lateinit var tv_followers:TextView
    private lateinit var tv_following:TextView
    private lateinit var notification: Notification
    private lateinit var dialog: Dialog
    val listIv_Str = arrayListOf(
        Image_String(Int_Uri().convertUri(R.drawable.ic_baseline_account_box_40), "Hồ sơ"),
        Image_String(Int_Uri().convertUri(R.drawable.ic_baseline_border_color_40), "Viết truyện"),
        Image_String(Int_Uri().convertUri(R.drawable.ic_baseline_monetization_on_40), "Nạp tiền"),
        Image_String(Int_Uri().convertUri(R.drawable.ic_baseline_priority_high_40), "Giới thiệu chúng tôi"),
        Image_String(Int_Uri().convertUri(R.drawable.ic_baseline_logout_24), "Đăng xuất"),
    )


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
        rv = view.findViewById(R.id.rv)
        tv_name = view.findViewById(R.id.tv_name)
        tv_level = view.findViewById(R.id.tv_level)
        tv_followers = view.findViewById(R.id.tv_followers)
        tv_following = view.findViewById(R.id.tv_following)
        activityHomepage = activity as Activity_homePage
        idUser = ModeDataSaveSharedPreferences(activityHomepage).getIdUser()
        notification = Notification(view.context)
        rv.adapter = Adapter_RV_iv_string(activityHomepage, listIv_Str, object : OnItemClick{
            override fun onItemClick(position: Int) {
                when(position){
                    0 -> {
                        val i = Intent(activityHomepage, Activity_profile::class.java)
                        val bundle = Bundle()
                        bundle.putString(IDUSER, idUser)
                        i.putExtras(bundle)
                        startActivity(i)
                    }
                    1 -> {
                        startActivity(Intent(activityHomepage, Activity_creative_zone::class.java))
                    }
                    2 -> {
                        Toast.makeText(context, "Chức năng chưa được cài đặt", Toast.LENGTH_SHORT).show()
                    }
                    3 -> {
                        Toast.makeText(context, "Chức năng chưa được cài đặt", Toast.LENGTH_SHORT).show()
                    }
                    4 -> {
                        // dăng xuất
                        ModeDataSaveSharedPreferences(activityHomepage).logout()
                        startActivity(Intent(context, Activity_Login::class.java))
                        activityHomepage.finish()
                    }
                }
            }
        })

        return view
    }

    override fun onStart() {
        super.onStart()
        dialog = notification.dialogLoading("Loading...")
        dialog.show()

        GetData().getUserByID(idUser){ userGet ->
            dialog.dismiss()
            if (userGet != null) {
                user = userGet
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


    }


    private fun addEvent() {
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
                R.id.item_changePassword ->{
                    changePassword()
                }
            }
            true
        }



    }

    private fun changePassword() {
        val dialog = Dialog(activityHomepage)
        dialog.setContentView(R.layout.layout_dialog_change_password)
        dialog.setCancelable(false)
        val cancel = dialog.findViewById<Button>(R.id.btn_cancel)
        val ok = dialog.findViewById<Button>(R.id.btn_ok)
        val ti_password_old = dialog.findViewById<TextInputLayout>(R.id.ti_password_old)
        val ti_password_new = dialog.findViewById<TextInputLayout>(R.id.ti_password_new)
        cancel.setOnClickListener {
            dialog.dismiss()
        }

        ok.setOnClickListener {
            val passOld = ti_password_old.editText?.text.toString().trim()
            val passNew = ti_password_new.editText?.text.toString().trim()
            if (passNew.isEmpty()){
                ti_password_new.error = "Mật khẩu không được bỏ trống!"
            }else{
                ti_password_new.error = null
                ti_password_new.isErrorEnabled = false
            }
            if (passOld != user.user.password){
                ti_password_old.error = "Mật khẩu không chính xác!"
            }else if ( passOld == user.user.password && passNew.isNotEmpty()){
                UpdateData().password_user(user.id_user, passNew){
                    if(it){
                        dialog.dismiss()
                        Notification(activityHomepage).toastCustom("Đổi mật khẩu thành công").show()
                    }
                }
            }

        }
        dialog.show()
    }


}