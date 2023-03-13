package com.example.app_mxh_manga.component

import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore

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





}