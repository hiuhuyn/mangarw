package com.example.app_mxh_manga.messenger

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.app_mxh_manga.R
import com.example.app_mxh_manga.component.GetData
import com.example.app_mxh_manga.component.NumberData
import com.example.app_mxh_manga.module.Messenger_Get
import com.example.app_mxh_manga.module.User_Get
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.squareup.picasso.Picasso

class Adapter_RV_messenger(var list: ArrayList<Messenger_Get>, var userOther: User_Get): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    class ReceiverViewHolder(view: View): RecyclerView.ViewHolder(view)
    class SenderViewHolder(view: View): RecyclerView.ViewHolder(view)

    fun update(list2: ArrayList<Messenger_Get>){
        list = list2
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == 0){
            return SenderViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_messenger_sender, parent, false))
        }else{
            return ReceiverViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_messenger_receiver, parent, false))
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val messengerGet = list[position]
        holder.itemView.apply {
            val tv_date = findViewById<TextView>(R.id.tv_date)
            val tv_content = findViewById<TextView>(R.id.tv_content)
            val rv_image = findViewById<RecyclerView>(R.id.rv_images)
            val listImage = ArrayList<Uri>()
//            val adapterImage

            tv_date.text = NumberData().formatTime(messengerGet.messenger.date_submit)
            tv_content.text = messengerGet.messenger.content
            if (list[position].messenger.images.isEmpty()){
                rv_image.visibility = View.GONE
            }else{
                for (i in messengerGet.messenger.images){
                    GetData().getImage(i){
                        if (it!=null){
                            listImage.add(it)

                        }
                    }
                }
                rv_image.visibility = View.VISIBLE
            }
            var checkClickContent = true
            tv_content.setOnClickListener {
                if (checkClickContent){
                    checkClickContent = false
                    tv_date.visibility = View.VISIBLE

                }else{
                    checkClickContent = true
                    tv_date.visibility = View.GONE
                }
            }

            if (holder is ReceiverViewHolder){
                val iv_avt = findViewById<ImageView>(R.id.iv_avt)
                val cardView = findViewById<CardView>(R.id.cardView)
                if (position>0 && list[position-1].messenger.receiver  ==  list[position].messenger.receiver){
                    iv_avt.visibility = View.GONE
                    cardView.visibility = View.GONE
                }else{
                    GetData().getImage(userOther.user.uri_avt){
                        if (it!=null){
                            Picasso.with(context).load(it).into(iv_avt)
                            iv_avt.visibility = View.VISIBLE
                            cardView.visibility = View.VISIBLE
                        }
                    }
                }
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        if (userOther.id_user != list[position].messenger.sender){
            return 0
        }else{
            return 1
        }
    }
    override fun getItemCount(): Int {
        return list.size
    }


}