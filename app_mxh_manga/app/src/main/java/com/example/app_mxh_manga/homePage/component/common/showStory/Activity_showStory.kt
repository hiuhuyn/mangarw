package com.example.app_mxh_manga.homePage.component.common.showStory

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.RecyclerView
import com.example.app_mxh_manga.R
import com.example.app_mxh_manga.component.GetData_id
import com.example.app_mxh_manga.component.GetNumberData
import com.example.app_mxh_manga.component.Int_Uri
import com.example.app_mxh_manga.component.OnItemClick
import com.example.app_mxh_manga.component.adaters.Adapter_RV_Chapter
import com.example.app_mxh_manga.component.adaters.Adapter_RV_Genre
import com.example.app_mxh_manga.homePage.component.common.Activity_readingStory
import com.example.app_mxh_manga.homePage.component.profile.component.Activity_profile
import com.example.app_mxh_manga.module.Chapter
import com.example.app_mxh_manga.module.DataTest
import com.example.app_mxh_manga.module.Genre
import com.example.app_mxh_manga.module.Story


class Activity_showStory : AppCompatActivity() {
    private lateinit var toolbar: Toolbar
    private lateinit var iv_avt: ImageView
    private lateinit var rv_genre: RecyclerView
    private lateinit var tv_author: TextView
    private lateinit var tv_user: TextView
    private lateinit var tv_like : TextView
    private lateinit var tv_follow: TextView
    private lateinit var tv_review: TextView
    private lateinit var view_star: View
    private lateinit var tv_desc: TextView
    private lateinit var tv_nameStory: TextView

    private lateinit var btn_continue: Button
    private lateinit var btn_follow: Button
    private lateinit var btn_reading_First: Button
    private lateinit var btn_reading_new: Button
    private lateinit var lv_chapter: RecyclerView

    private lateinit var story : Story
    private var listChapter = ArrayList<Chapter>()

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        val bundle = intent.extras
        if (bundle != null) {
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
//                story = bundle.getParcelable("story", Story::class.java) as Story
//            }else{
//
//            }
            story = bundle.getParcelable<Story>("story")!!
        }else{
            story = DataTest().getStory().get(0)
        }
        setContentView(R.layout.activity_show_story)
        toolbar = findViewById(R.id.toolbar)
        iv_avt = findViewById(R.id.iv_avt)
        rv_genre = findViewById(R.id.rv_genre)
        tv_author = findViewById(R.id.tv_author)
        tv_user = findViewById(R.id.tv_user)
        tv_like = findViewById(R.id.tv_like)
        tv_follow = findViewById(R.id.tv_follow)
        tv_review = findViewById(R.id.tv_Review)
        view_star = findViewById(R.id.view_star)
        tv_desc = findViewById(R.id.tv_desc)
        btn_reading_First = findViewById(R.id.btn_reading_First)
        btn_reading_new = findViewById(R.id.btn_reading_new)
        lv_chapter = findViewById(R.id.lv_chapter)
        btn_follow = findViewById(R.id.btn_follow)
        tv_nameStory = findViewById(R.id.tv_nameStory)
        btn_continue = findViewById(R.id.btn_continue)

        listChapter.addAll(DataTest().getChapter())
//        lv_chapter.adapter = Adapter_LV_Chapter(this, listChapter)
        lv_chapter.adapter = Adapter_RV_Chapter(listChapter, object : OnItemClick{
            override fun onItemClick(position: Int) {
                val i = Intent(this@Activity_showStory, Activity_readingStory::class.java)
                val bundle = Bundle()
                bundle.putInt("id_chapter", listChapter[position].id_chapter)
                i.putExtras(bundle)
                startActivity(i)
            }

        })

        tv_nameStory.setText(story.name)
        tv_author.setText(story.author)
        iv_avt.setImageURI(story.cover_image)
        tv_desc.setText(story.describe)
        // từ id lấy ra name
        tv_user.setText("${story.id_user}")
        tv_like.setText(GetNumberData().numberLike_Story(story.id_story))
        tv_follow.setText(GetNumberData().numberFollow_Story(story.id_story))
        // get data
        var rating = 3.5
        tv_review.setText("${rating}/5 - ${100}")
        setColorRating(rating)
        var listGenre = ArrayList<Genre>()
        listGenre.addAll(DataTest().getGenres())

        val adapter_gener = Adapter_RV_Genre(listGenre)
        rv_genre.adapter = adapter_gener





        addEvent()
    }

    private fun setColorRating(rating: Double) {
        val star1 = view_star.findViewById<ImageView>(R.id.iv_star1)
        val star2 = view_star.findViewById<ImageView>(R.id.iv_star2)
        val star3 = view_star.findViewById<ImageView>(R.id.iv_star3)
        val star4 = view_star.findViewById<ImageView>(R.id.iv_star4)
        val star5 = view_star.findViewById<ImageView>(R.id.iv_star5)
        if(rating>=0.5 && rating < 1.0){
            star1.setImageURI(Int_Uri().convertUri(R.drawable.ic_baseline_star_half_40))
        }else if(rating >= 1.0 && rating < 1.5){
            star1.setImageURI(Int_Uri().convertUri(R.drawable.ic_baseline_star_40))
        }else if(rating >= 1.5 && rating < 2){
            star1.setImageURI(Int_Uri().convertUri(R.drawable.ic_baseline_star_40))
            star2.setImageURI(Int_Uri().convertUri(R.drawable.ic_baseline_star_half_40))
        }else if(rating >= 2 && rating < 2.5){
            star1.setImageURI(Int_Uri().convertUri(R.drawable.ic_baseline_star_40))
            star2.setImageURI(Int_Uri().convertUri(R.drawable.ic_baseline_star_40))
        }else if(rating >= 2.5 && rating < 3){
            star1.setImageURI(Int_Uri().convertUri(R.drawable.ic_baseline_star_40))
            star2.setImageURI(Int_Uri().convertUri(R.drawable.ic_baseline_star_40))
            star3.setImageURI(Int_Uri().convertUri(R.drawable.ic_baseline_star_half_40))
        }else if(rating >= 3 && rating < 3.5){
            star1.setImageURI(Int_Uri().convertUri(R.drawable.ic_baseline_star_40))
            star2.setImageURI(Int_Uri().convertUri(R.drawable.ic_baseline_star_40))
            star3.setImageURI(Int_Uri().convertUri(R.drawable.ic_baseline_star_40))
        }else if(rating >= 3.5 && rating < 4){
            star1.setImageURI(Int_Uri().convertUri(R.drawable.ic_baseline_star_40))
            star2.setImageURI(Int_Uri().convertUri(R.drawable.ic_baseline_star_40))
            star3.setImageURI(Int_Uri().convertUri(R.drawable.ic_baseline_star_40))
            star4.setImageURI(Int_Uri().convertUri(R.drawable.ic_baseline_star_half_40))
        }else if(rating >= 4 && rating < 4.5){
            star1.setImageURI(Int_Uri().convertUri(R.drawable.ic_baseline_star_40))
            star2.setImageURI(Int_Uri().convertUri(R.drawable.ic_baseline_star_40))
            star3.setImageURI(Int_Uri().convertUri(R.drawable.ic_baseline_star_40))
            star4.setImageURI(Int_Uri().convertUri(R.drawable.ic_baseline_star_40))
        }else if(rating >= 4.5 && rating < 5){
            star1.setImageURI(Int_Uri().convertUri(R.drawable.ic_baseline_star_40))
            star2.setImageURI(Int_Uri().convertUri(R.drawable.ic_baseline_star_40))
            star3.setImageURI(Int_Uri().convertUri(R.drawable.ic_baseline_star_40))
            star4.setImageURI(Int_Uri().convertUri(R.drawable.ic_baseline_star_40))
            star5.setImageURI(Int_Uri().convertUri(R.drawable.ic_baseline_star_half_40))
        }else if(rating >= 5){
            star1.setImageURI(Int_Uri().convertUri(R.drawable.ic_baseline_star_40))
            star2.setImageURI(Int_Uri().convertUri(R.drawable.ic_baseline_star_40))
            star3.setImageURI(Int_Uri().convertUri(R.drawable.ic_baseline_star_40))
            star4.setImageURI(Int_Uri().convertUri(R.drawable.ic_baseline_star_40))
            star5.setImageURI(Int_Uri().convertUri(R.drawable.ic_baseline_star_40))
        }

        // sét sự kiện đánh giá
        star1.setOnClickListener {
            star1.setImageURI(Int_Uri().convertUri(R.drawable.ic_baseline_star_40))
            star2.setImageURI(Int_Uri().convertUri(R.drawable.ic_baseline_star_empty_40))
            star3.setImageURI(Int_Uri().convertUri(R.drawable.ic_baseline_star_empty_40))
            star4.setImageURI(Int_Uri().convertUri(R.drawable.ic_baseline_star_empty_40))
            star5.setImageURI(Int_Uri().convertUri(R.drawable.ic_baseline_star_empty_40))
        }
        star2.setOnClickListener {
            star1.setImageURI(Int_Uri().convertUri(R.drawable.ic_baseline_star_40))
            star2.setImageURI(Int_Uri().convertUri(R.drawable.ic_baseline_star_40))
            star3.setImageURI(Int_Uri().convertUri(R.drawable.ic_baseline_star_empty_40))
            star4.setImageURI(Int_Uri().convertUri(R.drawable.ic_baseline_star_empty_40))
            star5.setImageURI(Int_Uri().convertUri(R.drawable.ic_baseline_star_empty_40))
        }
        star3.setOnClickListener {
            star1.setImageURI(Int_Uri().convertUri(R.drawable.ic_baseline_star_40))
            star2.setImageURI(Int_Uri().convertUri(R.drawable.ic_baseline_star_40))
            star3.setImageURI(Int_Uri().convertUri(R.drawable.ic_baseline_star_40))
            star4.setImageURI(Int_Uri().convertUri(R.drawable.ic_baseline_star_empty_40))
            star5.setImageURI(Int_Uri().convertUri(R.drawable.ic_baseline_star_empty_40))
        }
        star4.setOnClickListener {
            star1.setImageURI(Int_Uri().convertUri(R.drawable.ic_baseline_star_40))
            star2.setImageURI(Int_Uri().convertUri(R.drawable.ic_baseline_star_40))
            star3.setImageURI(Int_Uri().convertUri(R.drawable.ic_baseline_star_40))
            star4.setImageURI(Int_Uri().convertUri(R.drawable.ic_baseline_star_40))
            star5.setImageURI(Int_Uri().convertUri(R.drawable.ic_baseline_star_empty_40))
        }
        star5.setOnClickListener {
            star1.setImageURI(Int_Uri().convertUri(R.drawable.ic_baseline_star_40))
            star2.setImageURI(Int_Uri().convertUri(R.drawable.ic_baseline_star_40))
            star3.setImageURI(Int_Uri().convertUri(R.drawable.ic_baseline_star_40))
            star4.setImageURI(Int_Uri().convertUri(R.drawable.ic_baseline_star_40))
            star5.setImageURI(Int_Uri().convertUri(R.drawable.ic_baseline_star_40))
        }





    }

    private fun addEvent() {
        tv_user.setOnClickListener {
            var i = Intent(this, Activity_profile::class.java)
            val bundle = Bundle()
            bundle.putInt("id_user", story.id_user)
            i.putExtras(bundle)
            startActivity(i)

        }
        var isFollow = true // kt 1 hàm khác
        if (isFollow){
            btn_follow.setBackgroundColor(getColor(R.color.green))
            btn_follow.setText("Theo dõi")
            isFollow = false
        }else{
            btn_follow.setBackgroundColor(getColor(R.color.red))
            btn_follow.setText("Đang theo dõi")
            isFollow = true
        }
        btn_follow.setOnClickListener {
            if (isFollow){
                btn_follow.setBackgroundColor(getColor(R.color.green))
                btn_follow.setText("Theo dõi")
                isFollow = false
            }else{
                btn_follow.setBackgroundColor(getColor(R.color.red))
                btn_follow.setText("Đang theo dõi")
                isFollow = true
            }
        }
        toolbar.setNavigationOnClickListener {
            finish()
        }
        toolbar.setOnMenuItemClickListener {
            when(it.itemId){
                R.id.download_story -> {

                }
                R.id.share_story -> {

                }

            }
            true
        }
        btn_continue.setOnClickListener {
            showContentChapter(listChapter[2].id_chapter)
        }
        btn_reading_First.setOnClickListener {
            showContentChapter(listChapter.first().id_chapter)
        }
        btn_reading_new.setOnClickListener {
            showContentChapter(listChapter.last().id_chapter)
        }


    }
    private fun showContentChapter(id_chapter: Int){
        val i = Intent(this, Activity_readingStory::class.java)
        val bundle = Bundle()
        bundle.putInt("id_chapter", id_chapter)
        i.putExtras(bundle)
        startActivity(i)

    }
}