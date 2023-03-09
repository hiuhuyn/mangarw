package com.example.app_mxh_manga.homePage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import com.example.app_mxh_manga.R
import com.example.app_mxh_manga.homePage.component.bookcase.Fragment_BookCase
import com.example.app_mxh_manga.homePage.component.genre.Fragment_Genre
import com.example.app_mxh_manga.homePage.component.home.Fragment_Home
import com.example.app_mxh_manga.homePage.component.profile.Fragment_Profile
import com.example.app_mxh_manga.homePage.component.readmanga.Fragment_ReadManga
import com.google.android.material.bottomnavigation.BottomNavigationView

class Activity_homePage : AppCompatActivity() {
    private lateinit var bottom_nav: BottomNavigationView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_page)
        bottom_nav = findViewById(R.id.bottom_nav)
        supportFragmentManager.beginTransaction().add(
            R.id.frameLayout,
            Fragment_Home()
        ).commit()

        bottom_nav.setOnItemSelectedListener {
            var tag = ""
            var selectorFragment: Fragment = Fragment_Home()
            when(it.itemId){
                R.id.item_home -> {
                    tag = "home"
                    selectorFragment = Fragment_Home()
                }
                R.id.item_readManga -> {
                    tag = "readbook"
                    selectorFragment = Fragment_ReadManga()
                }
                R.id.item_genre -> {
                    tag = "genre"
                    selectorFragment = Fragment_Genre()
                }
                R.id.item_bookcase -> {
                    tag = "bookcase"
                    selectorFragment = Fragment_BookCase()
                }
                R.id.item_profile -> {
                    tag = "profile"
                    selectorFragment = Fragment_Profile()
                }
            }
            supportFragmentManager.beginTransaction().replace(
                R.id.frameLayout,
                selectorFragment,
                tag
            ).commit()


            true
        }
    }
}