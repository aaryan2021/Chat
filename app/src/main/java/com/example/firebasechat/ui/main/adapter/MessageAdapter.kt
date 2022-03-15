package com.example.firebasechat.ui.main.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.firebasechat.R
import com.example.firebasechat.data.model.Message
import com.example.firebasechat.ui.main.activity.Chat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.core.Context
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView

class MessageAdapter(var context:android.content.Context,var messageList:ArrayList<Message>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var itemSend=1
    var itemReceived=2

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if(viewType==itemSend){
            var item=LayoutInflater.from(parent.context).inflate(R.layout.senderlayout,parent,false)
            return SenderViewHolder(item)
        }else{
            var item=LayoutInflater.from(parent.context).inflate(R.layout.receiver_layout,parent,false)
            return ReceiverViewHolder(item)

        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        var message=messageList.get(position)
        if(holder.itemViewType==itemSend){
            var viewHolder:SenderViewHolder= holder as SenderViewHolder
            viewHolder.message!!.text=message!!.message
            Picasso.get().load(Chat.senderImage).into(holder.profileimage);

        }else{
            var viewHolder:ReceiverViewHolder= holder as ReceiverViewHolder
            viewHolder.message!!.text=message!!.message
            Picasso.get().load(Chat.receiverImage).into(holder.profileimage);
        }

    }

    override fun getItemCount(): Int {
      return messageList.size
    }

    override fun getItemViewType(position: Int): Int {
        var message=messageList.get(position)
        if(FirebaseAuth.getInstance().currentUser!!.uid.equals(message.senderId)){
            return itemSend
        }else{
            return itemReceived
        }

    }

    class SenderViewHolder(item: View):RecyclerView.ViewHolder(item){
        var profileimage:CircleImageView?=null
        var message:TextView?=null
        init {
            profileimage=item.findViewById(R.id.userimage)
            message=item.findViewById(R.id.message)
        }
    }
    class ReceiverViewHolder(item: View):RecyclerView.ViewHolder(item){
        var profileimage:CircleImageView?=null
        var message:TextView?=null
        init {
            profileimage=item.findViewById(R.id.userimage)
            message=item.findViewById(R.id.message)
        }
    }


}