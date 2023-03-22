package com.example.app_mxh_manga.homePage.component.story

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Display.Mode
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.RecyclerView
import com.example.app_mxh_manga.R
import com.example.app_mxh_manga.component.*
import com.example.app_mxh_manga.component.adaters.Adapter_RV_Genre
import com.example.app_mxh_manga.module.Genre_Get
import com.example.app_mxh_manga.module.Story_Get
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.squareup.picasso.Picasso
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class Activity_Update_story : AppCompatActivity() {
    private lateinit var toolbar: Toolbar
    private lateinit var iv_avt: ImageView
    private lateinit var edt_name: EditText
    private lateinit var edt_desc: EditText
    private lateinit var ib_add_genre: ImageButton
    private lateinit var btn_save: Button
    private lateinit var rv_genre: RecyclerView
    private lateinit var startGallery_images: ActivityResultLauncher<Intent>
    private var allGenre = ArrayList<Genre_Get>()
    private lateinit var adapaterRV_Genre: Adapter_RV_Genre
    private var listGenre_Select = ArrayList<Genre_Get>()
    private var uri_Avt: Uri? = null
    private var storyGet = Story_Get()

    init {
        GetData().getAllGenre { geners ->
            if (geners!=null){
                allGenre.addAll(geners)
            }
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_story)
        toolbar = findViewById(R.id.toolbar)
        iv_avt = findViewById(R.id.iv_avt)
        edt_name = findViewById(R.id.edt_name)
        edt_desc = findViewById(R.id.edt_desc)
        ib_add_genre = findViewById(R.id.ib_add_genre)
        btn_save = findViewById(R.id.btn_save)
        rv_genre = findViewById(R.id.rv_genre)

        adapaterRV_Genre = Adapter_RV_Genre(listGenre_Select)
        rv_genre.adapter = adapaterRV_Genre

        storyGet.id_story = intent.extras?.getString(IDStory).toString()
        GetData().getStoryByID(storyGet.id_story){ get ->
            if (get!=null){
                storyGet = get
                GetData().getImage(storyGet.story.cover_image){
                    if (it!=null){
                        Picasso.with(this).load(it).into(iv_avt)
                    }
                }
                edt_name.setText(storyGet.story.name)
                edt_desc.setText(storyGet.story.describe)
                listGenre_Select.clear()
                for (i in storyGet.story.genres){
                    GetData().getGenreByIdGenre(i){
                        if (it != null) {
                            listGenre_Select.add(it)
                            adapaterRV_Genre.notifyDataSetChanged()
                        }
                    }
                }
            }

        }
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
            val adapterRvGenre2 = Adapter_RV_Genre2(allGenre, object : OnItemClick_2 {
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
        iv_avt.setOnClickListener {
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "image/*"
            startGallery_images.launch(intent)
        }
        btn_save.setOnClickListener {
            val dialog = Notification(this).dialogLoading("Save...")
            val simpleDateFormat = SimpleDateFormat("dd_mm_yyyy_hh_mm_ss")

            storyGet.story.name = edt_name.text.toString().trim()
            storyGet.story.describe = edt_desc.text.toString().trim()
            val listIdGenre = ArrayList<String>()
            for (item in listGenre_Select){
                listIdGenre.add(item.id_genre)
            }
            storyGet.story.genres = listIdGenre

            if (uri_Avt!=null){
                val avtbefore = storyGet.story.cover_image
                if (avtbefore!=""){
                    DeleteData().delOneImage(avtbefore){
                    }
                }
                val cover_image = "story/${storyGet.story.name}_${(0..1000).random()}.jpg"
                storyGet.story.cover_image = cover_image
                AddData().newImage(uri_Avt!!, cover_image){

                }
            }





            dialog.show()
            SetData().story(storyGet){
                dialog.dismiss()
                finish()
            }
        }
        toolbar.setNavigationOnClickListener {
            finish()
        }



    }
}