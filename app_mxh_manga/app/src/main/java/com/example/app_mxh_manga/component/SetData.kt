package com.example.app_mxh_manga.component

import android.net.Uri
import android.util.Log
import com.example.app_mxh_manga.module.Story_Get
import com.example.app_mxh_manga.module.User
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore

class SetData {
    fun setUser(idUser: String, newUser: User, callBack: (Boolean) -> Unit){
        if (idUser != ""){
            FirebaseFirestore.getInstance().collection("User").document(idUser).set(newUser)
                .addOnSuccessListener {
                    callBack(true)
                }
                .addOnFailureListener {
                    callBack(false)
                }
        }
    }
    fun story(story: Story_Get, callback: (Boolean) -> Unit){
        if (story.id_story!=""){
            val storyUpdater = story.story
            FirebaseFirestore.getInstance().collection("Storys").document(story.id_story)
                .set(storyUpdater)
                .addOnSuccessListener {
                    callback(true)
                }
                .addOnFailureListener {
                    callback(false)
                    Log.d(TAG_Update, "update story: $it")
                }
        }else{
            callback(false)
        }



    }




}