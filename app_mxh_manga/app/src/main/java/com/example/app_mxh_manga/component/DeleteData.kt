package com.example.app_mxh_manga.component

import android.util.Log
import com.example.app_mxh_manga.module.Chapter_Get
import com.example.app_mxh_manga.module.Posts
import com.example.app_mxh_manga.module.Story_Get
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage

const val TAGDEL = "TagDelete"
class DeleteData {
    fun delOneImage(pathName: String, callback: (Boolean) -> Unit){
        if(pathName != ""){
            val storageRef = Firebase.storage.reference
            val imageRef = storageRef.child(pathName)
            imageRef.delete().addOnSuccessListener {
                callback(true)
            }.addOnFailureListener {
                // Xóa ảnh thất bại
                callback(false)
            }
        }else{
            callback(false)
        }
    }

    fun delPost(id_post: String, callback: (Boolean) -> Unit){
        if (id_post!=""){
            val postRef  = FirebaseFirestore.getInstance().collection("Posts").document(id_post)
            postRef.get().addOnSuccessListener {
                val post = it.toObject<Posts>()
                if (post == null){
                    Log.d("GetData", "delPost: post is null")
                }else{
                    for (itemIMG in post.images){
                        delOneImage(itemIMG){
                        }
                    }
                }
            }.addOnFailureListener {
                callback(false)
            }


            postRef.delete().addOnSuccessListener {
                delAllCmtPost(id_post){
                    callback(true)
                }
            }.addOnFailureListener {
                callback(false)
            }

        }else{
            callback(false)
        }

    }
    fun delAllCmtPost(id_post: String, callback: (Boolean) -> Unit){
        if (id_post!=""){
            val db = FirebaseFirestore.getInstance()
            val batch =db.batch()
            val query = db.collection("Comment_post").whereEqualTo("id_post", id_post)
                .get()
                .addOnSuccessListener {
                    for(i in it.documents){
                        batch.delete(i.reference)
                    }
                    batch.commit().addOnCompleteListener {
                        callback(true)
                    }
                }
                .addOnFailureListener {
                    callback(false)
                }
        }else{
            callback(false)
        }
    }


    fun delChapter(chapterGet: Chapter_Get, callback: (Boolean) -> Unit){
        if (chapterGet.id_chapter!=""){
            val chapterRef = FirebaseFirestore.getInstance().collection("Chapters").document(chapterGet.id_chapter)

            chapterRef.delete().addOnSuccessListener {
                for (i in chapterGet.chapter.images){
                    delOneImage(i){}
                }
                delAllCmtChapter(chapterGet.id_chapter){
                    callback(true)
                }
            }.addOnFailureListener {
                callback(false)
            }
        }else{
            callback(false)
        }
    }

    fun delAllCmtChapter(id_chapter: String, callback: (Boolean) -> Unit){
        if (id_chapter!=""){
            val db = FirebaseFirestore.getInstance()
            val batch =db.batch()
            val query = db.collection("Comment_chapter").whereEqualTo("id_chapter", id_chapter)
                .get()
                .addOnSuccessListener {
                    for(i in it.documents){
                        batch.delete(i.reference)
                    }
                    batch.commit().addOnCompleteListener {
                        callback(true)
                    }
                }
                .addOnFailureListener {
                    callback(false)
                }
        }else{
            callback(false)
        }
    }

    fun delStory(storyGet: Story_Get, callback: (Boolean) -> Unit){
        if (storyGet.id_story != ""){
            val db  = FirebaseFirestore.getInstance()
            val storyRef = db.collection("Storys").document(storyGet.id_story)
            val chapterRef = db.collection("Chapters")
            storyRef.delete().addOnSuccessListener {
                val chapterDel = chapterRef.whereEqualTo("id_story", storyGet.id_story)
                chapterDel.get()
                    .addOnSuccessListener {
                        for (i in it){
                            delChapter(Chapter_Get(i.id, i.toObject())){
                                callback(true)
                                if (!it){
                                    Log.e(TAGDEL, "del chapter failure")
                                }
                            }
                        }
                    }.addOnFailureListener {
                        callback(true)
                        Log.e(TAGDEL, "getChap by story failure: $it")
                    }
                delOneImage(storyGet.story.cover_image){
                    if (!it){
                        Log.e(TAGDEL, "del image story failure")
                    }
                }

            }.addOnFailureListener {
                callback(false)
            }
        }else{
            callback(false)
        }
    }






}