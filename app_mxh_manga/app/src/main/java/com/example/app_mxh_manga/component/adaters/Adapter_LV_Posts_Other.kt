package com.example.app_mxh_manga.component.adaters

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.app_mxh_manga.R
import com.example.app_mxh_manga.component.*
import com.example.app_mxh_manga.homePage.component.common.Activity_ShowImagePost
import com.example.app_mxh_manga.homePage.component.common.IDPOST
import com.example.app_mxh_manga.module.*
import com.example.app_mxh_manga.module.system.Image_String
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.squareup.picasso.Picasso
import java.util.*
import kotlin.collections.ArrayList

class Adapter_LV_Posts_Other(val activity: AppCompatActivity, val list: ArrayList<Post_Get>): ArrayAdapter<Post_Get>(activity, R.layout.item_post) {
    var listFilter: ArrayList<Post_Get> = ArrayList()
    init {
        listFilter.addAll(list)
    }
    fun update(list: ArrayList<Post_Get>){
        listFilter.addAll(list)
        notifyDataSetChanged()
    }
    override fun getCount(): Int {
        return listFilter.size
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = activity.layoutInflater.inflate(R.layout.item_post, parent, false)
        val imgAvt = view.findViewById<ImageView>(R.id.imgView_avt)
        val tv_name = view.findViewById<TextView>(R.id.tv_name)
        val tv_content = view.findViewById<TextView>(R.id.tv_content)
        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)
        val tv_date = view.findViewById<TextView>(R.id.tv_date)
        val imgFavorite = view.findViewById<ImageView>(R.id.imgView_favourite)
        val imgComment = view.findViewById<ImageView>(R.id.imgView_comment)
        val tv_favorite = view.findViewById<TextView>(R.id.tv_favourite)
        val tv_cmt = view.findViewById<TextView>(R.id.tv_number_comment)
        val iv_close = view.findViewById<ImageView>(R.id.image_close)
        val iv_setting = view.findViewById<ImageView>(R.id.image_setting)

        val mainId_User = ModeDataSaveSharedPreferences(activity).getIdUser()

        iv_close.visibility = View.GONE
        iv_setting.visibility = View.GONE
        val post:Posts = listFilter[position].posts

        if (post.id_user == mainId_User){
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
            if (mainId_User == i){
                isCheckTym = true
                break
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
                    if (item == mainId_User){
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
                UpdateData().newLike_post(list[position].id_post, mainId_User ){
                    post.likes.add(mainId_User)
                    tv_favorite.setText("${NumberData().formatInt(post.likes.size)}")
                }
            }
        }
        imgComment.setOnClickListener {

        }
        tv_favorite.setText("${NumberData().formatInt(post.likes.size)}")
        tv_cmt.setText("${NumberData().formatInt(post.comments.size)}")

        if (post.images.size <=0){
            recyclerView.visibility = View.GONE
        }else{
            recyclerView.visibility = View.VISIBLE
            val adapter_RV_ImagePost = Adapter_RV_ImagePost(post.images, object : View.OnClickListener {
                override fun onClick(v: View?) {
                    val intent = Intent(context, Activity_ShowImagePost::class.java)
                    val bundle = Bundle()
                    bundle.putString(IDPOST, listFilter[position].id_post)
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
                listView.setOnItemClickListener { parent, view, index, id ->
                    when(index){
                        0 -> {

                        }
                        1 -> {
                            DeleteData().delPost(mainId_User){
                                if (it){
                                    bottomSheet.dismiss()
                                    list.removeAt(position)
                                    notifyDataSetChanged()
                                }
                            }
                        }
                    }
                }
            }

            bottomSheet.show()
        }


        iv_close.setOnClickListener {
            listFilter.removeAt(position)
            notifyDataSetChanged()
        }



        return view
    }


    override fun getFilter(): Filter {
        return object : Filter(){
            override fun performFiltering(constraint: CharSequence?): FilterResults? {
                val strSearch = constraint.toString()
                val results = FilterResults()

                if (strSearch.isEmpty()){
                    results.values = list
                }else{
                    val list2 = ArrayList<Post_Get>()
                    for (item in listFilter){
                        if (item.posts.content!!.toLowerCase(Locale.ROOT).contains(strSearch.toLowerCase(Locale.ROOT))){
                            list2.add(item)
                        }
                    }
                    results.values = list2
                }
                return results
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                listFilter.clear()
                if (results != null) {
                    listFilter.addAll(results.values as Collection<Post_Get>)
                }
                notifyDataSetChanged()
            }

        }
    }
}

class Adapter_RV_ImagePost(val list: ArrayList<String>, val onClickListener: OnClickListener) : RecyclerView.Adapter<Adapter_RV_ImagePost.ImageViewHolder>(){

    class ImageViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_image_post, parent, false)
        return ImageViewHolder(view)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        GetData().getImage(list[position]){ uri->
            holder.itemView.apply {
                val imageView = findViewById<ImageView>(R.id.imageView)
                Picasso.with(context).load(uri).into(imageView)
                imageView.setOnLongClickListener {
                    onClickListener.onClick(this)
                    true
                }
            }
        }
    }
    override fun getItemCount(): Int {
        return list.size
    }
}


class Adapter_LV_ShowAll_ImagesPost(val activity: AppCompatActivity, val list: ArrayList<Uri>): ArrayAdapter<Uri> (activity, R.layout.item_listview_image){
    override fun getCount(): Int {
        return list.size
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = activity.layoutInflater.inflate(R.layout.item_listview_image, parent, false)
        val imageView = view.findViewById<ImageView>(R.id.imgPost)
        imageView.setImageURI(list[position])
        Picasso.with(activity).load(list[position]).into(imageView)

        return view
    }
}








