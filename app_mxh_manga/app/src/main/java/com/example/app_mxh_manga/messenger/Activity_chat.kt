package com.example.app_mxh_manga.messenger

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.app_mxh_manga.R
import com.example.app_mxh_manga.component.*
import com.example.app_mxh_manga.module.Chat
import com.example.app_mxh_manga.module.Chat_Get
import com.example.app_mxh_manga.module.User_Get
import com.squareup.picasso.Picasso

const val IDCHAT = "id_chat"

class Activity_chat : AppCompatActivity() {
    private var idUser = ""
    private lateinit var userGet:User_Get
    private lateinit var iv_avt: ImageView
    private lateinit var rv_user: RecyclerView
    private lateinit var rv_messenger: RecyclerView
    private lateinit var ib_exit: ImageButton
    private lateinit var view_search: View
    private lateinit var adapterChat: Adapter_RV_Chat


    private var listUserFollowing = ArrayList<User_Get>()
    private var listChat = ArrayList<Chat_Get>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)
        idUser = ModeDataSaveSharedPreferences(this).getIdUser()
        iv_avt = findViewById(R.id.iv_avt)
        rv_user = findViewById(R.id.rv_user)
        rv_messenger = findViewById(R.id.rv_messenger)
        ib_exit = findViewById(R.id.ib_exit)
        view_search = findViewById(R.id.viewSearch)


    }

    override fun onStart() {
        super.onStart()
        listChat.clear()
        adapterChat = Adapter_RV_Chat(listChat, idUser)
        val dialog = Notification(this).dialogLoading("Loading...")
        dialog.show()
        GetData().getListChat(idUser){ chatGets ->
            dialog.dismiss()
            if (chatGets!=null){
                listChat.clear()
                listChat.addAll(chatGets)
                adapterChat.update(listChat)
            }
        }
        ListenerData().listenerChat(idUser){
            if (it!=null){
                listChat.clear()
                listChat.addAll(it)
                adapterChat.update(listChat)
            }
        }
        GetData().getUserByID(idUser){ user ->
            if(user!=null){
                userGet = user
                GetData().getImage(user.user.uri_avt){
                    if(it!=null){
                        Picasso.with(this).load(it).into(iv_avt)
                    }
                }
                listUserFollowing.clear()
                val adaptet_userFollowing = Adapter_RV_user_horizontal(listUserFollowing, object : OnItemClick{
                    override fun onItemClick(position: Int) {
                        GetData().getChat(idUser, listUserFollowing[position].id_user){ chatGet ->
                            if (chatGet!=null){
                                val intent = Intent(this@Activity_chat, Activity_messenger::class.java)
                                val bundle = Bundle()
                                bundle.putString(IDCHAT, chatGet.id_chat)
                                intent.putExtras(bundle)
                                startActivity(intent)
                            }else{
                                val chat = Chat(idUser, listUserFollowing[position].id_user)
                                AddData().newChat(chat){
                                    if (it!=null){
                                        val intent = Intent(this@Activity_chat, Activity_messenger::class.java)
                                        val bundle = Bundle()
                                        bundle.putString(IDCHAT, it)
                                        intent.putExtras(bundle)
                                        startActivity(intent)
                                    }
                                }
                            }
                        }

                    }

                })
                rv_messenger.adapter = adapterChat
                rv_user.adapter = adaptet_userFollowing

                for (item in user.user.follow_users){
                    GetData().getUserByID(item){
                        if (it!=null){
                            listUserFollowing.add(it)
                            adaptet_userFollowing.notifyDataSetChanged()
                        }
                    }
                }
            }
        }
        ib_exit.setOnClickListener {
            finish()
        }
        view_search.setOnClickListener {

        }
    }



}