package com.example.app_mxh_manga.homePage.component.profile.component

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import android.widget.RadioGroup
import androidx.appcompat.widget.Toolbar
import com.example.app_mxh_manga.R
import com.example.app_mxh_manga.component.GetData_id
import com.example.app_mxh_manga.component.ModeDataSaveSharedPreferences
import com.example.app_mxh_manga.module.User
import java.text.SimpleDateFormat

class Activity_EditProfile : AppCompatActivity() {
    private lateinit var toolbar: Toolbar
    private lateinit var edt_name: EditText
    private lateinit var edt_email: EditText
    private lateinit var edt_story: EditText
    private lateinit var edt_birthday: EditText
    private lateinit var iv_avt: ImageView
    private lateinit var iv_cover: ImageView
    private lateinit var rg_sex: RadioGroup
    private lateinit var user: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)
        user = GetData_id().getUser(ModeDataSaveSharedPreferences(this).getIdUser())
        toolbar = findViewById(R.id.toolbar)
        edt_name = findViewById(R.id.edt_name)
        edt_email = findViewById(R.id.edt_email)
        edt_story = findViewById(R.id.edt_story)
        edt_birthday = findViewById(R.id.edt_birthday)
        iv_avt = findViewById(R.id.iv_avt)
        iv_cover = findViewById(R.id.iv_cover)
        rg_sex = findViewById(R.id.rg_sex)
        edt_name.setText(user.name)
        edt_email.setText(user.email)
        edt_story.setText(user.story)
        val simpleDateFormat = SimpleDateFormat("dd/mm/yyyy")
        edt_birthday.setText(simpleDateFormat.format(user.birthday))
        iv_avt.setImageURI(user.uri_avt)
        iv_cover.setImageURI(user.uri_cover)
        addEvent()
    }

    private fun addEvent() {
        toolbar.setNavigationOnClickListener {
            finish()
        }
        toolbar.setOnMenuItemClickListener {
            when(it.itemId){
                R.id.check -> {
                    finish()
                }
            }
            true
        }

    }
}