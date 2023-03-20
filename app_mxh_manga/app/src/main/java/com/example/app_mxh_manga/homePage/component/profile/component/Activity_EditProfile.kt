package com.example.app_mxh_manga.homePage.component.profile.component

import android.app.Activity
import android.app.DatePickerDialog
import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import android.widget.RadioGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.widget.Toolbar
import com.example.app_mxh_manga.R
import com.example.app_mxh_manga.component.*
import com.example.app_mxh_manga.module.User
import com.squareup.picasso.Picasso
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

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
    private lateinit var idUser: String
    private var birthdayText = ""
    var sex = true
    lateinit var startGallery_Avt: ActivityResultLauncher<Intent>
    lateinit var startGallery_Cover: ActivityResultLauncher<Intent>
    var avt: Uri? = null
    var avt2: Uri? = null
    var cover: Uri? = null
    var cover2: Uri? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)
        toolbar = findViewById(R.id.toolbar)
        edt_name = findViewById(R.id.edt_name)
        edt_email = findViewById(R.id.edt_email)
        edt_story = findViewById(R.id.edt_story)
        edt_birthday = findViewById(R.id.edt_birthday)
        iv_avt = findViewById(R.id.iv_avt)
        iv_cover = findViewById(R.id.iv_cover)
        rg_sex = findViewById(R.id.rg_sex)

        startGallery_Avt = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()) { result ->
            val recode = result.resultCode
            val imageUri = result.data!!.data
            if (recode == Activity.RESULT_OK  && result.data != null){
                if (imageUri != null) {
                    avt2 = imageUri
                    iv_avt.setImageURI(imageUri)
                }
            }
        }
        startGallery_Cover = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()) { result ->
            val recode = result.resultCode
            if (recode == Activity.RESULT_OK  && result.data != null){
                val imageUri = result.data!!.data
                if (imageUri != null) {
                    cover2 = imageUri
                    iv_cover.setImageURI(imageUri)
                }
            }
        }

        edt_birthday.showSoftInputOnFocus = false
        idUser = ModeDataSaveSharedPreferences(this).getIdUser()
        GetData().getUserByID(idUser){
            if (it!=null){
                user = it.user
                edt_name.setText(user.name)
                edt_email.setText(user.email)
                edt_story.setText(user.story)
                edt_birthday.setText(user.birthday)
                sex = user.sex
                if (sex){
                    rg_sex.check(R.id.rb_male)
                }else{
                    rg_sex.check(R.id.rb_female)
                }
                GetData().getImage(user.uri_avt){
                    if (it != null){
                        Picasso.with(this@Activity_EditProfile).load(it).into(iv_avt)
                        avt = it
                    }
                }
                GetData().getImage(user.uri_cover){
                    if (it != null){
                        Picasso.with(this@Activity_EditProfile).load(it).into(iv_cover)
                        cover = it
                    }
                }
            }
        }
        addEvent()
    }

    private fun addEvent() {
        rg_sex.setOnCheckedChangeListener { group, checkedId ->
            when(checkedId){
                R.id.rb_male -> {
                    sex = true
                }
                R.id.rb_female -> {
                    sex = false
                }
            }
        }
        edt_birthday.setOnClickListener {
            showDatePickerDialog()
        }
        iv_avt.setOnClickListener {
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "image/*"
            intent.addCategory(Intent.CATEGORY_OPENABLE)
            startGallery_Avt.launch(intent)
        }
        iv_cover.setOnClickListener {
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "image/*"
            intent.addCategory(Intent.CATEGORY_OPENABLE)
            startGallery_Cover.launch(intent)
        }

        toolbar.setNavigationOnClickListener {
            finish()
        }
        toolbar.setOnMenuItemClickListener {
            when(it.itemId){
                R.id.check -> {
                    val dialog = Notification(this).dialogLoading("Đang cập nhật...")
                    dialog.show()
                    val simpleDateFormat = SimpleDateFormat("dd_mm_yyyy_hh_mm_ss")
                    if (user.name != edt_name.text.toString()){
                        UpdateData().name_user(idUser, edt_name.text.toString()){
                        }
                    }
                    if ( avt2 != null ){
                        val pathNameAvt = "images/${simpleDateFormat.format(Calendar.getInstance().time)}_${idUser}_avt.jpg"
                        AddData().newImage(avt2!!, pathNameAvt){
                            UpdateData().avtUser(idUser, pathNameAvt){
                            }
                        }
                    }
                    if(cover2!=null){

                        val pathNameCover = "images/${simpleDateFormat.format(Calendar.getInstance().time)}_${idUser}_cover.jpg"
                        AddData().newImage(cover2!!, pathNameCover){
                            UpdateData().coverUser(idUser, pathNameCover){
                            }
                        }
                    }
                    if (user.story != edt_story.text.toString()){
                        UpdateData().story_user(idUser, edt_story.text.toString()){
                        }
                    }
                    if (user.birthday != edt_birthday.text.toString()){
                        UpdateData().birthday_user(idUser, edt_birthday.text.toString()){
                        }
                    }
                    if ( user.sex != sex){
                        UpdateData().sex_user(idUser, sex){
                        }
                    }
                    dialog.dismiss()
                    finish()

                }
            }

            true
        }


    }

    private fun showDatePickerDialog() {
        // Lấy ngày hiện tại để thiết lập cho DatePickerDialog
        val currentDate = Calendar.getInstance()
        val year = currentDate.get(Calendar.YEAR)
        val month = currentDate.get(Calendar.MONTH)
        val day = currentDate.get(Calendar.DAY_OF_MONTH)

        // Khởi tạo DatePickerDialog
        val datePickerDialog = DatePickerDialog(this, R.style.Them_datePickerDialog, { _, selectedYear, selectedMonth, selectedDay ->
            // Lấy ngày được chọn và hiển thị trên TextView
            val selectedDate = "${selectedDay}/${selectedMonth + 1}/${selectedYear}"
            birthdayText = selectedDate
            edt_birthday.setText(birthdayText)
        }, year, month, day)
        // Hiển thị DatePickerDialog
        datePickerDialog.show()
    }

}