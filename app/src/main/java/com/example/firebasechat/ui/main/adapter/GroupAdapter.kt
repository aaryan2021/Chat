package com.example.firebasechat.ui.main.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.firebasechat.R
import com.example.firebasechat.data.model.users
import com.example.firebasechat.ui.main.activity.Chat
import com.example.firebasechat.utils.Keys
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView

class GroupAdapter (var context: Context, var data:ArrayList<String>): RecyclerView.Adapter<GroupAdapter.MyVIewHolder>() {
    class MyVIewHolder(item: View): RecyclerView.ViewHolder(item) {
        var name: TextView?=null
        var userstatus: TextView?=null
        var userimage: CircleImageView?=null
        init {
            name=item.findViewById(R.id.name)
            userstatus=item.findViewById(R.id.user_status)
            userimage=item.findViewById(R.id.userimage)

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyVIewHolder {
        var item= LayoutInflater.from(parent.context).inflate(R.layout.user_list_item,parent,false)
        return MyVIewHolder(item)
    }

    override fun onBindViewHolder(holder: MyVIewHolder, position: Int) {
        holder.name!!.text=data.get(position)
        holder.userstatus!!.text="just for fun."

    }

    override fun getItemCount(): Int {
        return data.size
    }
}