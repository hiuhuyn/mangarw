package com.example.app_mxh_manga.component

class GetNumberData {
    fun formatInt(count: Int) : String {
        var string = ""
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
    fun numberLike_Story(id_story: Int): String{
        val count = 1284792
        // lấy dữ liệu về

        return formatInt(count)
    }
    fun numberLike_Chapter(id_chapter: Int): String{
        val count = 1284792
        // lấy dữ liệu về

        return formatInt(count)
    }
    fun numberChapter(id_story: Int): Int{
        val count = 99

        return count
    }


    fun numberCmt_Story(id_story: Int): String{
        val count = 1284792
        // lấy dữ liệu về

        return formatInt(count)
    }
    fun numberLike_post(id_post: Int): String{
        val count = 1284792
        // lấy dữ liệu về

        return formatInt(count)
    }
    fun numberCmt_post(id_post: Int): String{
        val count = 1284792
        // lấy dữ liệu về

        return formatInt(count)
    }
    fun numberFollow_Story(id_story: Int): String{
        val count = 1284792
        // lấy dữ liệu về

        return formatInt(count)
    }
    fun numberFollow_User(id_user: Int): String{
        val count = 1284792
        // lấy dữ liệu về

        return formatInt(count)
    }
    fun numFollowers(id_user: Int): String{
        val count = 1284792
        // lấy dữ liệu về

        return formatInt(count)
    }
    fun numFollowing(id_user: Int): String{
        val count = 1284792
        // lấy dữ liệu về

        return formatInt(count)
    }






}