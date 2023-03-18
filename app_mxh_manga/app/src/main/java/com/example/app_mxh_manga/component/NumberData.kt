package com.example.app_mxh_manga.component

import android.graphics.Color
import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.concurrent.TimeUnit

class NumberData {
    fun formatInt(count: Int) : String {
        var string = "${count}"
        if (count >= 1000 && count < 1000000){
            val k:Double = (count/1000).toDouble()
            string = String.format("%.1f", k) + "K"
        }else if (count >= 1000000 && count < 1000000000 ){
            val k:Double = (count/1000000).toDouble()
            string = String.format("%.1f", k) + "M"
        }else if (count >= 1000000000){
            val k:Double = (count/1000000000).toDouble()
            string = String.format("%.1f", k) + "G"
        }else{
            string = "${count}"
        }
        return string
    }
    fun formatLevel(score: Int, callback: (Int, String) -> Unit){
        val colorList = arrayOf("#C18F8F8F", "#FF00BCD4", "#FF03A9F4", "#4cae4c", "#FF6200EE", "#FFFF9800", "#FFFFEB3B", "#FFDA00FF", "#FF00FF0A", "#D9534F", "#000000", "#FFFFFFFF")
        if (score < 10){
            callback(0, colorList[0])
        }else if (score in 10..99){
            callback(1, colorList[1])
        }else if (score in 100..999){
            callback(2, colorList[2])
        }else if (score in 1000..4999){
            callback(3, colorList[3])
        }else if (score in 5000..9999){
            callback(4, colorList[4])
        }else if (score in 10000..14999){
            callback(5, colorList[5])
        }else if (score in 15000..19999){
            callback(6, colorList[6])
        }else if (score in 20000..24999){
            callback(7, colorList[7])
        }else if (score in 25000..29999){
            callback(8, colorList[8])
        }else if (score in 30000..34999){
            callback(9, colorList[9])
        }else if (score > 35000){
            callback(10,colorList[10])
        }
    }


    fun formatTime( pastTime: Date ): String{
        val currentTime = Calendar.getInstance().time
        val diffInSeconds = (currentTime.time - pastTime.time)/1000
        val minutes = TimeUnit.SECONDS.toMinutes(diffInSeconds)
        val hours = TimeUnit.SECONDS.toHours(diffInSeconds)

        if (diffInSeconds<60){
            return "${diffInSeconds} giây trước"
        }else if (minutes<60 && minutes >= 1){
            return "${minutes} phút trước"
        }else if (hours <24 && hours >=0 ){
            return "${hours} giờ trước"
        }else{
            return  SimpleDateFormat("dd/MM/yyyy").format(pastTime)
        }
    }

    fun chapter(id_story: Int): Int{
        val count = 99

        return count
    }









}