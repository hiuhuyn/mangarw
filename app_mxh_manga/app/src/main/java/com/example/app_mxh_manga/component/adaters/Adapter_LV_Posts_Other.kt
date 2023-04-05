package com.example.app_mxh_manga.component.adaters

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.app_mxh_manga.IDUSER
import com.example.app_mxh_manga.R
import com.example.app_mxh_manga.component.*
import com.example.app_mxh_manga.homePage.component.common.Activity_ShowImagePost
import com.example.app_mxh_manga.homePage.component.common.IDPOST
import com.example.app_mxh_manga.homePage.component.profile.component.Activity_profile
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
                    if (it!=null){
                        Picasso.with(context).load(it).into(imgAvt)
                    }
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
                tv_favorite.setText("${NumberData().formatInt(post.likes.size-1)}")
                for (item in post.likes){
                    if (item == mainId_User){
                        UpdateData().removeLike_post(list[position].id_post, item){
                            if (it){
                                post.likes.remove(item)
                            }
                            tv_favorite.setText("${NumberData().formatInt(post.likes.size)}")
                        }
                        break
                    }
                }


            }else{
                isCheckTym = true
                imgFavorite.setImageResource(R.drawable.ic_baseline_favorite_40)
                tv_favorite.setText("${NumberData().formatInt(post.likes.size+1)}")
                UpdateData().newLike_post(list[position].id_post, mainId_User ){
                    if(it){
                        post.likes.add(mainId_User)
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
                    val commentPost = Comment_post(content, date_submit, mainId_User, id_post, parent_comment_id)
                    edt_cmt.setText("")
                    AddData().newCmtPost(commentPost){
                        Log.d("NewData", "new comment: $it")
                        if (it!=null){
                            listCmt.add(Comment_Post_Get(it, commentPost))
                            adapter_cmt.notifyDataSetChanged()
                        }
                    }
                }
            }
            bottomSheetDialog.show()
        }


        tv_favorite.setText("${NumberData().formatInt(post.likes.size)}")
//        tv_cmt.setText("${NumberData().formatInt(post.comments.size)}")

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
            val popup = PopupMenu(view.context, view)
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

        holder.itemView.apply {
            val imageView = findViewById<ImageView>(R.id.imageView)
            GetData().getImage(list[position]){ uri->
                if (uri!=null){
                    Picasso.with(context).load(uri).into(imageView)
                }
            }
            imageView.setOnLongClickListener {
                onClickListener.onClick(this)
                true
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
        view.visibility = View.GONE
        val imageView = view.findViewById<ImageView>(R.id.imgPost)
        val dialog = Notification(view.context).dialogLoading("Loading...")
        dialog.show()
        Picasso.with(activity).load(list[position]).into(imageView)
        dialog.dismiss()
        view.visibility = View.VISIBLE

        return view
    }
}








