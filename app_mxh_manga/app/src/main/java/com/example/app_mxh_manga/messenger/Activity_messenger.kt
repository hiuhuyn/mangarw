package com.example.app_mxh_manga.messenger

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Spinner
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.app_mxh_manga.IDUSER
import com.example.app_mxh_manga.R
import com.example.app_mxh_manga.component.*
import com.example.app_mxh_manga.homePage.component.profile.component.Activity_profile
import com.example.app_mxh_manga.module.*
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.toObject
import com.squareup.picasso.Picasso
import java.util.Calendar

class Activity_messenger : AppCompatActivity() {
    private lateinit var adapter_RV_messenger: Adapter_RV_messenger
    private lateinit var ib_return: ImageButton
    private lateinit var iv_avt: ImageView
    private lateinit var tv_name: TextView
    private lateinit var rv_messenger: RecyclerView
    private lateinit var spinner_gallery: Spinner
    private lateinit var edt_content: EditText
    private lateinit var ib_send: ImageButton
    private lateinit var rv_images: RecyclerView
    private var listMessage = ArrayList<Messenger_Get>()
    private var id_me = ""
    private var user_other = User_Get()
    private var id_chat = ""
//    private var listImage = ArrayList<Uri>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_messenger)
        ib_return = findViewById(R.id.ib_return)
        iv_avt = findViewById(R.id.iv_avt)
        tv_name = findViewById(R.id.tv_name)
        rv_messenger = findViewById(R.id.rv_messenger)
        spinner_gallery = findViewById(R.id.spinner_gallery)
        edt_content = findViewById(R.id.edt_content)
        ib_send = findViewById(R.id.ib_send)
        rv_images = findViewById(R.id.rv_images)
        id_me = ModeDataSaveSharedPreferences(this).getIdUser()

        val bundle = intent.extras
        id_chat = bundle?.getString(IDCHAT).toString()

    }

    override fun onStart() {
        super.onStart()
        listMessage.clear()
        val dialog = Notification(this).dialogLoading("Loading...")
        dialog.show()
        GetData().getChatByID(id_chat){ chatGet ->
            if (chatGet!=null){
                if (chatGet.chat.id_user1 != id_me){
                    user_other.id_user = chatGet.chat.id_user1
                }else{
                    user_other.id_user = chatGet.chat.id_user2
                }

                GetData().getUserByID(user_other.id_user){ userGet ->
                    dialog.dismiss()
                    if(userGet!=null){
                        user_other = userGet
                        tv_name.text = userGet.user.name
                        GetData().getImage(user_other.user.uri_avt){
                            if (it!=null){
                                Picasso.with(this).load(it).into(iv_avt)
                            }
                        }
                        adapter_RV_messenger = Adapter_RV_messenger(listMessage, user_other)
                        rv_messenger.adapter = adapter_RV_messenger
                        GetData().getMessengerBy_IdChat(id_chat){
                            if (it!=null){
                                listMessage = it
                                adapter_RV_messenger.notifyDataSetChanged()
                            }

                        }
                        addEvent()
                    }
                }


            }
        }



    }

    private fun addEvent() {
        FirebaseFirestore.getInstance().collection("Messenger")
            .whereEqualTo("id_chat", id_chat)
            .orderBy("date_submit", Query.Direction.ASCENDING)
            .addSnapshotListener{ snapshot, e ->
                if (e != null) {
                    Log.d(TagListener, "Listen failed.", e)
                    return@addSnapshotListener
                }
                if (snapshot != null) {
                    val list = ArrayList<Messenger_Get>()
                    for(i in snapshot){
                        list.add(Messenger_Get(i.id, i.toObject()))
                        Log.d(TagListener, "listener ${i.toObject<Messenger>().content}")
                    }
                    listMessage.clear()
                    listMessage.addAll(list)

                    adapter_RV_messenger.update(listMessage)

                } else {
                    Log.d(TagListener, "Current data: null")
                }
            }
        ib_return.setOnClickListener {
            finish()
        }
        iv_avt.setOnClickListener {
            val i = Intent(this, Activity_profile::class.java)
            val bundle = Bundle()
            bundle.putString(IDUSER, user_other.id_user)
            i.putExtras(bundle)
            startActivity(i)
        }




        ib_send.setOnClickListener {
            val messenger = Messenger(id_chat, id_me, user_other.id_user, edt_content.text.toString().trim())
            edt_content.text.clear()
            val dialog = Notification(this).dialogLoading("send...")
            dialog.show()
            AddData().newMessenger(messenger){
                if(it!=null){
                    val chat = Chat_Get(id_chat, Chat(id_me, user_other.id_user, messenger.content, messenger.date_submit))
                    UpdateData().chat(chat){
                        dialog.dismiss()
                    }

                }
            }
        }
    }




}