package com.example.app_mxh_manga.module

import com.example.app_mxh_manga.R
import com.example.app_mxh_manga.component.Int_Uri
import java.util.Calendar
import kotlin.random.Random

class DataTest {
    val nameList = arrayListOf(
        "John", "Emily", "Michael", "Olivia", "William",
        "Sophia", "Daniel", "Isabella", "David", "Emma",
        "Mason", "Ava", "James", "Mia", "Benjamin",
        "Charlotte", "Lucas", "Amelia", "Ethan", "Harper",
        "John", "Emily", "Michael", "Olivia", "William",
        "Sophia", "Daniel", "Isabella", "David", "Emma",
        "Mason", "Ava", "James", "Mia", "Benjamin",
        "Charlotte", "Lucas", "Amelia", "Ethan", "Harper"
    )
    val descList = arrayListOf(
        "Bạn có biết rằng hành tây không chỉ là một loại gia vị mà còn có nhiều lợi ích sức khỏe bất ngờ?",
        "Một chú chó nhỏ bé đã đưa tôi đến những nơi tôi chưa từng tưởng tượng đến.",
        "Nghệ thuật cắm hoa đã trở thành một hình thức nghệ thuật phổ biến, tuy nhiên, điều gì khiến nó trở nên đặc biệt và cuốn hút?",
        "Vũ trụ rộng lớn đầy bí ẩn vẫn là một chủ đề hấp dẫn cho con người. Tại sao chúng ta lại muốn tìm hiểu về sự tồn tại của vũ trụ?",
        "Có bao giờ bạn tự hỏi vì sao hình thức giáo dục truyền thống lại không phù hợp với mọi học sinh và học sinh?",
        "Chiều có hẹn thì ráng mà đi đi, đừng có cái suy nghĩ hủy kèo",
        "Một bức ảnh tuyệt vời mô tả toàn cảnh quá trình một chú chim ưng đang săn mồi.\n" +
                "Để thực hiện được bức ảnh này chắc chắn nhiếp ảnh gia phải có một chiếc máy ảnh với tốc độ chụp liên tiếp khủng",
        "Nhanh trí bỏ phong bì 200k rồi ngồi ăn luôn cho đỡ áy náy ",
        "Người dùng khám phá sản phẩm thông qua các quảng cáo trực tuyến, tiếp cận sản phẩm với Google Ads ngay hôm nay",
        "Bạn có biết rằng hành tây không chỉ là một loại gia vị mà còn có nhiều lợi ích sức khỏe bất ngờ?",
        "Một chú chó nhỏ bé đã đưa tôi đến những nơi tôi chưa từng tưởng tượng đến.",
        "Nghệ thuật cắm hoa đã trở thành một hình thức nghệ thuật phổ biến, tuy nhiên, điều gì khiến nó trở nên đặc biệt và cuốn hút?",
        "Vũ trụ rộng lớn đầy bí ẩn vẫn là một chủ đề hấp dẫn cho con người. Tại sao chúng ta lại muốn tìm hiểu về sự tồn tại của vũ trụ?",
        "Có bao giờ bạn tự hỏi vì sao hình thức giáo dục truyền thống lại không phù hợp với mọi học sinh và học sinh?",
        "Chiều có hẹn thì ráng mà đi đi, đừng có cái suy nghĩ hủy kèo",
        "Một bức ảnh tuyệt vời mô tả toàn cảnh quá trình một chú chim ưng đang săn mồi.\n" +
                "Để thực hiện được bức ảnh này chắc chắn nhiếp ảnh gia phải có một chiếc máy ảnh với tốc độ chụp liên tiếp khủng",
        "Nhanh trí bỏ phong bì 200k rồi ngồi ăn luôn cho đỡ áy náy ",
        "Người dùng khám phá sản phẩm thông qua các quảng cáo trực tuyến, tiếp cận sản phẩm với Google Ads ngay hôm nay",
        "Bạn có biết rằng hành tây không chỉ là một loại gia vị mà còn có nhiều lợi ích sức khỏe bất ngờ?",
        "Một chú chó nhỏ bé đã đưa tôi đến những nơi tôi chưa từng tưởng tượng đến.",
        "Nghệ thuật cắm hoa đã trở thành một hình thức nghệ thuật phổ biến, tuy nhiên, điều gì khiến nó trở nên đặc biệt và cuốn hút?",
        "Vũ trụ rộng lớn đầy bí ẩn vẫn là một chủ đề hấp dẫn cho con người. Tại sao chúng ta lại muốn tìm hiểu về sự tồn tại của vũ trụ?",
        "Có bao giờ bạn tự hỏi vì sao hình thức giáo dục truyền thống lại không phù hợp với mọi học sinh và học sinh?",
        "Chiều có hẹn thì ráng mà đi đi, đừng có cái suy nghĩ hủy kèo",
        "Một bức ảnh tuyệt vời mô tả toàn cảnh quá trình một chú chim ưng đang săn mồi.\n" +
                "Để thực hiện được bức ảnh này chắc chắn nhiếp ảnh gia phải có một chiếc máy ảnh với tốc độ chụp liên tiếp khủng",
        "Nhanh trí bỏ phong bì 200k rồi ngồi ăn luôn cho đỡ áy náy ",
        "Người dùng khám phá sản phẩm thông qua các quảng cáo trực tuyến, tiếp cận sản phẩm với Google Ads ngay hôm nay"
    )
    val sexList = arrayListOf(
        true, false, false, true, false, true, true, true, false, false, true,
        true, false, false, true, false, true, true, true, false, false, true,
        true, false, false, true, false, true, true, true, false, false, true,
        true, false, false, true, false, true, true, true, false, false, true,
        true, false, false, true, false, true, true, true, false, false, true,
    )
    val genresList = arrayListOf(
        "Action", "Adventure", "Comedy", "Drama", "Ecchi",
        "Fantasy", "Gender Bender", "Harem", "Historical", "Horror",
        "Josei", "Martial Arts", "Mature", "Mecha", "Mystery",
        "Psychological", "Romance", "School Life", "Sci-Fi", "Seinen",
        "Shoujo", "Shoujo Ai", "Shounen", "Shounen Ai", "Slice of Life"
    )
    val storyTitles = listOf(
        "Đi tìm lẽ sống",
        "Cuộc đời của Pi",
        "Harry Potter và Hòn Đá Phù Thủy",
        "Tôi là Malala",
        "Sống",
        "Tôi tài giỏi, bạn cũng thế",
        "Không gian trong em",
        "Mắt biếc",
        "Một lít nước mắt",
        "Phật giáo đại thừa",
        "Nỗi buồn chiến tranh",
        "Đời ngắn đừng ngủ dài",
        "Dấn thân về phía mặt trời",
        "Thành phố mộng mơ",
        "Nhà Giả Kim",
        "Đắc nhân tâm",
        "Lão Hạc",
        "Bí mật của tư duy triệu phú",
        "Nhật ký Đặng Thùy Trâm",
        "Số đỏ",
        "Tôi đã làm giàu như thế nào",
        "Hạnh phúc không chờ đợi",
        "Tôi đã kiếm được 100 tỷ đồng như thế nào",
        "Đời ngắn đừng ngủ dài",
        "Điều kỳ diệu của tiếng nói"
    )

    val imageList = arrayListOf(
        R.drawable.ic_launcher_background,
        R.drawable.ic_baseline_book_40,
        R.drawable.ic_baseline_close_40,
        R.drawable.ic_baseline_favorite_40,
        R.drawable.ic_baseline_favorite_border_40,
        R.drawable.ic_baseline_folder_40,
        R.drawable.ic_baseline_format_list_bulleted_24,
        R.drawable.ic_baseline_home_40,
        R.drawable.ic_baseline_local_fire_department_30,
        R.drawable.ic_baseline_mark_email_unread_40,
        R.drawable.ic_baseline_mode_comment_40,
        R.drawable.ic_baseline_more_horiz_40,
        R.drawable.ic_baseline_more_vert_40,
        R.drawable.ic_baseline_navigate_before_40,
        R.drawable.ic_baseline_thumb_up_alt_40,
        R.drawable.ic_baseline_search_40,
        R.drawable.ic_baseline_favorite_40,
        R.drawable.ic_baseline_widgets_40,
        R.drawable.logo_mangarw,
        R.drawable.ic_launcher_foreground,
        R.drawable.ic_baseline_person_40,

    )


    fun getChapter_manga(): ArrayList<Chapter_manga>{
        val list = ArrayList<Chapter_manga>()
        for (i in imageList){
            list.add(Chapter_manga((0..100).random(), Int_Uri().convertUri(i)))
        }
        return list
    }
    fun getChapter_Novel(): Chapter_novel{
        var chapterNovel = Chapter_novel(0, descList[(0..10).random()])
        return chapterNovel

    }

    fun getUsers(): ArrayList<User>{
        val users = ArrayList<User>()

        for ( i in 0..20 ){
            users.add(User(i, nameList[i], Calendar.getInstance().time, sexList[i], "${i} email", i*Random(9).nextInt(),i*Random(99).nextInt(), descList[i], Int_Uri().convertUri(imageList[i]),Int_Uri().convertUri(
                R.drawable.img_1
            ), "11111" ))
        }
        return users
    }
    fun getStory(): ArrayList<Story>{
        val storys = ArrayList<Story>()
        for (i in 0..20){
            storys.add(Story(i+1, storyTitles[i], nameList[i], descList[i], true,
                Int_Uri().convertUri(imageList[i]), Random(10).nextInt(),  sexList[i]))
        }
        return storys
    }
    fun getPosts(): ArrayList<Posts>{
        val list = ArrayList<Posts>()

        for (i in 0.. 20){
            list.add(Posts(i, Random(10).nextInt(), descList[i], Calendar.getInstance().time))
        }
        return list
    }

    fun getGenres() : ArrayList<Genre>{
        val list = ArrayList<Genre>()
        var index = 0;
        for (i in genresList){
            list.add(Genre(index, i))
            index++
        }

        return list
    }
    fun getChapter() : ArrayList<Chapter>{
        val list = ArrayList<Chapter>()
        for (i in storyTitles){
            list.add(Chapter((0..100).random(), i , Calendar.getInstance().time , 1, 100))
        }


        return list
    }







}