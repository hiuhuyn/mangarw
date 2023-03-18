package com.example.app_mxh_manga.component

import android.net.Uri
import android.util.Log
import android.widget.Toast
import com.example.app_mxh_manga.module.*
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import java.text.SimpleDateFormat
import java.util.*

class AddData {
    fun newUser(user: User, callback: (Boolean) -> Unit){
        val userRef = FirebaseFirestore.getInstance().collection("User")
        userRef.add(user).addOnSuccessListener {
            callback(true)
        }.addOnFailureListener {
            callback(false)
        }
    }
    fun newImage(uri: Uri, pathSave: String, callback: (check: Boolean) -> Unit){
        val storageReference = FirebaseStorage.getInstance().reference
        val mountainImageRef = storageReference.child("${pathSave}")
        mountainImageRef.putFile(uri)
            .addOnSuccessListener {
                callback(true)
            }
            .addOnFailureListener {
                callback(false)
            }
    }
    fun newPost(post: Posts, callback: (id: String?) -> Unit){
        val postRef = FirebaseFirestore.getInstance().collection("Posts")
        postRef.add(post).addOnSuccessListener {
            callback(it.id)
        }.addOnFailureListener {
            callback(null)
        }
    }

    fun newStory(post: Story, callback: (id: String?) -> Unit){
        val storyRef = FirebaseFirestore.getInstance().collection("Storys")
        storyRef.add(post).addOnSuccessListener {
            callback(it.id)
        }.addOnFailureListener {
            callback(null)
        }
    }

    fun newChapter(chapter: Chapter, callback: (id: String?) -> Unit){
        val chapterRef = FirebaseFirestore.getInstance().collection("Chapters")
        chapterRef.add(chapter).addOnSuccessListener {
            callback(it.id)
        }.addOnFailureListener {
            callback(null)
        }
    }
    fun newRating(rating: Rating, callback: (id: String?) -> Unit){
        Log.d("AddData", "newRating ${rating.score} ")
        val ratingRef = FirebaseFirestore.getInstance().collection("Ratings")
        ratingRef.add(rating).addOnSuccessListener {
            callback(it.id)
        }.addOnFailureListener {
            callback(null)
        }
    }
    fun newCmtPost(commentPost: Comment_post, callback: (id: String?) -> Unit){
        FirebaseFirestore.getInstance().collection("Comment_post").add(commentPost).addOnSuccessListener {
            callback(it.id)
        }.addOnFailureListener {
            callback(null)
        }
    }
    fun newCmtChapter(commentChapter: Comment_chapter, callback: (id: String?) -> Unit){
        FirebaseFirestore.getInstance().collection("Comment_chapter").add(commentChapter).addOnSuccessListener {
            callback(it.id)
        }.addOnFailureListener {
            callback(null)
        }
    }

    fun newHistory(history: History, callback: (Boolean) -> Unit){
        FirebaseFirestore.getInstance().collection("History").add(history).addOnSuccessListener {
            callback(true)
        }.addOnFailureListener {
            callback(false)
        }
    }







}