package com.example.app_mxh_manga.homePage.component.story

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.RecyclerView
import com.example.app_mxh_manga.R
import com.example.app_mxh_manga.component.GetData_id
import com.example.app_mxh_manga.component.Int_Uri
import com.example.app_mxh_manga.component.ModeDataSaveSharedPreferences
import com.example.app_mxh_manga.component.OnItemClick_2
import com.example.app_mxh_manga.component.adaters.Adapter_RV_Genre
import com.example.app_mxh_manga.module.DataTest
import com.example.app_mxh_manga.module.Genre
import com.example.app_mxh_manga.module.Story
import com.google.android.material.bottomsheet.BottomSheetDialog

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
    private var listGenre = ArrayList<Genre>()
    private var idUser = 0
    private var uri_Avt: Uri = Int_Uri().convertUri(R.drawable.ic_launcher_background)
    private var isType: Boolean = true
    private lateinit var startGallery_images: ActivityResultLauncher<Intent>

    private lateinit var adapaterRV_Genre: Adapter_RV_Genre

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
        adapaterRV_Genre = Adapter_RV_Genre(listGenre)
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
            val story = Story(0, edt_name.text.toString(), edt_author.text.toString(), edt_desc.text.toString(),
                false, uri_Avt, idUser, isType)
            finish()
            finish()
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
//            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
            intent.addCategory(Intent.CATEGORY_OPENABLE)
            startGallery_images.launch(intent)
        }
        ib_add_genre.setOnClickListener {
            setTheme(R.style.Theme_transparent)
            val view = layoutInflater.inflate(R.layout.layout_bottomsheet_genre, null)
            val bottomSheet = BottomSheetDialog(this)
            bottomSheet.setContentView(view)
            val listGenre2 = ArrayList<Genre>()
            val rv_genre2 = view.findViewById<RecyclerView>(R.id.rv_genre2)
            val btn_confirm_genre = view.findViewById<Button>(R.id.btn_confirm_genre)
            btn_confirm_genre.setOnClickListener {
                listGenre.clear()
                listGenre.addAll(listGenre2)
                bottomSheet.dismiss()
                adapaterRV_Genre.notifyDataSetChanged()
            }
            val allGenre = DataTest().getGenres()
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