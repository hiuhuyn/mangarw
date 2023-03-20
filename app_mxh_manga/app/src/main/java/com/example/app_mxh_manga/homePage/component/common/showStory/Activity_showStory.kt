package com.example.app_mxh_manga.homePage.component.common.showStory

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.RecyclerView
import com.example.app_mxh_manga.IDUSER
import com.example.app_mxh_manga.R
import com.example.app_mxh_manga.component.*
import com.example.app_mxh_manga.component.adaters.Adapter_RV_Chapter
import com.example.app_mxh_manga.component.adaters.Adapter_RV_Genre
import com.example.app_mxh_manga.homePage.component.common.Activity_readingChapter
import com.example.app_mxh_manga.homePage.component.profile.component.Activity_profile
import com.example.app_mxh_manga.homePage.component.story.IDCHAPTER
import com.example.app_mxh_manga.homePage.component.story.IDStory
import com.example.app_mxh_manga.module.*
import com.squareup.picasso.Picasso


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

    private lateinit var story_get : Story_Get
    private var listChapter = ArrayList<Chapter_Get>()
    private var listGenre_Get = ArrayList<Genre_Get>()
    private var id_story: String = ""
    private lateinit var adapter_gener : Adapter_RV_Genre
    private lateinit var adapter_chapter : Adapter_RV_Chapter
    private var user_get = User_Get()
    private var user_main = User_Get()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_story)
        val bundle = intent.extras
        if (bundle != null) {
            id_story = bundle.getString(IDStory).toString()
        }else{
            Toast.makeText(this, "Lỗi òi...", Toast.LENGTH_SHORT).show()
            finish()
        }

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

        btn_continue.visibility = View.GONE

        adapter_gener = Adapter_RV_Genre(listGenre_Get)
        rv_genre.adapter = adapter_gener
        user_main.id_user = ModeDataSaveSharedPreferences(this).getIdUser()

        adapter_chapter = Adapter_RV_Chapter(listChapter, object : OnItemClick {
            override fun onItemClick(position: Int) {
                val i = Intent(this@Activity_showStory, Activity_readingChapter::class.java)
                val bundle = Bundle()
                bundle.putString(IDCHAPTER, listChapter[position].id_chapter)
                i.putExtras(bundle)
                startActivity(i)
            }
        })
        lv_chapter.adapter = adapter_chapter
    }

    override fun onStart() {
        super.onStart()
        listGenre_Get.clear()
        listChapter.clear()
        GetData().getUserByID(user_main.id_user){
            if (it!=null){
                user_main = it
                eventFollow()
            }
        }
        GetData().getStoryByID(id_story){ storyGet ->
            if (storyGet!=null){
                story_get = storyGet
                for (item in storyGet.story.genres){
                    GetData().getGenreByIdGenre(item){
                        if (it != null) {
                            listGenre_Get.add(it)
                            adapter_gener.notifyDataSetChanged()
                        }
                    }
                }
                eventTv_user()
                tv_nameStory.setText(story_get.story.name)
                tv_author.setText(story_get.story.author)
                GetData().getImage(story_get.story.cover_image){
                    if (it!=null){
                        Picasso.with(this).load(it).into(iv_avt)
                    }
                }
                tv_desc.setText(story_get.story.describe)
                // từ id lấy ra name
                GetData().getUserByID(story_get.story.id_user){
                    if (it!=null){
                        user_get = it
                        tv_user.setText("${it.user.name}")
                    }
                }

                GetData().userFollowStory(story_get.id_story){
                    if (it != null) {
                        tv_follow.setText(NumberData().formatInt(it.size))
                    }else{
                        tv_follow.setText("0")
                    }
                }
                GetData().getChapterByIdStory(story_get.id_story){
                    if (it!=null){
                        var countLike = 0
                        for(i in it){
                            countLike += i.chapter.likes.size
                        }

                        tv_like.setText(NumberData().formatInt(countLike))
                    }else{
                        tv_like.setText("0")
                    }
                }

                GetData().getRatingStory(story_get.id_story){
                    if (it!=null){
                        var rating = 0.0
                        var id_rating = ""
                        for (i in it){
                            rating += i.rating.score
                            if(i.rating.id_user == user_main.id_user ){
                                id_rating = i.id_rating
                            }
                        }
                        Log.d("GetData", "getRatingStory rating: $rating, size : ${it.size}, id rating $id_rating")
                        rating /= it.size
                        tv_review.setText("${String.format("%.1f", rating)}/5 - ${it.size}")
                        setColorRating(id_rating,rating)
                    }else{
                        tv_review.setText("----")
                        setColorRating("", 0.0)
                    }
                }

            }
        }
        GetData().getChapterByIdStory(id_story){
            if (it!=null){
                listChapter.addAll(it)
                adapter_chapter.notifyDataSetChanged()
                eventReading()
            }
        }
        eventToolbar()
    }

    private fun setColorRating(id_rating: String, rating: Double) {
        val star1 = view_star.findViewById<ImageView>(R.id.iv_star1)
        val star2 = view_star.findViewById<ImageView>(R.id.iv_star2)
        val star3 = view_star.findViewById<ImageView>(R.id.iv_star3)
        val star4 = view_star.findViewById<ImageView>(R.id.iv_star4)
        val star5 = view_star.findViewById<ImageView>(R.id.iv_star5)

        if (rating<0.5){
            star1.setImageURI(Int_Uri().convertUri(R.drawable.ic_baseline_star_empty_40))
            star2.setImageURI(Int_Uri().convertUri(R.drawable.ic_baseline_star_empty_40))
            star3.setImageURI(Int_Uri().convertUri(R.drawable.ic_baseline_star_empty_40))
            star4.setImageURI(Int_Uri().convertUri(R.drawable.ic_baseline_star_empty_40))
            star5.setImageURI(Int_Uri().convertUri(R.drawable.ic_baseline_star_empty_40))
        }else if(rating>=0.5 && rating < 1.0){
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
            if (id_rating!=""){
                // đã tồn tại
                UpdateData().ratingUpdate(id_rating, 1){
                }
            }else{
                val ratingNew = Rating(1, user_main.id_user, id_story)
                AddData().newRating(ratingNew){

                }
            }
        }
        star2.setOnClickListener {
            star1.setImageURI(Int_Uri().convertUri(R.drawable.ic_baseline_star_40))
            star2.setImageURI(Int_Uri().convertUri(R.drawable.ic_baseline_star_40))
            star3.setImageURI(Int_Uri().convertUri(R.drawable.ic_baseline_star_empty_40))
            star4.setImageURI(Int_Uri().convertUri(R.drawable.ic_baseline_star_empty_40))
            star5.setImageURI(Int_Uri().convertUri(R.drawable.ic_baseline_star_empty_40))
            if (id_rating!=""){
                // đã tồn tại
                UpdateData().ratingUpdate(id_rating, 2){
                }
            }else{
                val ratingNew = Rating(2, user_main.id_user, id_story)
                AddData().newRating(ratingNew){

                }
            }
        }
        star3.setOnClickListener {
            star1.setImageURI(Int_Uri().convertUri(R.drawable.ic_baseline_star_40))
            star2.setImageURI(Int_Uri().convertUri(R.drawable.ic_baseline_star_40))
            star3.setImageURI(Int_Uri().convertUri(R.drawable.ic_baseline_star_40))
            star4.setImageURI(Int_Uri().convertUri(R.drawable.ic_baseline_star_empty_40))
            star5.setImageURI(Int_Uri().convertUri(R.drawable.ic_baseline_star_empty_40))
            if (id_rating!=""){
                // đã tồn tại
                UpdateData().ratingUpdate(id_rating, 3){
                }
            }else{
                val ratingNew = Rating(3, user_main.id_user, id_story)
                AddData().newRating(ratingNew){

                }
            }
        }
        star4.setOnClickListener {
            star1.setImageURI(Int_Uri().convertUri(R.drawable.ic_baseline_star_40))
            star2.setImageURI(Int_Uri().convertUri(R.drawable.ic_baseline_star_40))
            star3.setImageURI(Int_Uri().convertUri(R.drawable.ic_baseline_star_40))
            star4.setImageURI(Int_Uri().convertUri(R.drawable.ic_baseline_star_40))
            star5.setImageURI(Int_Uri().convertUri(R.drawable.ic_baseline_star_empty_40))
            if (id_rating!=""){
                // đã tồn tại
                UpdateData().ratingUpdate(id_rating, 4){
                }
            }else{
                val ratingNew = Rating(4, user_main.id_user, id_story)
                AddData().newRating(ratingNew){

                }
            }
        }
        star5.setOnClickListener {
            star1.setImageURI(Int_Uri().convertUri(R.drawable.ic_baseline_star_40))
            star2.setImageURI(Int_Uri().convertUri(R.drawable.ic_baseline_star_40))
            star3.setImageURI(Int_Uri().convertUri(R.drawable.ic_baseline_star_40))
            star4.setImageURI(Int_Uri().convertUri(R.drawable.ic_baseline_star_40))
            star5.setImageURI(Int_Uri().convertUri(R.drawable.ic_baseline_star_40))
            if (id_rating!=""){
                // đã tồn tại
                UpdateData().ratingUpdate(id_rating, 5){
                }
            }else{
                val ratingNew = Rating(5, user_main.id_user, id_story)
                AddData().newRating(ratingNew){

                }
            }
        }
    }

    private fun eventFollow(){
        var isFollow = false // kt 1 hàm khác
        for (item in user_main.user.follow_storys){
            if (item== id_story){
                isFollow = true
                break
            }
        }

        if (isFollow){
            btn_follow.setBackgroundColor(getColor(R.color.red))
            btn_follow.setText("Đang theo dõi")
        }else{
            btn_follow.setBackgroundColor(getColor(R.color.green))
            btn_follow.setText("Theo dõi")
        }
        btn_follow.setOnClickListener {
            if (isFollow){
                btn_follow.setBackgroundColor(getColor(R.color.green))
                btn_follow.setText("Theo dõi")
                isFollow = false
                UpdateData().removeFollow_story(user_main.id_user, id_story){
                }
            }else{
                btn_follow.setBackgroundColor(getColor(R.color.red))
                btn_follow.setText("Đang theo dõi")
                isFollow = true
                UpdateData().newFollow_story(user_main.id_user, id_story){

                }
            }
        }
    }
    private fun eventTv_user(){
        tv_user.setOnClickListener {
            val i = Intent(this, Activity_profile::class.java)
            val bundle = Bundle()
            bundle.putString(IDUSER, story_get.story.id_user)
            i.putExtras(bundle)
            startActivity(i)

        }
    }
    private fun eventReading(){
        GetData().getHistoryByUser_Story(user_main.id_user, id_story){ historyGet ->
            if (historyGet!=null && historyGet.history.id_chapter!=""){
                btn_continue.visibility = View.VISIBLE
                btn_continue.setOnClickListener {
                    showContentChapter(historyGet.history.id_chapter)
                }
            }else{
                btn_continue.visibility = View.GONE
            }
        }
        btn_reading_First.setOnClickListener {
            showContentChapter(listChapter.last().id_chapter)
        }
        btn_reading_new.setOnClickListener {
            showContentChapter(listChapter.first().id_chapter)
        }
    }
    private fun eventToolbar(){
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
    }


    private fun showContentChapter(id_chapter: String){
        val i = Intent(this, Activity_readingChapter::class.java)
        val bundle = Bundle()
        bundle.putString("id_chapter", id_chapter)
        i.putExtras(bundle)
        startActivity(i)

    }
}