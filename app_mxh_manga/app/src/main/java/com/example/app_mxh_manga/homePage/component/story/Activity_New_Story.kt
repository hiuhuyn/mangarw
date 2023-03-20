package com.example.app_mxh_manga.homePage.component.story

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.RadioGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.RecyclerView
import com.example.app_mxh_manga.R
import com.example.app_mxh_manga.component.*
import com.example.app_mxh_manga.component.adaters.Adapter_RV_Genre
import com.example.app_mxh_manga.module.Genre
import com.example.app_mxh_manga.module.Genre_Get
import com.example.app_mxh_manga.module.Story
import com.google.android.material.bottomsheet.BottomSheetDialog
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class Activity_New_Story : AppCompatActivity() {
    private lateinit var toolbar: Toolbar
    private lateinit var iv_avt:ImageView
    private lateinit var edt_name: EditText
    private lateinit var edt_author: EditText
    private lateinit var edt_desc: EditText
    private lateinit var ib_add_genre: ImageButton
    private lateinit var rg_type: RadioGroup
    private lateinit var btn_save: Button
    private lateinit var rv_genre: RecyclerView
    private var listGenre_Select = ArrayList<Genre_Get>()
    private var idUser = ""
    private var uri_Avt: Uri = Int_Uri().convertUri(R.drawable.ic_launcher_foreground)
    private var isType: Boolean = true
    private lateinit var startGallery_images: ActivityResultLauncher<Intent>
    private var allGenre = ArrayList<Genre_Get>()
    private lateinit var adapaterRV_Genre: Adapter_RV_Genre
    init {
        GetData().getAllGenre { geners ->
            if (geners!=null){
                allGenre.addAll(geners)
            }
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_story)
        toolbar = findViewById(R.id.toolbar)
        iv_avt = findViewById(R.id.iv_avt)
        edt_name = findViewById(R.id.edt_name)
        edt_desc = findViewById(R.id.edt_desc)
        edt_author = findViewById(R.id.edt_author)
        ib_add_genre = findViewById(R.id.ib_add_genre)
        rg_type = findViewById(R.id.rg_type)
        btn_save = findViewById(R.id.btn_save)
        rv_genre = findViewById(R.id.rv_genre)

        idUser = ModeDataSaveSharedPreferences(this).getIdUser()
        adapaterRV_Genre = Adapter_RV_Genre(listGenre_Select)
        rv_genre.adapter = adapaterRV_Genre

        startGallery_images = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK && result.data != null) {
                val imageUri = result.data!!.data
                if (imageUri!=null){
                    uri_Avt = imageUri
                    iv_avt.setImageURI(imageUri)
                }
            }
        }
        addEvent()
    }

    private fun addEvent() {
        toolbar.setNavigationOnClickListener {
            finish()
        }
        btn_save.setOnClickListener {
            val name: String = edt_name.text.toString()
            val author: String = edt_author.text.toString()
            val id_user: String = idUser
            val describe: String = edt_desc.text.toString()
            val status: Boolean = false
            val type: Boolean = isType
            val dialog = Notification(this).dialogLoading("Save...")
            val simpleDateFormat = SimpleDateFormat("dd_mm_yyyy_hh_mm_ss")
            dialog.show()

            val cover_image = "images/${simpleDateFormat.format(Calendar.getInstance().time)}_${idUser}_story.jpg"
            val listIdGenre = ArrayList<String>()
            for (item in listGenre_Select){
                listIdGenre.add(item.id_genre)
            }
            val story = Story(name, author, id_user, describe, status, cover_image, type, listIdGenre)
            AddData().newStory(story){ id->
                dialog.dismiss()
                if (id!=null){
                    Notification(this).toastCustom("Thêm thành công").show()
                    AddData().newImage(uri_Avt, cover_image){
                        if (it){
                            UpdateData().cover_story(id, cover_image){
                                finish()
                            }
                        }
                    }
                }else{
                    Notification(this).toastCustom("Thêm không thành công").show()
                }
            }
        }
        rg_type.setOnCheckedChangeListener { group, checkedId ->
            when(checkedId){
                R.id.rb_novel -> {
                    isType = true
                }
                R.id.rb_manga -> {
                    isType = false
                }
            }
        }
        iv_avt.setOnClickListener {
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "image/*"
//            intent.addCategory(Intent.CATEGORY_OPENABLE)
            startGallery_images.launch(intent)
        }
        ib_add_genre.setOnClickListener {
            val view = layoutInflater.inflate(R.layout.layout_bottomsheet_genre, null)
            val bottomSheet = BottomSheetDialog(this)
            bottomSheet.setContentView(view)
            val listGenre2 = ArrayList<Genre_Get>()
            val rv_genre2 = view.findViewById<RecyclerView>(R.id.rv_genre2)
            val btn_confirm_genre = view.findViewById<Button>(R.id.btn_confirm_genre)
            btn_confirm_genre.setOnClickListener {
                listGenre_Select.clear()
                listGenre_Select.addAll(listGenre2)
                adapaterRV_Genre.notifyDataSetChanged()
                bottomSheet.dismiss()
            }
            val adapterRvGenre2 = Adapter_RV_Genre2(allGenre, object : OnItemClick_2{
                override fun onItemClick2(position: Int, check: Boolean) {
                    if (check){
                        listGenre2.remove(allGenre[position])
                    }else{
                        listGenre2.add(allGenre[position])
                    }
                }
            })
            rv_genre2.adapter = adapterRvGenre2
            bottomSheet.show()
        }



    }
}