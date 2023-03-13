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
        myEdit.putString("id_user", "")
        myEdit.apply()
    }


    fun setLogin(id_user: String){
        val myEdit: SharedPreferences.Editor = myperf.edit()
        myEdit.putString("id_user", id_user)
        myEdit.apply()
    }

    fun getIdUser():String{
        return myperf.getString("id_user", "").toString()
    }
}