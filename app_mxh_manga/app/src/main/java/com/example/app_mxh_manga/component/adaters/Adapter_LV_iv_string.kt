package com.example.app_mxh_manga.component.adaters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.app_mxh_manga.R
import com.example.app_mxh_manga.component.Int_Uri
import com.example.app_mxh_manga.module.system.Image_String

class Adapter_LV_iv_string( context: Context, val list: List<Image_String>): ArrayAdapter<Image_String>(context, R.layout.item_bs_listview) {
    override fun getCount(): Int {
        return list.size
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = LayoutInflater.from(context).inflate(R.layout.item_bs_listview, parent, false)
        val iv_icon = view.findViewById<ImageView>(R.id.iv_icon)
        val tv_title = view.findViewById<TextView>(R.id.tv_title)

        iv_icon.setImageURI(list[position].image)
        tv_title.setText(list[position].str)

        return view
    }
}