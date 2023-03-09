package com.example.app_mxh_manga.component

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate

class ModeDataSaveSharedPreferences(val activity: AppCompatActivity) {
    private val myperf: SharedPreferences = activity.getSharedPreferences("dataSave", AppCompatActivity.MODE_PRIVATE)

    fun themersApp(): Int{
        return myperf.getInt("themes_app", AppCompatDelegate.MODE_NIGHT_NO)
    }
    fun logout(){
        val myEdit: SharedPreferences.Editor = myperf.edit()
        myEdit.putBoolean("login", false)
        myEdit.putInt("id_user", -1)
        myEdit.apply()
    }

    fun isCheckLogin(): Boolean{
        return myperf.getBoolean("login", true)
    }
    fun setLogin(check: Boolean, id_user: Int){
        val myEdit: SharedPreferences.Editor = myperf.edit()
        myEdit.putBoolean("login", check)
        myEdit.putInt("id_user", id_user)
        myEdit.apply()
    }

    fun getIdUser():Int{
        return myperf.getInt("id_user", 1)
    }
}