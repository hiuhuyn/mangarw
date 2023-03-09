package com.example.app_mxh_manga

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import com.example.app_mxh_manga.component.ModeDataSaveSharedPreferences
import com.example.app_mxh_manga.homePage.Activity_homePage
import com.example.app_mxh_manga.login.Activity_Login


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(ModeDataSaveSharedPreferences(this).themersApp())
        setContentView(R.layout.activity_main)

        if(ModeDataSaveSharedPreferences(this).isCheckLogin()){
            startActivity(Intent(this, Activity_homePage::class.java))
            finish()
        }else{
            startActivity(Intent(this, Activity_Login::class.java))
            finish()
        }


    }
}