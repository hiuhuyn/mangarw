package com.example.app_mxh_manga.component.adaters

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.app_mxh_manga.IDUSER
import com.example.app_mxh_manga.R
import com.example.app_mxh_manga.component.GetData
import com.example.app_mxh_manga.component.NumberData
import com.example.app_mxh_manga.component.OnItemClickComment
import com.example.app_mxh_manga.component.TAGGET
import com.example.app_mxh_manga.homePage.component.profile.component.Activity_profile
import com.example.app_mxh_manga.module.Comment_Post_Get
import com.squareup.picasso.Picasso


class Adapter_RV_Comment_Post(val context: Context, val list: ArrayList<Comment_Post_Get>, val onItemClickComment: OnItemClickComment): RecyclerView.Adapter<Adapter_RV_Comment_Post.ItemViewHolder>() {
    class ItemViewHolder(view: View): RecyclerView.ViewHolder(view)



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_comment, parent, false))
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {

        holder.itemView.apply {
            val iv_avt = findViewById<ImageView>(R.id.iv_avt)
            val tv_name = findViewById<TextView>(R.id.tv_name)
            val tv_content = findViewById<TextView>(R.id.tv_content)
            val tv_date = findViewById<TextView>(R.id.tv_date)
            val tv_countLike = findViewById<TextView>(R.id.tv_count_like)
            val btn_like = findViewById<Button>(R.id.btn_like)
            val btn_reply = findViewById<Button>(R.id.btn_reply)
            val btn_showCmt = findViewById<Button>(R.id.btn_showCmt)
            val listView_reply = findViewById<ListView>(R.id.listView_reply)
            listView_reply.visibility = View.GONE
            btn_showCmt.visibility = View.GONE
            tv_countLike.visibility = View.GONE

            var countRepLy = 0
            GetData().getUserByID(list[position].comment_post.id_user){ userGet ->
                if (userGet!=null){
                    GetData().getImage(userGet.user.uri_avt){
                        if (it!=null){
                            Picasso.with(context).load(it).into(iv_avt)
                        }
                    }
                    tv_name.text = userGet.user.name
                    btn_reply.setOnClickListener {
                        onItemClickComment.onClickReply(position, userGet.id_user)
                    }
                    iv_avt.setOnClickListener {
                        val i = Intent(this.context, Activity_profile::class.java)
                        val bundle = Bundle()
                        bundle.putString(IDUSER, userGet.id_user)
                        i.putExtras(bundle)
                        context.startActivity(i)
                    }
                    tv_name.setOnClickListener {
                        val i = Intent(this.context, Activity_profile::class.java)
                        val bundle = Bundle()
                        bundle.putString(IDUSER, userGet.id_user)
                        i.putExtras(bundle)
                        context.startActivity(i)
                    }
                }
            }
            tv_content.text = list[position].comment_post.content
            tv_date.text = NumberData().formatTime(list[position].comment_post.date_submit)

            btn_showCmt.setOnClickListener {
                listView_reply.visibility = View.VISIBLE
            }
            var checkLike = false

            btn_like.setOnClickListener {

                tv_countLike.visibility = View.VISIBLE
                tv_countLike.text = "${NumberData().formatInt(countRepLy+1)}"
            }

        }
    }

    override fun getItemCount(): Int {
        return list.size
    }
}


