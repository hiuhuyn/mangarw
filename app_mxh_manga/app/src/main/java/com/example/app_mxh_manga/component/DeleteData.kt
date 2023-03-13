package com.example.app_mxh_manga.component

import android.util.Log
import com.example.app_mxh_manga.module.Posts
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage

class DeleteData {
    fun oneImage(pathName: String, callback: (Boolean) -> Unit){
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
                    Log.d("GetData", "delPost: post is")
                }else{
                    for (itemIMG in post.images){
                        oneImage(itemIMG){
                            Log.d("DeleteData", "delPost delOneImage: $itemIMG")
                        }
                    }
                }
            }.addOnFailureListener {
                callback(false)
            }

            postRef.delete().addOnSuccessListener {
                callback(true)
            }.addOnFailureListener {
                callback(false)
            }

        }else{
            callback(false)
        }

    }



}