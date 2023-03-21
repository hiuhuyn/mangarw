package com.example.app_mxh_manga.homePage.component.profile.component

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.view.menu.MenuBuilder
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.app_mxh_manga.IDUSER
import com.example.app_mxh_manga.R
import com.example.app_mxh_manga.component.*
import com.example.app_mxh_manga.component.adaters.Adapter_LV_iv_string
import com.example.app_mxh_manga.component.adaters.Adapter_RV_Story

import com.example.app_mxh_manga.homePage.component.common.Adapter_RV_Post
import com.example.app_mxh_manga.messenger.Activity_messenger
import com.example.app_mxh_manga.messenger.IDCHAT
import com.example.app_mxh_manga.module.Chat
import com.example.app_mxh_manga.module.Story_Get
import com.example.app_mxh_manga.module.User_Get
import com.example.app_mxh_manga.module.system.Image_String
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.squareup.picasso.Picasso

class Activity_profile : AppCompatActivity() {
    private lateinit var user: User_Get
    private lateinit var toolbar: Toolbar
    private lateinit var iv_cover: ImageView
    private lateinit var iv_avt: ImageView
    private lateinit var tv_name: TextView
    private lateinit var tv_desc : TextView
    private lateinit var tv_level: TextView
    private lateinit var tv_sex: TextView
    private lateinit var tv_followers: TextView
    private lateinit var tv_following: TextView
    private lateinit var floatBtn: FloatingActionButton
    private lateinit var btn_follow: Button
    private lateinit var idUser: String
    private lateinit var idUserMain: String
    private lateinit var btn_post: Button
    private lateinit var btn_story: Button
    private lateinit var btn_messenger: Button
    private lateinit var tv_empty: TextView

    private lateinit var adapterPost: Adapter_RV_Post
    private lateinit var adapterRvStory: Adapter_RV_Story
    private var checkPost = false
    private var checkStory = false
    private lateinit var rv : RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        toolbar = findViewById(R.id.toolbar)
        iv_cover = findViewById(R.id.iv_cover)
        iv_avt = findViewById(R.id.iv_avt)
        tv_name = findViewById(R.id.tv_name)
        tv_desc  = findViewById(R.id.tv_desc)
        tv_level = findViewById(R.id.tv_level)
        tv_sex = findViewById(R.id.tv_sex)
        tv_followers = findViewById(R.id.tv_followers)
        tv_following = findViewById(R.id.tv_following)
        floatBtn = findViewById(R.id.float_btn)
        btn_follow = findViewById(R.id.btn_follow)
        rv = findViewById(R.id.recyclerView)
        btn_post  = findViewById(R.id.btn_post)
        btn_story= findViewById(R.id.btn_story)
        tv_empty= findViewById(R.id.tv_empty)
        btn_messenger= findViewById(R.id.btn_messenger)
        tv_empty.setText(". . .")
        tv_empty.visibility= View.VISIBLE

        val bundle = intent.extras
        if (bundle != null) {
            idUser = bundle.getString(IDUSER).toString()
        }else{
            idUser = ""
        }
        idUserMain = ModeDataSaveSharedPreferences(this).getIdUser()

    }

    override fun onStart() {
        super.onStart()
        val dialog = Notification(this).dialogLoading("Loading...")
        dialog.show()
        GetData().getUserByID(idUser){ userGet ->
            if (userGet != null) {
                user = userGet
                tv_name.text = user.user.name
                tv_desc.text = "Tiểu sử: "+user.user.story
                NumberData().formatLevel(user.user.score){ score, color ->
                    tv_level.text = "LV $score"
                    tv_level.setBackgroundColor(Color.parseColor(color))
                }
                if (user.user.sex){
                    tv_sex.text = "Nam"
                }else{
                    tv_sex.text = "Nu"
                }




                GetData().usersFollowUser(idUser){
                    if (it!=null){
                        tv_followers.setText(NumberData().formatInt(it.size))
                    }
                }
                tv_following.setText(NumberData().formatInt(user.user.follow_users.size))

                GetData().getImage(user.user.uri_avt){
                    if(it!=null){
                        Picasso.with(this).load(it).into(iv_avt)
                    }
                }
                GetData().getImage(user.user.uri_cover){
                    if(it!=null){
                        Picasso.with(this).load(it).into(iv_cover)
                    }
                }
                //
                GetData().getPost_IdUser(idUser){ posts->
                    if (posts!=null){
                        adapterPost = Adapter_RV_Post(posts, idUserMain)
                        checkPost = true
                        rv.adapter = adapterPost
                        tv_empty.visibility = View.GONE
                    }else{
                        checkPost = false
                        tv_empty.visibility = View.VISIBLE
                        tv_empty.setText("Hiện chưa có bài viết nào")
                    }
                    GetData().getStoryByIdUser(idUser){ storys ->
                        if (storys!=null){
                            val listStory = ArrayList<Story_Get>()
                            for (i in storys){
                                if (i.story.status){
                                    listStory.add(i)
                                }
                            }
                            adapterRvStory = Adapter_RV_Story(listStory)
                            checkStory = true
                        }else{
                            checkStory = false
                        }
                        dialog.dismiss()
                        addEvent()
                    }
                }
                if (idUserMain == idUser){
                    profile_me()
                }else{
                    profile_other()
                }
            }else{
                dialog.dismiss()
                Notification(this).toastCustom("Lấy thông tin thất bại")
            }


        }


    }




    private fun profile_other(){
        floatBtn.visibility = View.GONE
        btn_follow.visibility = View.VISIBLE
        btn_messenger.visibility = View.VISIBLE
        iv_avt.setOnClickListener {
            //
        }
        btn_messenger.setOnClickListener {
            GetData().getChat(idUser, idUserMain){ chatGet ->
                if (chatGet!=null){
                    val intent = Intent(this, Activity_messenger::class.java)
                    val bundle = Bundle()
                    bundle.putString(IDCHAT, chatGet.id_chat)
                    intent.putExtras(bundle)
                    startActivity(intent)
                }else{
                    val chat = Chat(idUser, idUserMain)
                    AddData().newChat(chat){
                        if (it!=null){
                            val intent = Intent(this, Activity_messenger::class.java)
                            val bundle = Bundle()
                            bundle.putString(IDCHAT, it)
                            intent.putExtras(bundle)
                            startActivity(intent)
                        }
                    }
                }
            }
        }



        GetData().getUserByID(idUserMain){ userGet ->
            if (userGet!=null){
                var check_follow = false
                for (item in userGet.user.follow_users){
                    if (item == idUser){
                        check_follow = true
                        break
                    }
                }
                if (check_follow){
                    btn_follow.setText("Đang theo dõi")
                }else{
                    btn_follow.setText("Theo dõi")
                }
                btn_follow.setOnClickListener {
                    if (check_follow){
                        btn_follow.setText("Theo dõi")
                        UpdateData().removeFollow_user(idUserMain, idUser){
                            if (it){
                                btn_follow.setText("Theo dõi")
                                check_follow = false
                            }else{
                                btn_follow.setText("Đang theo dõi")
                                check_follow = true
                            }
                        }

                    }else{
                        btn_follow.setText("Đang theo dõi")
                        UpdateData().newFollow_user(idUserMain, idUser){
                            if (it){
                                btn_follow.setText("Đang theo dõi")
                                check_follow = true
                            }else{
                                btn_follow.setText("Theo dõi")
                                check_follow = false
                            }
                        }

                    }
                }
            }
        }
    }
    private fun profile_me(){
        floatBtn.visibility = View.VISIBLE
        btn_follow.visibility = View.GONE
        iv_avt.setOnClickListener {
            val listIv_Str = listOf(
                Image_String(Int_Uri().convertUri(R.drawable.ic_baseline_account_box_40), "Xem ảnh đại diện"),
                Image_String(Int_Uri().convertUri(R.drawable.ic_baseline_photo_library_40), "Chọn ảnh đại diện")
            )
            this.setTheme(R.style.Theme_transparent)
            val bottomSheet = BottomSheetDialog(this)
            bottomSheet.setContentView(R.layout.layout_bottom_sheeet_listview)
            val listView = bottomSheet.findViewById<ListView>(R.id.listView)
            if (listView != null) {
                listView.adapter = Adapter_LV_iv_string(bottomSheet.context,  listIv_Str )
                listView.setOnItemClickListener { parent, view, position, id ->
                    Toast.makeText(this, listIv_Str[position].str, Toast.LENGTH_SHORT).show()
                }
            }
            bottomSheet.show()
        }
        floatBtn.setOnClickListener {
            startActivity(Intent(this, Activity_NewPost::class.java))
        }
    }

    private fun addEvent() {
        toolbar.setNavigationOnClickListener {
            finish()
        }
        btn_post.setOnClickListener {
            if (checkPost){
                rv.adapter = adapterPost
                tv_empty.visibility = View.GONE
            }else{
                rv.adapter = null
                tv_empty.visibility = View.VISIBLE
                tv_empty.setText("Hiện chưa có bài viết nào.")
            }
        }
        btn_story.setOnClickListener {
            if (checkStory){
                rv.adapter = adapterRvStory
                tv_empty.visibility = View.GONE
            }else{
                rv.adapter = null
                tv_empty.visibility = View.VISIBLE
                tv_empty.setText("Hiện chưa có tác phẩm nào")

            }
        }


    }
}