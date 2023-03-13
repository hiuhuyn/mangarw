package com.example.app_mxh_manga.component

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
    fun formatLevel(score: Int): Int{
        if (score < 10){
            return 0
        }else if (score in 10..99){
            return 1
        }else if (score in 100..999){
            return 2
        }else if (score in 1000..4999){
            return 3
        }else if (score in 5000..9999){
            return 4
        }else if (score in 10000..14999){
            return 5
        }else if (score in 15000..19999){
            return 6
        }else if (score in 20000..24999){
            return 7
        }else if (score in 25000..29999){
            return 8
        }else if (score in 30000..34999){
            return 8
        }else if (score in 35000..39999){
            return 9
        }else if (score >= 40000 ){
            return 10
        }
        return 0
    }


    fun formatTime( pastTime: Date ): String{
        val currentTime = Calendar.getInstance().time
        val diffInSeconds = (currentTime.time - pastTime.time)/1000
        val minutes = TimeUnit.SECONDS.toMinutes(diffInSeconds)
        val hours = TimeUnit.SECONDS.toHours(diffInSeconds)
        val days = TimeUnit.DAYS.toHours(diffInSeconds)

        if (diffInSeconds<60){
            return "${diffInSeconds} giây trước"
        }else if (minutes<60 && minutes >= 1){
            return "${minutes} phút trước"
        }else if (hours <24 && hours >=0 ){
            return "${hours} giờ trước"
        }else if(days >= 1 && days <= 7){
            return "${days} ngày trước"
        }else{
            return  SimpleDateFormat("dd/mm/yyyy").format(pastTime)
        }
    }

    fun chapter(id_story: Int): Int{
        val count = 99

        return count
    }









}