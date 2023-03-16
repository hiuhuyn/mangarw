package com.example.app_mxh_manga.homePage.component.genre.component

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.app_mxh_manga.R
import com.example.app_mxh_manga.module.Genre_Get


class Adapter_Spinner_TextView(context: Context, val list: ArrayList<String>): ArrayAdapter<String>(context, R.layout.item_textview_2) {
    override fun getCount(): Int {
        return list.size
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_textview, parent, false)
        val tv = view.findViewById<TextView>(R.id.tv)
        tv.text = list[position]
        return view
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_textview_2, parent, false)
        val tv = view.findViewById<TextView>(R.id.tv)
        tv.text = list[position]
        return view
    }
}