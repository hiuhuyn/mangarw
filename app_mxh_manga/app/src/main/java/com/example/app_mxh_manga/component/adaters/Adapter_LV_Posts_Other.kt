package com.example.app_mxh_manga.component.adaters

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.app_mxh_manga.R
import com.example.app_mxh_manga.component.GetData_id
import com.example.app_mxh_manga.component.GetNumberData
import com.example.app_mxh_manga.homePage.component.common.Activity_ShowImagePost
import com.example.app_mxh_manga.module.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class Adapter_LV_Posts_Other(val activity: AppCompatActivity, val list: ArrayList<Posts>): ArrayAdapter<Posts>(activity, R.layout.item_post) {
    val listFilter: ArrayList<Posts> = ArrayList()
    init {
        listFilter.addAll(list)
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


        val listImages = GetData_id().getListImgPost(listFilter[position].id_post)
        val user = GetData_id().getUser(listFilter[position].id_user)
        imgAvt.setImageURI(user.uri_avt)
        tv_name.setText(user.name)
        tv_content.setText(listFilter[position].content)
        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        tv_date.setText(dateFormat.format(listFilter[position].data_submit))
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
            val adapter_RV_ImagePost = Adapter_RV_ImagePost(listImages, object : OnClickListener{
                override fun onClick(v: View?) {
                    val intent = Intent(activity, Activity_ShowImagePost::class.java)
                    val bundle = Bundle()
                    bundle.putParcelableArrayList("listImagePost", listImages)
                    intent.putExtras(bundle)
                    activity.startActivity(intent)
                }

            })
            recyclerView.adapter = adapter_RV_ImagePost
            recyclerView.layoutManager = LinearLayoutManager(
                activity,
                LinearLayoutManager.HORIZONTAL,
                false
            )


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
                    val list2 = ArrayList<Posts>()
                    for (item in listFilter){
                        if (item.content.toLowerCase(Locale.ROOT).contains(strSearch.toLowerCase(Locale.ROOT))){
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
                    listFilter.addAll(results.values as Collection<Posts>)
                }

                notifyDataSetChanged()
            }

        }
    }
}

class Adapter_RV_ImagePost(val list: ArrayList<Uri>, val onClickListener: OnClickListener) : RecyclerView.Adapter<Adapter_RV_ImagePost.ImageViewHolder>(){

    class ImageViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_image_post, parent, false)
        return ImageViewHolder(view)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        holder.itemView.apply {
            val imageView = findViewById<ImageView>(R.id.imageView)
            imageView.setImageURI(list[position])
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
        val imageView = view.findViewById<ImageView>(R.id.imgPost)
        imageView.setImageURI(list[position])

        return view
    }
}








