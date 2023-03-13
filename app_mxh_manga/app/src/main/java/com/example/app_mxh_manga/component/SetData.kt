package com.example.app_mxh_manga.component

import android.net.Uri
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




}