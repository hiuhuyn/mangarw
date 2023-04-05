package com.example.app_mxh_manga.homePage.component.common

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.app_mxh_manga.IDUSER
import com.example.app_mxh_manga.R
import com.example.app_mxh_manga.component.*
import com.example.app_mxh_manga.component.adaters.Adapter_RV_iv_string
import com.example.app_mxh_manga.component.adaters.Adapter_RV_Comment_Post
import com.example.app_mxh_manga.component.adaters.Adapter_RV_ImagePost
import com.example.app_mxh_manga.homePage.component.profile.component.Activity_profile
import com.example.app_mxh_manga.module.Comment_Post_Get
import com.example.app_mxh_manga.module.Comment_post
import com.example.app_mxh_manga.module.Post_Get
import com.example.app_mxh_manga.module.system.Image_String
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.squareup.picasso.Picasso
import java.util.*
import kotlin.collections.ArrayList

const val IDPOST = "id_post"
class Adapter_RV_Post(val list: ArrayList<Post_Get>,  val id_user: String) : RecyclerView.Adapter<Adapter_RV_Post.PosterViewHolder>(){
    class PosterViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PosterViewHolder {
        return PosterViewHolder(
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
            val iv_setting = findViewById<ImageView>(R.id.image_setting)
            iv_setting.visibility = View.GONE

            val post = list[position].posts
            val index = position

            if (post.id_user == id_user){
                iv_setting.visibility = View.VISIBLE
            }else{
                iv_setting.visibility = View.GONE
            }
            GetData().getUserByID(post.id_user){ userGet ->
                if (userGet != null) {
                    tv_name.setText(userGet.user.name)
                    GetData().getImage(userGet.user.uri_avt){
                        if (it!=null){
                            Picasso.with(context).load(it).into(imgAvt)
                        }
                    }
                }
            }


            tv_content.text = post.content
            tv_date.text = NumberData().formatTime(post.date_submit)
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
                    tv_favorite.setText("${NumberData().formatInt(post.likes.size+1)}")
                    UpdateData().newLike_post(list[position].id_post, id_user ){
                        if (it){
                            post.likes.add(id_user)
                        }
                        tv_favorite.setText("${NumberData().formatInt(post.likes.size)}")
                    }
                }
            }
            var listCmt = ArrayList<Comment_Post_Get>()
            val adapter_cmt = Adapter_RV_Comment_Post(this.context, listCmt, object : OnItemClickComment{
                override fun onClickReply(position: Int, id_user: String) {

                }

            })

            GetData().getCmtPostByIDPost(list[position].id_post){ listRefCmt ->
                Log.d(TAGGET, "getCmtPostByIDPost ...: $listRefCmt")
                if (listRefCmt !=null){
                    listCmt.addAll(listRefCmt)
                    adapter_cmt.notifyDataSetChanged()
                    tv_cmt.text = NumberData().formatInt(listCmt.size)
                }
            }

            imgComment.setOnClickListener {
                val bottomSheetDialog = BottomSheetDialog(this.context)
                bottomSheetDialog.setContentView(R.layout.layout_bottom_sheet_comment)
                val rv_comment = bottomSheetDialog.findViewById<RecyclerView>(R.id.rv_comment)
                val edt_cmt = bottomSheetDialog.findViewById<EditText>(R.id.edt_cmt)
                val ib_send = bottomSheetDialog.findViewById<ImageButton>(R.id.ib_send)

                rv_comment!!.adapter = adapter_cmt


                ib_send!!.setOnClickListener {
                    if (edt_cmt!!.text.toString().isEmpty()){
                        Notification(this.context).toastCustom("Chưa có nội dung comment").show()
                    }else{
                        val content = edt_cmt.text.toString().trim()
                        val date_submit: Date = Calendar.getInstance().time
                        val id_post = list[position].id_post
                        val parent_comment_id  = ""
                        val commentPost = Comment_post(content, date_submit, id_user, id_post, parent_comment_id)
                        edt_cmt.setText("")
                        AddData().newCmtPost(commentPost){
                            Log.d("NewData", "new comment: $it")
                            if (it!=null){
                                listCmt.add(Comment_Post_Get(it, commentPost))
                                tv_cmt.text = NumberData().formatInt(listCmt.size)
                                adapter_cmt.notifyDataSetChanged()
                            }
                        }
                    }
                }
                bottomSheetDialog.show()
            }
            imgAvt.setOnClickListener {
                val intent = Intent(this.context, Activity_profile::class.java)
                val bundle = Bundle()
                bundle.putString(IDUSER, post.id_user)
                intent.putExtras(bundle)
                context.startActivity(intent)
            }
            tv_name.setOnClickListener {
                val intent = Intent(this.context, Activity_profile::class.java)
                val bundle = Bundle()
                bundle.putString(IDUSER, post.id_user)
                intent.putExtras(bundle)
                context.startActivity(intent)
            }

            tv_favorite.setText("${NumberData().formatInt(post.likes.size)}")
//            tv_cmt.setText("${NumberData().formatInt(post.comments.size)}")

            iv_setting.setOnClickListener {
                val popup = PopupMenu(context, this)
                popup.inflate(R.menu.menu_setting_post)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    popup.gravity = Gravity.TOP or Gravity.END
                }
                popup.setOnMenuItemClickListener { menuItem ->
                    when(menuItem.itemId){
                        R.id.item_edit_post -> {

                        }
                        R.id.item_delete_post -> {
                            DeleteData().delPost(list[position].id_post){
                                if (it){
                                    popup.dismiss()
                                    list.removeAt(position)
                                    notifyDataSetChanged()
                                }
                            }
                        }
                    }
                    false
                }
                popup.show()
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
