package com.example.app_mxh_manga.messenger

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.app_mxh_manga.R
import com.example.app_mxh_manga.component.GetData
import com.example.app_mxh_manga.component.NumberData
import com.example.app_mxh_manga.module.Chat_Get
import com.example.app_mxh_manga.module.User_Get
import com.squareup.picasso.Picasso

class Adapter_RV_Chat(var list: ArrayList<Chat_Get>, val id_Me: String): RecyclerView.Adapter<Adapter_RV_Chat.ItemViewHolder>() {
    class ItemViewHolder(view: View): RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_chat, parent,false))
    }
    fun update(list2: ArrayList<Chat_Get>){
        list = list2
        notifyDataSetChanged()

    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {

        holder.itemView.apply {
            val iv_avt = findViewById<ImageView>(R.id.iv_avt)
            val tv_name = findViewById<TextView>(R.id.tv_name)
            val tv_last_messenger = findViewById<TextView>(R.id.tv_last_messenger)
            val tv_date = findViewById<TextView>(R.id.tv_date)
            var id_user = ""
            if (list[position].chat.id_user1 == id_Me){
                id_user = list[position].chat.id_user2
            }else{
                id_user = list[position].chat.id_user1
            }
            GetData().getUserByID(id_user){ userGet ->
                if (userGet!=null){
                    GetData().getImage(userGet.user.uri_avt){
                        if (it!=null){
                            Picasso.with(context).load(it).into(iv_avt)
                        }else{
                            iv_avt.setImageResource(R.drawable.ic_launcher_foreground)
                        }
                    }
                    tv_name.text = userGet.user.name
                }
            }
            tv_last_messenger.text = list[position].chat.last_messenger
            if (list[position].chat.last_messenger_time!=null){
                tv_date.text = "${list[position].chat.last_messenger_time?.let {
                    NumberData().formatTime(
                        it
                    )
                }}"
            }


            setOnClickListener {
                val i = Intent(this.context, Activity_messenger::class.java)
                val bundle = Bundle()
                bundle.putString(IDCHAT, list[position].id_chat)
                i.putExtras(bundle)
                context.startActivity(i)

            }

        }
    }

    override fun getItemCount(): Int {
        return list.size
    }


}