package com.example.app_mxh_manga.component

import android.util.Log
import com.example.app_mxh_manga.module.Chat
import com.example.app_mxh_manga.module.Chat_Get
import com.example.app_mxh_manga.module.Messenger_Get
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.toObject

const val TagListener = "ListenerData"

class ListenerData {
    fun listenerMessenger(id_chat: String, callback: ( messengers: ArrayList<Messenger_Get> ?)->Unit){
        if(id_chat!=""){
            FirebaseFirestore.getInstance().collection("Messenger")
                .whereEqualTo("id_chat", id_chat)
                .addSnapshotListener{ snapshot, e ->
                    if (e != null) {
                        Log.d(TagListener, "Listen failed.", e)
                        return@addSnapshotListener
                    }
                    if (snapshot != null) {
                        val list = ArrayList<Messenger_Get>()
                        for(i in snapshot){
                            list.add(Messenger_Get(i.id, i.toObject()))
                        }
                        callback(list)
                    } else {
                        Log.d(TagListener, "Current data: null")
                        callback(null)
                    }
                }
        }else{
            callback(null)
        }
    }

    fun listenerChat(id_user: String, callback: (ArrayList<Chat_Get>?) -> Unit){
        if (id_user!=""){
            val listChat = ArrayList<Chat_Get>()
            val db = FirebaseFirestore.getInstance().collection("Chat")
            db.whereEqualTo("id_user1", id_user)
                .orderBy("last_messenger_time", Query.Direction.DESCENDING)
                .addSnapshotListener { value, error ->
                    listChat.clear()
                    if (error != null) {
                        Log.d(TAGGET, "Listen failed. $error")
                        return@addSnapshotListener
                    }
                    if (value!=null){
                        for (i in value){
                            val chat = i.toObject<Chat>()
                            if (chat.last_messenger_time!=null){
                                listChat.add(Chat_Get(i.id,chat))
                            }
                        }
                        db.whereEqualTo("id_user2", id_user)
                            .orderBy("last_messenger_time", Query.Direction.DESCENDING)
                            .addSnapshotListener { value2, error2 ->
                                if (error2 != null) {
                                    Log.d(TAGGET, "Listen failed. $error2")
                                    return@addSnapshotListener
                                }
                                if (value2!=null){
                                    for (i in value2){
                                        val chat = i.toObject<Chat>()
                                        if (chat.last_messenger_time!=null){
                                            listChat.add(Chat_Get(i.id,chat))
                                        }
                                    }
                                    callback(listChat)
                                }else{
                                    callback(listChat)
                                }
                            }
                    }else{
                        db.whereEqualTo("id_user2", id_user)
                            .orderBy("last_messenger_time", Query.Direction.DESCENDING)
                            .addSnapshotListener { value2, error2 ->
                                if (error2 != null) {
                                    Log.d(TagListener, "Listen failed.", error2)
                                    return@addSnapshotListener
                                }
                                if (value2!=null){
                                    for (i in value2){
                                        val chat = i.toObject<Chat>()
                                        if (chat.last_messenger_time!=null){
                                            listChat.add(Chat_Get(i.id,chat))
                                        }
                                    }
                                    callback(listChat)
                                }
                            }
                    }
                }
        }else{
            callback(null)
        }
    }



}