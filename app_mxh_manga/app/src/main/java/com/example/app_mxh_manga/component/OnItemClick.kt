package com.example.app_mxh_manga.component

interface OnItemClick {
    fun onItemClick(position: Int)
}

interface OnItemClick_2 {
    fun onItemClick2(position: Int, check: Boolean)
}
interface OnItemClickComment {
    fun onClickReply(position: Int, id_user: String)
}

