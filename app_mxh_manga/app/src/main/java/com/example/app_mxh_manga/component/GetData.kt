package com.example.app_mxh_manga.component

import android.net.Uri
import android.nfc.Tag
import android.util.Log
import com.example.app_mxh_manga.module.*
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage

const val TAGGET ="GetData"
class GetData{
    private val db = Firebase.firestore
    fun getUserByEmail(email: String, callback: (user: User_Get?) -> Unit){
        if (email != ""){
            val usersReflection = db.collection("User")
            usersReflection.whereEqualTo("email", email)
                .get()
                .addOnSuccessListener {
                    if(it.isEmpty){
                        callback(null)
                    }else{
                        val user: User? = it.documents.first().toObject()
                        if (user!=null){
                            val userGet = User_Get(it.first().id,user)
                            callback(userGet)
                        }else{
                            callback(null)
                        }
                    }
                }
                .addOnFailureListener {
                    Log.d(TAGGET, "Khong truy xuat duoc: ", it)
                    callback(null)
                }
        }else{
            callback(null)
        }

    }


    fun getUserByID(id_user: String, callback: (userGet: User_Get?) -> Unit){
        if (id_user != ""){
            val userRef = db.collection("User").document(id_user)
            userRef.get().addOnSuccessListener {
                if (it != null){
                    val user = it.toObject(User::class.java)!!
                    val userGet = User_Get(it.id, user)
                    callback(userGet)
                }else{
                    Log.d(TAGGET, "No such document")
                    callback(null)
                }
            }.addOnFailureListener {
                Log.d(TAGGET, "get failed with ", it)
                callback(null)
            }
        }else{
            callback(null)
        }
    }
    fun getUserSortScore(callback: (user_get: ArrayList<User_Get>?) -> Unit){
        FirebaseFirestore.getInstance().collection("User")
            .orderBy("score", Query.Direction.DESCENDING)
            .get()
            .addOnSuccessListener {
                if(it.isEmpty){
                    callback(null)
                }else{
                    val list = ArrayList<User_Get>()
                    for (i in it){
                        list.add(User_Get(i.id, i.toObject()))
                    }
                    callback(list)
                }
            }
            .addOnFailureListener {
                callback(null)
                Log.e(TAGGET, " getUserSortScore failure: $it")
            }
    }

    fun getAllUser(callback: (userGet: ArrayList<User_Get>?) -> Unit){
        db.collection("User")
            .get()
            .addOnSuccessListener { result ->
                if (!result.isEmpty){
                    val listUser_Get = ArrayList<User_Get>()
                    for (document in result) {
                        val user = document.toObject(User::class.java)
                        listUser_Get.add(User_Get(document.id, user))
                    }
                    callback(listUser_Get)
                }else{
                    callback(null)
                }

            }
            .addOnFailureListener { exception ->
                Log.w(TAGGET, "getAllUser Error getting documents.", exception)
                callback(null)
            }
    }

    fun usersFollowUser(usersAreFollowed: String, callback: (followers :ArrayList<User_Get>?) -> Unit){
        if (usersAreFollowed!= ""){
            val userRef = FirebaseFirestore.getInstance().collection("User")
            userRef.whereArrayContains("follow_users", usersAreFollowed)
                .get()
                .addOnSuccessListener {
                    if (it.isEmpty){
                        callback(null)
                    }else{
                        val listId = ArrayList<User_Get>()
                        for (i in it){
                            listId.add(User_Get(i.id, i.toObject()))
                        }
                        callback(listId)
                    }
                }.addOnFailureListener {
                    callback(null)
                }
        }else{
            callback(null)
        }


    }

    fun getImage(src: String, callback: (Uri?) -> Unit){
        if (src!= ""){
            val storageReference2 = FirebaseStorage.getInstance().getReference(src)
            storageReference2.downloadUrl.addOnSuccessListener {
                if (it!=null){
                    callback(it)
                }else{
                    callback(null)
                }
            }.addOnFailureListener {
                callback(null)
            }
        }else{
            callback(null)
        }
    }

    fun getStoryIDNewUpdate(callback: (ArrayList<String>?) -> Unit){
        val db = FirebaseFirestore.getInstance()
        db.collection("Chapters")
            .orderBy("date_submit", Query.Direction.DESCENDING)
            .get()
            .addOnSuccessListener { querySnapshot ->
                if(querySnapshot.isEmpty){
                    callback(null)
                }else{
                    val storyIds = ArrayList<String>()
                    for (i in querySnapshot){
                        val storyID = i.getString("id_story")
                        if (!storyIds.contains(storyID) && storyID != null){
                            storyIds.add(storyID)
                        }
                    }
                    if (storyIds.isEmpty()){
                        callback(null)
                    }else{
                        callback(storyIds)
                    }
                }
            }
            .addOnFailureListener {
                callback(null)
            }
    }
    fun userFollowStory(id_story: String, callback: (array: ArrayList<User_Get>?) -> Unit){
        if (id_story!=""){
            val userRef = FirebaseFirestore.getInstance().collection("User")
            userRef.whereArrayContains("follow_storys", id_story).get()
                .addOnSuccessListener {
                    if (it.isEmpty){
                        callback(null)
                    }else{
                        val list = ArrayList<User_Get>()
                        for (i in it){
                            list.add(User_Get(i.id, i.toObject()))
                        }
                        callback(list)
                    }
                }.addOnFailureListener {
                    callback(null)
                }
        }else{
            callback(null)
        }
    }


    fun getPost_IdUser(id_user: String, callback: (postsGet: ArrayList<Post_Get>?) -> Unit){
        if (id_user != ""){
            val usersReflection = db.collection("Posts")
            usersReflection.whereEqualTo("id_user", id_user)
                .orderBy("date_submit", Query.Direction.DESCENDING)
                .get()
                .addOnSuccessListener {
                    if(it.isEmpty){
                        Log.d("GetData", "getPost_IdUser: isEmpty, ${id_user}")
                        callback(null)
                    }else{
                        val postGet = ArrayList<Post_Get>()
                        for (i in it){
                            val post: Posts = i.toObject(Posts::class.java)
                            postGet.add(Post_Get(i.id, post))
                        }
                        callback(postGet)
                    }
                }
                .addOnFailureListener {
                    Log.d("GetData", "Khong truy xuat duoc: $it")
                    callback(null)
                }
        }else{
            Log.d("GetData", "getPost_IdUser Null")
            callback(null)
        }

    }

    fun getPost(id_post: String, callback: (Post_Get?) -> Unit){
        if (id_post!= ""){
            val userRef = db.collection("Posts").document(id_post)
            userRef.get().addOnSuccessListener {
                if (it != null){
                    val post = it.toObject(Posts::class.java)!!
                    callback(Post_Get(it.id,post))
                }else{
                    Log.d("GetData", "GetPost No such document ")
                    callback(null)
                }
            }.addOnFailureListener {
                Log.d(TAGGET, "GetPost get failed with ", it)
                callback(null)
            }
        }else{
            callback(null)
        }
    }

    fun getAllPosts(callback: (postGet: ArrayList<Post_Get>?) -> Unit){
        val postRef = FirebaseFirestore.getInstance().collection("Posts")
            .orderBy("date_submit", Query.Direction.DESCENDING)
            .get().addOnSuccessListener {
            if (it.isEmpty){
                Log.d(TAGGET, "getAllPosts isEmpty")
                callback(null)
            }else{
                val postsGet = ArrayList<Post_Get>()
                for (item in it){
                    postsGet.add(Post_Get(item.id, item.toObject()))
                }
                callback(postsGet)
            }
        }.addOnFailureListener {
            callback(null)
                Log.d(TAGGET, "getAllPosts: $it")
        }
    }

    fun getStoryByIdUser(id_user: String, callback: (storyGet: ArrayList<Story_Get>?) -> Unit){
        if (id_user!= ""){
            val storyRef  = FirebaseFirestore.getInstance().collection("Storys")
            storyRef.whereEqualTo("id_user", id_user).get()
                .addOnSuccessListener {
                    if (it.isEmpty){
                        callback(null)
                    }else{
                        val storyGet: ArrayList<Story_Get> =ArrayList()
                        for (item in it){
                            storyGet.add(Story_Get(item.id, item.toObject()))
                        }
                        callback(storyGet)
                    }
                }.addOnFailureListener {
                    callback(null)
                }
        }else{
            callback(null)
        }
    }
    fun getStoryByID(id_story: String, callback: (storyGet: Story_Get?)->Unit){
        if (id_story!=""){
            val storyRef = FirebaseFirestore.getInstance().collection("Storys")
            storyRef.document(id_story).get()
                .addOnSuccessListener {
                    val story = it.toObject<Story>()
                    if (story!=null){
                        callback(Story_Get(it.id,story))
                    }else{
                        callback(null)
                    }

                }.addOnFailureListener {
                    callback(null)
                }
        }
        else{
            callback(null)
        }
    }

    fun getChapterByIdStory(id_story: String, callback: (chapters: ArrayList<Chapter_Get>?)->Unit){
        if (id_story!=""){
            val storyRef = FirebaseFirestore.getInstance().collection("Chapters")
                .whereEqualTo("id_story", id_story)
                .orderBy("date_submit", Query.Direction.DESCENDING)
                .get()
                .addOnSuccessListener {
                    if (it.isEmpty){
                        callback(null)
                        Log.d(TAGGET, "getChapterByIdStory isEmpty")
                    }else{
                        val chapters = ArrayList<Chapter_Get>()
                        for (item in it){
                            chapters.add(Chapter_Get(item.id, item.toObject()))
                        }
                        callback(chapters)
                    }

                }.addOnFailureListener {
                    Log.d(TAGGET, "getChapterByIdStory $it")
                    callback(null)
                }
        }
        else{
            callback(null)
        }
    }
    fun getStorySort_view(callback: (storys: ArrayList<Story_Get>?) -> Unit){
        FirebaseFirestore.getInstance().collection("Storys")
            .orderBy("views", Query.Direction.DESCENDING)
            .get().addOnSuccessListener {
                if (it.isEmpty){
                    callback(null)
                }else{
                    val storys = ArrayList<Story_Get>()
                    for (i in it){
                        storys.add(Story_Get(i.id, i.toObject()))
                    }
                    callback(storys)
                }
            }
            .addOnFailureListener {
                callback(null)
                Log.e(TAGGET, "getStorySort_view addOnFailureListener $it")
            }
    }

    fun getChapterByIDChapter(id_chapter: String, callback: (chapter: Chapter_Get?)->Unit){
        if(id_chapter!=""){
            val chapterRef = FirebaseFirestore.getInstance().collection("Chapters").document(id_chapter)
            chapterRef.get().addOnSuccessListener {
                val chapter = it.toObject<Chapter>()
                if (chapter!=null){
                    callback(Chapter_Get(it.id, chapter))
                }else{
                    callback(null)
                }
            }.addOnFailureListener {
                callback(null)
            }
        }else{
            callback(null)
        }
    }

    fun getAllGenre(callback: (geners: ArrayList<Genre_Get>?)->Unit){
        val genreRef = FirebaseFirestore.getInstance().collection("Genres")
        genreRef.get().addOnSuccessListener {
            if (it.isEmpty){
                callback(null)
            }else{
                val genres = ArrayList<Genre_Get>()

                for (item in it){
                    genres.add(Genre_Get(item.id, item.toObject()))
                }
                callback(genres)
            }
        }.addOnFailureListener {
            callback(null)
        }
    }

    fun getGenreByIdGenre(id_genre: String, callback: (genre:Genre_Get?)->Unit){
        if (id_genre!=""){
            val genreRef = FirebaseFirestore.getInstance().collection("Genres").document(id_genre)
                .get().addOnSuccessListener {
                    val genre = it.toObject<Genre>()
                    if (genre!=null){
                        callback(Genre_Get(it.id,genre))
                    }else{
                        callback(null)
                    }

                }.addOnFailureListener {
                    callback(null)
                }


        }else{
            callback(null)
        }


    }

    fun getAllStory_Type(type: Boolean , callback: (ArrayList<Story_Get>?) -> Unit){
        val storyRef = FirebaseFirestore.getInstance().collection("Storys")
        storyRef.whereEqualTo("type",  type).get()
            .addOnSuccessListener {
                if (it.isEmpty){
                    callback(null)
                }else{
                    val list = ArrayList<Story_Get>()
                    for (item in it){
                        list.add(Story_Get(item.id, item.toObject()))
                    }
                    callback(list)
                }

            }.addOnFailureListener {
                callback(null)
            }
    }
    fun getAllStory(callback: (ArrayList<Story_Get>?) -> Unit){
        val storyRef = FirebaseFirestore.getInstance().collection("Storys")
        storyRef.get()
            .addOnSuccessListener {
                if (it.isEmpty){
                    callback(null)
                }else{
                    val list = ArrayList<Story_Get>()
                    for (item in it){
                        list.add(Story_Get(item.id, item.toObject()))
                    }
                    callback(list)
                }

            }.addOnFailureListener {
                callback(null)
            }
    }

    fun getRatingStory(id_story: String, callback: (ArrayList<Rating_Get>?) -> Unit){
        if (id_story!=""){
            val ratingRef = FirebaseFirestore.getInstance().collection("Ratings")
            ratingRef.whereEqualTo("id_story", id_story)
                .get()
                .addOnSuccessListener {
                    val list = ArrayList<Rating_Get>()
                    for (i in it){
                        list.add(Rating_Get(i.id, i.toObject()))
                    }
                    callback(list)
                }
                .addOnFailureListener {
                    callback(null)
                    Log.d("GetData", "getRatingStory $it")
                }

        }else{
            callback(null)
        }
    }

    fun getCmtPostByIDPost(id_post: String, callback: (array: ArrayList<Comment_Post_Get>?) -> Unit){
        if (id_post!=""){
            val cmtRef = FirebaseFirestore.getInstance().collection("Comment_post")
            cmtRef.whereEqualTo("id_post", id_post)
                .orderBy("date_submit", Query.Direction.DESCENDING)
                .get()
                .addOnSuccessListener {
                    if (it.isEmpty){
                        Log.d(TAGGET, "getCmtPostByIDPost isEmpty")
                        callback(null)
                    }else{
                        val list = ArrayList<Comment_Post_Get>()
                        for(i in it){
                            list.add(Comment_Post_Get(i.id, i.toObject()))
                        }
                        callback(list)
                    }
                }
                .addOnFailureListener {
                    callback(null)
                    Log.d(TAGGET, "getCmtPostByIDPost addOnFailureListener:  $it" )
                }
        }else{
            callback(null)
        }
    }
    fun getCmtPostByIDCmt(id_comment: String, callback: (Comment_Post_Get?) -> Unit){
        if (id_comment!=""){
            val cmtRef = FirebaseFirestore.getInstance().collection("Comment_post")
                .document(id_comment)
                .get()
                .addOnSuccessListener {
                    if (it!=null ){
                        callback(Comment_Post_Get(it.id, it.toObject<Comment_post>()!!))
                    }else{
                        callback(null)
                    }
                }
                .addOnFailureListener {
                    callback(null)
                    Log.d(TAGGET, "getCmtByIDCmt addOnFailureListener: $it")
                }
        }else{
            callback(null)
        }
    }
    fun getCmtChapterByIDChapter(id_chapter: String, callback: (array: ArrayList<Comment_Chapter_Get>?) -> Unit){
        if (id_chapter!=""){
            val cmtRef = FirebaseFirestore.getInstance().collection("Comment_chapter")
            cmtRef.whereEqualTo("id_chapter", id_chapter)
                .orderBy("date_submit", Query.Direction.DESCENDING)
                .get()
                .addOnSuccessListener {
                    if (it.isEmpty){
                        Log.d(TAGGET, "getCmtChapterByIDChapter isEmpty")
                        callback(null)
                    }else{
                        val list = ArrayList<Comment_Chapter_Get>()
                        for(i in it){
                            list.add(Comment_Chapter_Get(i.id, i.toObject()))
                        }
                        callback(list)
                    }
                }
                .addOnFailureListener {
                    callback(null)
                    Log.d(TAGGET, "getCmtChapterByIDChapter addOnFailureListener:  $it" )
                }
        }else{
            callback(null)
        }
    }
    fun getCmtChapterByIDCmt(id_comment: String, callback: (Comment_Chapter_Get?) -> Unit){
        if (id_comment!=""){
            val cmtRef = FirebaseFirestore.getInstance().collection("Comment_chapter")
                .document(id_comment)
                .get()
                .addOnSuccessListener {
                    if (it!=null){
                        val comment = it.toObject<Comment_chapter>()
                        if (comment!=null){
                            callback(Comment_Chapter_Get(it.id, comment))
                        }

                    }else{
                        callback(null)
                    }
                }
                .addOnFailureListener {
                    callback(null)
                    Log.d(TAGGET, "getCmtChapterByIDCmt addOnFailureListener: $it")
                }
        }else{
            callback(null)
        }
    }

    fun getHistoryByUser(id_user: String, callback: (ArrayList<History_Get>?) -> Unit){
        if (id_user!=""){
            val db = FirebaseFirestore.getInstance().collection("History")
            db.whereEqualTo("id_user", id_user)
                .orderBy("time_reading", Query.Direction.DESCENDING)
                .get()
                .addOnSuccessListener {
                    if (it.isEmpty){
                        callback(null)
                    }else{
                        val list = ArrayList<History_Get>()
                        for (i in it){
                            list.add(History_Get(i.id, i.toObject()))
                        }
                        callback(list)
                    }
                }
                .addOnFailureListener {
                    callback(null)
                    Log.e(TAGGET, "getHistory $it")
                }
        }else{
            callback(null)
        }
    }
    fun getHistoryByUser_Story(id_user: String, id_story: String, callback: (History_Get?) -> Unit){
        if (id_user!=""){
            val db = FirebaseFirestore.getInstance().collection("History")
            db.whereEqualTo("id_user", id_user).whereEqualTo("id_story", id_story)
                .get()
                .addOnSuccessListener {
                    if (it.isEmpty){
                        callback(null)
                    }else{
                        val history = History_Get(it.first().id, it.first().toObject())
                        Log.d(TAGGET, "getHistoryByUser_Story id: ${history.id_history}")
                        callback(history)
                    }
                }
                .addOnFailureListener {
                    callback(null)
                    Log.e(TAGGET, "getHistory $it")
                }
        }else{
            callback(null)
        }
    }










}