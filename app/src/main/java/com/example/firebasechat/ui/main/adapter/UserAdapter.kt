package com.example.firebasechat.ui.main.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.firebasechat.R
import com.example.firebasechat.data.model.users
import com.example.firebasechat.ui.main.activity.Chat
import com.example.firebasechat.utils.Keys
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView
import org.w3c.dom.Text
import java.security.Key

class UserAdapter(var context: Context,var data:ArrayList<users>): RecyclerView.Adapter<UserAdapter.MyVIewHolder>() {
    class MyVIewHolder(item: View):RecyclerView.ViewHolder(item) {
        var name:TextView?=null
        var userstatus:TextView?=null
        var userimage:CircleImageView?=null
        init {
            name=item.findViewById(R.id.name)
            userstatus=item.findViewById(R.id.user_status)
            userimage=item.findViewById(R.id.userimage)

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserAdapter.MyVIewHolder {
        var item=LayoutInflater.from(parent.context).inflate(R.layout.user_list_item,parent,false)
        return MyVIewHolder(item)
    }

    override fun onBindViewHolder(holder: UserAdapter.MyVIewHolder, position: Int) {
        holder.name!!.text=data.get(position).name
        holder.userstatus!!.text=data.get(position).status

        Picasso.get().load(data.get(position).imageUri).into(holder.userimage);
        holder.itemView.setOnClickListener(View.OnClickListener {
            var intent=Intent(context, Chat::class.java)
            intent.putExtra(Keys.Name,data.get(position).name)
            intent.putExtra(Keys.ImageUri,data.get(position).imageUri)
            intent.putExtra(Keys.Uid,data.get(position).uid)
            intent.putExtra(Keys.Email,data.get(position).email)
            intent.putExtra(Keys.Status,data.get(position).status)
            context.startActivity(intent)
        })

    }

    override fun getItemCount(): Int {
       return data.size
    }
}