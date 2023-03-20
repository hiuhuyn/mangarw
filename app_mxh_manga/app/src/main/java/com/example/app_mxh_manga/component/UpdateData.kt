package com.example.app_mxh_manga.component

import android.util.Log
import com.example.app_mxh_manga.module.*
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore

const val TAG_Update = "update_data"

class UpdateData {
    fun oneImagePost(id_post: String, pathName: String, callback: (Boolean) -> Unit){
        if (id_post!= "" && pathName != ""){
            val postRef = FirebaseFirestore.getInstance().collection("Posts").document(id_post)
            postRef.update("images", FieldValue.arrayUnion(pathName)).addOnSuccessListener {
                callback(true)
            }.addOnFailureListener {
                callback(false)
            }
        }else{
            callback(false)
        }
    }

    fun oneImageChapter(id_chapter:String, pathName: String, callback: (Boolean) -> Unit){
        if (id_chapter!= "" && pathName != ""){
            val postRef = FirebaseFirestore.getInstance().collection("Chapters").document(id_chapter)
            postRef.update("images", FieldValue.arrayUnion(pathName)).addOnSuccessListener {
                callback(true)
            }.addOnFailureListener {
                callback(false)
            }
        }else{
            callback(false)
        }
    }



    fun cover_story(id_story: String, pathName: String, callback: (Boolean) -> Unit){
        if (id_story!="" && pathName!=""){
            val postRef = FirebaseFirestore.getInstance().collection("Storys").document(id_story)
            val updates = hashMapOf<String, Any>(
                "cover_image" to FieldValue.delete(),
                "cover_image" to pathName
            )
            postRef.update(updates).addOnSuccessListener {
                callback(true)
            }.addOnFailureListener {
                callback(false)
            }
        }else{
            callback(false)
        }
    }

    fun avtUser(id_user: String, pathName: String, callback: (Boolean) -> Unit){
        if (id_user!= "" && pathName != ""){
            val postRef = FirebaseFirestore.getInstance().collection("User").document(id_user)
            val updates = hashMapOf<String, Any>(
                "uri_avt" to FieldValue.delete(),
                "uri_avt" to pathName
            )
            postRef.update(updates).addOnSuccessListener {
                callback(true)
            }.addOnFailureListener {
                callback(false)
            }
        }else{
            callback(false)
        }
    }
    fun coverUser(id_user: String, pathName: String, callback: (Boolean) -> Unit){
        if (id_user!="" && pathName!=""){
            val postRef = FirebaseFirestore.getInstance().collection("User").document(id_user)
            val updates = hashMapOf<String, Any>(
                "uri_cover" to FieldValue.delete(),
                "uri_cover" to pathName
            )
            postRef.update(updates).addOnSuccessListener {
                callback(true)
            }.addOnFailureListener {
                callback(false)
            }
        }else{
            callback(false)
        }
    }

    fun name_user(id_user: String, name: String, callback: (Boolean) -> Unit){
        if (id_user!="" && name!=""){
            val postRef = FirebaseFirestore.getInstance().collection("User").document(id_user)
            val updates = hashMapOf<String, Any>(
                "name" to FieldValue.delete(),
                "name" to name
            )
            postRef.update(updates).addOnSuccessListener {
                callback(true)
            }.addOnFailureListener {
                callback(false)
            }
        }else{
            callback(false)
        }

    }
    fun birthday_user(id_user: String, birthday: String, callback: (Boolean) -> Unit){
        if (id_user!="" && birthday!=""){
            val postRef = FirebaseFirestore.getInstance().collection("User").document(id_user)
            val updates = hashMapOf<String, Any>(
                "birthday" to FieldValue.delete(),
                "birthday" to birthday
            )
            postRef.update(updates).addOnSuccessListener {
                callback(true)
            }.addOnFailureListener {
                callback(false)
            }
        }else{
            callback(false)
        }
    }
    fun story_user(id_user: String, story: String, callback: (Boolean) -> Unit){
        if (id_user!="" && story!=""){
            val postRef = FirebaseFirestore.getInstance().collection("User").document(id_user)
            val updates = hashMapOf<String, Any>(
                "story" to FieldValue.delete(),
                "story" to story
            )
            postRef.update(updates).addOnSuccessListener {
                callback(true)
            }.addOnFailureListener {
                callback(false)
            }
        }else{
            callback(false)
        }

    }
    fun sex_user(id_user: String, sex: Boolean, callback: (Boolean) -> Unit){
        if (id_user!=""){
            val postRef = FirebaseFirestore.getInstance().collection("User").document(id_user)
            val updates = hashMapOf<String, Any>(
                "sex" to FieldValue.delete(),
                "sex" to sex
            )
            postRef.update(updates).addOnSuccessListener {
                callback(true)
            }.addOnFailureListener {
                callback(false)
            }
        }else{
            callback(false)
        }

    }
    fun password_user(id_user: String, password: String, callback: (Boolean) -> Unit){
        if (id_user!="" && password !=""){
            val postRef = FirebaseFirestore.getInstance().collection("User").document(id_user)
            val updates = hashMapOf<String, Any>(
                "password" to FieldValue.delete(),
                "password" to password
            )
            postRef.update(updates).addOnSuccessListener {
                callback(true)
            }.addOnFailureListener {
                callback(false)
            }
        }else{
            callback(false)
        }
    }
    fun newLike_post(id_post: String, likes: String, callback: (Boolean) -> Unit){
        if (id_post!=""){
            val postRef = FirebaseFirestore.getInstance().collection("Posts").document(id_post)

            postRef.update("likes" , FieldValue.arrayUnion(likes)).addOnSuccessListener {
                callback(true)
            }.addOnFailureListener {
                callback(false)
            }
        }else{
            callback(false)
        }
    }
    fun removeLike_post(id_post: String, likes: String , callback: (Boolean) -> Unit){
        if (id_post!=""){
            val postRef = FirebaseFirestore.getInstance().collection("Posts").document(id_post)
            postRef.update("likes" , FieldValue.arrayRemove(likes)).addOnSuccessListener {
                callback(true)
            }.addOnFailureListener {
                callback(false)
            }
        }else{
            callback(false)
        }
    }
    fun newFollow_user(id_user: String, id_User_follow: String, callback: (Boolean) -> Unit){
        if (id_user!=""){
            val postRef = FirebaseFirestore.getInstance().collection("User").document(id_user)
            postRef.update("follow_users" , FieldValue.arrayUnion(id_User_follow)).addOnSuccessListener {
                callback(true)
            }.addOnFailureListener {
                callback(false)
            }
        }else{
            callback(false)
        }
    }
    fun removeFollow_user(id_user: String, id_User_follow: String , callback: (Boolean) -> Unit){
        if (id_user!=""){
            val postRef = FirebaseFirestore.getInstance().collection("User").document(id_user)
            postRef.update("follow_users" , FieldValue.arrayRemove(id_User_follow)).addOnSuccessListener {
                callback(true)
            }.addOnFailureListener {
                callback(false)
            }
        }else{
            callback(false)
        }
    }

    fun updateScoreUser(id_user: String, callback: (Boolean) -> Unit){
        if (id_user!=""){
            val chapterRef = FirebaseFirestore.getInstance().collection("User").document(id_user)
            // tăng  giá trị lên 1
            val increment = FieldValue.increment(1)
            chapterRef.update("score", increment)
                .addOnSuccessListener {
                    callback(true)
                }
                .addOnFailureListener {
                    callback(false)
                    Log.d("UpdateData", "updateScoreUser Failure $it")
                }

        }else{
            callback(false)
        }

    }

    fun updateViewStory(id_story: String, callback: (Boolean) -> Unit){
        if (id_story!=""){
            val chapterRef = FirebaseFirestore.getInstance().collection("Storys").document(id_story)
            // tăng  giá trị lên 1
            val increment = FieldValue.increment(1)
            chapterRef.update("views", increment)
                .addOnSuccessListener {
                    callback(true)
                }
                .addOnFailureListener {
                    callback(false)
                    Log.d("UpdateData", "updateViewStory Failure $it")
                }

        }else{
            callback(false)
        }

    }


    fun updateViewChapter(id_chapter: String, callback: (Boolean) -> Unit){
        if (id_chapter!=""){
            val chapterRef = FirebaseFirestore.getInstance().collection("Chapters").document(id_chapter)
            // tăng  giá trị lên 1
            val increment = FieldValue.increment(1)
            chapterRef.update("views", increment)
                .addOnSuccessListener {
                    callback(true)
                }
                .addOnFailureListener {
                    callback(false)
                    Log.d("UpdateData", "updateViewChapter Failure $it")
                }

        }else{
            callback(false)
        }
    }

    fun newFollow_story(id_user: String, id_story: String, callback: (Boolean) -> Unit){
        if (id_user!=""){
            val postRef = FirebaseFirestore.getInstance().collection("User").document(id_user)
            postRef.update("follow_storys" , FieldValue.arrayUnion(id_story)).addOnSuccessListener {
                callback(true)
            }.addOnFailureListener {
                callback(false)
            }
        }else{
            callback(false)
        }
    }
    fun removeFollow_story(id_user: String, id_story: String , callback: (Boolean) -> Unit){
        if (id_user!=""){
            val postRef = FirebaseFirestore.getInstance().collection("User").document(id_user)
            postRef.update("follow_storys" , FieldValue.arrayRemove(id_story)).addOnSuccessListener {
                callback(true)
            }.addOnFailureListener {
                callback(false)
            }
        }else{
            callback(false)
        }
    }

    fun newLike_Chapter(id_user: String, id_chapter: String, callback: (Boolean) -> Unit){
        if (id_user!=""){
            val postRef = FirebaseFirestore.getInstance().collection("Chapters").document(id_chapter)
            postRef.update("likes" , FieldValue.arrayUnion(id_user)).addOnSuccessListener {
                callback(true)
            }.addOnFailureListener {
                callback(false)
            }
        }else{
            callback(false)
        }
    }
    fun removeLike_Chapter(id_user: String, id_chapter: String , callback: (Boolean) -> Unit){
        if (id_user!=""){
            val postRef = FirebaseFirestore.getInstance().collection("Chapters").document(id_chapter)
            postRef.update("likes" , FieldValue.arrayRemove(id_user)).addOnSuccessListener {
                callback(true)
            }.addOnFailureListener {
                callback(false)
            }
        }else{
            callback(false)
        }
    }

    fun ratingUpdate(id_rating: String, score: Int , callback: (Boolean) -> Unit){
        if (id_rating!=""){
            val postRef = FirebaseFirestore.getInstance().collection("Ratings").document(id_rating)
            postRef.update("score", score).addOnSuccessListener {
                callback(true)
            }.addOnFailureListener {
                callback(false)
            }
        }else{
            callback(false)
        }
    }

    fun history(history: History_Get, callback: (Boolean) -> Unit){
        if (history.id_history != ""){
            val updateHistory = hashMapOf(
                "id_chapter" to history.history.id_chapter, // id của chương truyện vừa đọc
                "time_reading" to FieldValue.serverTimestamp() // thời gian hiện tại
            )
            FirebaseFirestore.getInstance().collection("History").document(history.id_history)
                .update(updateHistory)
                .addOnSuccessListener {
                    callback(true)
                }
                .addOnFailureListener {
                    callback(false)
                }
        }
        else{
            callback(false)
        }
    }

    fun chat(chatGet: Chat_Get, callback: (Boolean) -> Unit){
        if (chatGet.id_chat != ""){
            val update = hashMapOf(
                "last_messenger" to chatGet.chat.last_messenger, // id của chương truyện vừa đọc
                "last_messenger_time" to FieldValue.serverTimestamp() // thời gian hiện tại
            )
            FirebaseFirestore.getInstance().collection("Chat").document(chatGet.id_chat)
                .update(update)
                .addOnSuccessListener {
                    callback(true)
                }
                .addOnFailureListener {
                    callback(false)
                }
        }
        else{
            callback(false)
        }
    }

    fun name_story(id_story: String, newName: String, callback: (Boolean) -> Unit){
        if (id_story != "" && newName!=""){
            FirebaseFirestore.getInstance().collection("Storys").document(id_story)
                .update("name", newName)
                .addOnSuccessListener {
                    callback(true)
                }
                .addOnFailureListener {
                    callback(false)
                    Log.d(TAG_Update, "name_story: $it")
                }



        }else{
            callback(false)
        }
    }
    fun describe_story(id_story: String, newdesc: String, callback: (Boolean) -> Unit){
        if (id_story != "" && newdesc!=""){
            FirebaseFirestore.getInstance().collection("Storys").document(id_story)
                .update("describe", newdesc)
                .addOnSuccessListener {
                    callback(true)
                }
                .addOnFailureListener {
                    callback(false)
                    Log.d(TAG_Update, "describe_story: $it")
                }
        }else{
            callback(false)
        }
    }
    fun coverImage_story(id_story: String, newpath: String, callback: (Boolean) -> Unit){
        if (id_story != "" && newpath!=""){
            FirebaseFirestore.getInstance().collection("Storys").document(id_story)
                .update("cover_image", newpath)
                .addOnSuccessListener {
                    callback(true)
                }
                .addOnFailureListener {
                    callback(false)
                    Log.d(TAG_Update, "coverImage_story: $it")
                }
        }else{
            callback(false)
        }
    }







}