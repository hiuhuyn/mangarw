package com.example.app_mxh_manga.component

import android.net.Uri
import android.provider.ContactsContract.Data
import com.example.app_mxh_manga.R
import com.example.app_mxh_manga.module.*
import java.util.*
import kotlin.collections.ArrayList

class GetData_id {
    fun getUser(id_user: Int): User{
        val user: User = User(id_user, "Nguey Min", Calendar.getInstance().time, true, "aaaaa", 0, 0, "Hêjee", Int_Uri().convertUri(
            R.drawable.img_1), Int_Uri().convertUri(R.drawable.img), "000000")
        return user
    }
    fun getGenre(id_genre: Int): Genre{
        val genre = Genre(id_genre, "Giang hồ ${(0..100).random()}")

        return genre
    }
    fun getAllGenre(): ArrayList<Genre>{
        val list_genre = ArrayList<Genre>()
        list_genre.addAll(DataTest().getGenres())
        return list_genre
    }

    fun getListStory_genre(id_story: Int): ArrayList<Story_genre>{
        val list_sg = ArrayList<Story_genre>()
        for (i in 0..10){
            list_sg.add(Story_genre(id_story, (0..100).random()))
        }
        return list_sg
    }
    fun getListStory_Genre2(id_genre: Int): ArrayList<Story_genre>{
        val list_sg = ArrayList<Story_genre>()
        for (i in 0..10){
            list_sg.add(Story_genre((0..100).random(), id_genre))
        }
        return list_sg
    }

    fun getListGenre(id_story: Int): ArrayList<Genre>{
        val list_genre = ArrayList<Genre>()
        for (i in getListStory_genre(id_story)){
            list_genre.add(getGenre(i.id_genre))
        }
        return list_genre
    }
    fun getListStoryInGenre(id_genre: Int): ArrayList<Story>{
        val list = ArrayList<Story>()
        for (i in getListStory_Genre2(id_genre)){
            list.add(getStory(i.id_story))
        }
        return list
    }
    fun getStory(id_story: Int) : Story{
        var name: String = "Nhà gải kim"
        var author: String = "No"
        var describe: String = "Chiều nắng tà"
        var status: Boolean = true
        var cover_image: Uri = Int_Uri().convertUri(R.drawable.img_1)
        var id_user: Int = 0
        var type: Boolean = DataTest().sexList[(0..10).random()]

        val story = Story( id_story, name, author, describe, status, cover_image, id_user, type )

        return story
    }
    fun getListStory_user(id_user: Int): ArrayList<Story>{
        val list = ArrayList<Story>()
        list.addAll(DataTest().getStory())

        return list
    }
    fun getListImgPost(id_post: Int): ArrayList<Uri>{
        val list = ArrayList<Uri>()
        for( i in DataTest().imageList){
            list.add(Int_Uri().convertUri(i))
        }
        return list
    }
    fun getListpost(id_user: Int): ArrayList<Posts>{
        val list = ArrayList<Posts>()

        list.addAll(DataTest().getPosts())

        return list
    }
    fun getListChapter(ic_story: Int): ArrayList<Chapter>{
        val list = ArrayList<Chapter>()

        list.addAll(DataTest().getChapter())

        return list
    }
    fun getChapter(id_chapter: Int): Chapter{
        var title: String = "Lalalaa"
        var date_submit: Date = Calendar.getInstance().time
        var id_story: Int = 2
        var views: Int = 100

        return Chapter(id_chapter, title, date_submit, id_story, views)
    }

    fun isCheckUser(email: String, pass: String): Boolean{

        return true
    }

    fun getUser_form_email_pass(email: String, pass: String): User? {
        if(isCheckUser(email, pass)){
            return User(1, "Nguey Min", Calendar.getInstance().time, true, email, 0, 0, "Hêjee", Int_Uri().convertUri(
                R.drawable.img_1), Int_Uri().convertUri(R.drawable.img), pass)
        }else{
            return null
        }
    }
    fun isCheckEmail(email: String): Boolean{


        return false
    }






}