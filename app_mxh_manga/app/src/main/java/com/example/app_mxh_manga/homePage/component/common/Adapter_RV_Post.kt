package com.example.app_mxh_manga.homePage.component.common

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.app_mxh_manga.R
import com.example.app_mxh_manga.component.*
import com.example.app_mxh_manga.component.adaters.Adapter_LV_iv_string
import com.example.app_mxh_manga.component.adaters.Adapter_RV_ImagePost
import com.example.app_mxh_manga.module.Post_Get
import com.example.app_mxh_manga.module.system.Image_String
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.squareup.picasso.Picasso

class Adapter_RV_Post(val list: ArrayList<Post_Get>, val id_user: String, val onItemClick: OnItemClick) : RecyclerView.Adapter<Adapter_RV_Post.PosterViewHolder>(){
    class PosterViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PosterViewHolder {
        return Adapter_RV_Post.PosterViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_post, parent, false)
        )
    }


    override fun onBindViewHolder(holder: PosterViewHolder, position: Int) {
        holder.itemView.apply {
            val imgAvt = findViewById<ImageView>(R.id.imgView_avt)
            val tv_name = findViewById<TextView>(R.id.tv_name)
            val tv_content = findViewById<TextView>(R.id.tv_content)
            val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
            val tv_date = findViewById<TextView>(R.id.tv_date)
            val imgFavorite = findViewById<ImageView>(R.id.imgView_favourite)
            val imgComment = findViewById<ImageView>(R.id.imgView_comment)
            val tv_favorite = findViewById<TextView>(R.id.tv_favourite)
            val tv_cmt = findViewById<TextView>(R.id.tv_number_comment)
            val iv_close = findViewById<ImageView>(R.id.image_close)
            val iv_setting = findViewById<ImageView>(R.id.image_setting)
            iv_close.visibility = View.GONE
            iv_setting.visibility = View.GONE

            val post = list[position].posts
            val index = position

            if (post.id_user == id_user){
                iv_setting.visibility = View.VISIBLE
                iv_close.visibility = View.GONE
            }else{
                iv_setting.visibility = View.GONE
                iv_close.visibility = View.VISIBLE
            }
            GetData().getUserByID(post.id_user){ userGet ->
                if (userGet != null) {
                    tv_name.setText(userGet.user.name)
                    GetData().getImage(userGet.user.uri_avt){
                        Picasso.with(context).load(it).into(imgAvt)
                        Log.d("GetData", "Image avt: ${post.id_user} ")
                    }
                }
            }


            tv_content.setText(post.content)
            tv_date.setText(NumberData().formatTime(post.date_submit))
            var isCheckTym = false

            for (i in post.likes){
                if (id_user == i){
                    isCheckTym = true
                }
            }

            if (isCheckTym){
                imgFavorite.setImageResource(R.drawable.ic_baseline_favorite_40)
            }else{
                imgFavorite.setImageResource(R.drawable.ic_baseline_favorite_border_40)
            }
            imgFavorite.setOnClickListener {
                if (isCheckTym){
                    isCheckTym = false
                    imgFavorite.setImageResource(R.drawable.ic_baseline_favorite_border_40)
                    for (item in post.likes){
                        if (item == id_user){
                            UpdateData().removeLike_post(list[position].id_post, item){
                                post.likes.remove(item)
                                tv_favorite.setText("${NumberData().formatInt(post.likes.size)}")
                            }
                            break
                        }
                    }


                }else{
                    isCheckTym = true
                    imgFavorite.setImageResource(R.drawable.ic_baseline_favorite_40)
                    UpdateData().newLike_post(list[position].id_post, id_user ){
                        post.likes.add(id_user)
                        tv_favorite.setText("${NumberData().formatInt(post.likes.size)}")
                    }
                }
            }
            imgComment.setOnClickListener {

            }

            tv_favorite.setText("${NumberData().formatInt(post.likes.size)}")
            tv_cmt.setText("${NumberData().formatInt(post.comments.size)}")
            iv_close.setOnClickListener {
                list.removeAt(position)
                notifyDataSetChanged()
            }
            iv_setting.setOnClickListener {
                val listIv_Str = listOf(
                    Image_String(Int_Uri().convertUri(R.drawable.ic_baseline_account_box_40), "Chỉnh sửa bài viết"),
                    Image_String(Int_Uri().convertUri(R.drawable.ic_baseline_photo_library_40), "Xóa bài viết")
                )
                context.setTheme(R.style.Theme_transparent)
                val bottomSheet = BottomSheetDialog(context)
                bottomSheet.setContentView(R.layout.layout_bottom_sheeet_listview)
                val listView = bottomSheet.findViewById<ListView>(R.id.listView)
                if (listView != null) {
                    listView.adapter = Adapter_LV_iv_string(bottomSheet.context,  listIv_Str )
                    listView.setOnItemClickListener { parent, view, i, id ->
                        when(i){
                            0 -> {

                            }
                            1 -> {
                                DeleteData().delPost(list[index].id_post){

                                }
                                bottomSheet.dismiss()
                                list.removeAt(index)
                                notifyDataSetChanged()
                            }
                        }
                    }
                }

                bottomSheet.show()
            }
            if (post.images.size <=0){
                recyclerView.visibility = View.GONE
            }else{
                recyclerView.visibility = View.VISIBLE
                val adapter_RV_ImagePost = Adapter_RV_ImagePost(post.images, object : View.OnClickListener {
                    override fun onClick(v: View?) {
                        val intent = Intent(context, Activity_ShowImagePost::class.java)
                        val bundle = Bundle()
                        bundle.putString(IDPOST, list[index].id_post)
                        intent.putExtras(bundle)
                        context.startActivity(intent)
                    }

                })
                recyclerView.adapter = adapter_RV_ImagePost
                recyclerView.layoutManager = LinearLayoutManager(
                    context,
                    LinearLayoutManager.HORIZONTAL,
                    false
                )
            }
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }





}
