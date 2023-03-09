package com.example.app_mxh_manga.homePage.component.common

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.app_mxh_manga.R
import com.example.app_mxh_manga.component.GetData_id
import com.example.app_mxh_manga.component.GetNumberData
import com.example.app_mxh_manga.component.adaters.Adapter_RV_ImagePost
import com.example.app_mxh_manga.module.Posts
import java.text.SimpleDateFormat

class Adapter_RV_Post(val list: ArrayList<Posts>) : RecyclerView.Adapter<Adapter_RV_Post.PosterViewHolder>(){
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


            val listImages = GetData_id().getListImgPost(list[position].id_post)
            val user = GetData_id().getUser(list[position].id_user)
            imgAvt.setImageURI(user.uri_avt)
            tv_name.setText(user.name)
            tv_content.setText(list[position].content)
            val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
            tv_date.setText(dateFormat.format(list[position].data_submit))
            var isCheckTym = false
            imgFavorite.setOnClickListener {
                if (isCheckTym){
                    isCheckTym = false
                    imgFavorite.setImageResource(R.drawable.ic_baseline_favorite_border_40)
                }else{
                    isCheckTym = true
                    imgFavorite.setImageResource(R.drawable.ic_baseline_favorite_40)
                }
            }
            imgComment.setOnClickListener {

            }
            tv_favorite.setText("${GetNumberData().numberLike_post(list[position].id_post)}")
            tv_cmt.setText("${GetNumberData().numberCmt_post(list[position].id_post)}")

            if (listImages.size <=0){
                recyclerView.visibility = View.GONE
            }else{
                recyclerView.visibility = View.VISIBLE
                val adapter_RV_ImagePost = Adapter_RV_ImagePost(listImages, object : View.OnClickListener {
                    override fun onClick(v: View?) {
                        val intent = Intent(context, Activity_ShowImagePost::class.java)
                        val bundle = Bundle()
                        bundle.putParcelableArrayList("listImagePost", listImages)
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

                iv_close.setOnClickListener {
                    list.removeAt(position)
                    notifyDataSetChanged()
                }

            }
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }


}
