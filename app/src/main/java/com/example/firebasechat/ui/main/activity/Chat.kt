package com.example.firebasechat.ui.main.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.firebasechat.R
import com.example.firebasechat.data.model.Message
import com.example.firebasechat.ui.main.adapter.MessageAdapter
import com.example.firebasechat.utils.Keys
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_chat.*
import java.util.*
import kotlin.collections.ArrayList

class Chat : AppCompatActivity() {
    var receiverName:String?=null
    var receiverEmail:String?=null
    var receiverStatus:String?=null
    var receiverUid:String?=null
    var senderId:String?=null



    lateinit var firebaseAuth:FirebaseAuth
    lateinit var firebaseDatabase: FirebaseDatabase

    var messageArrayList:ArrayList<Message>?=null
    var messageAdapter:MessageAdapter?=null

    companion object{
        var senderImage:String?=null
        var receiverImage:String?=null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)
        getBundleData()
    }
    fun getBundleData(){
        firebaseAuth= FirebaseAuth.getInstance()
        messageArrayList= ArrayList()
        firebaseDatabase= FirebaseDatabase.getInstance()
        receiverName=intent.getStringExtra(Keys.Name)
        receiverEmail=intent.getStringExtra(Keys.Email)
        receiverImage=intent.getStringExtra(Keys.ImageUri)
        receiverStatus=intent.getStringExtra(Keys.Status)
        receiverUid=intent.getStringExtra(Keys.Uid)
        senderId=firebaseAuth.uid
        setData()
    }

    fun setData(){
        Picasso.get().load(receiverImage).into(profile_image);
        name.text=receiverName

        var layoutManager=LinearLayoutManager(this)
        layoutManager.stackFromEnd
        messagelist.layoutManager=layoutManager
        messageAdapter= MessageAdapter(this,messageArrayList!!)
        messagelist.adapter=messageAdapter
        sendData()
        getData()


    }


    fun getData(){
        var refernce=firebaseDatabase.reference.child("user").child(firebaseAuth.uid!!)
        refernce.addValueEventListener(object:ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                senderImage=snapshot.child("imageUri").getValue().toString()
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })

        var chatReference=firebaseDatabase.reference.child("chats").child(firebaseAuth.uid!!).child("Message")

        chatReference.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                messageArrayList!!.clear()
                for(snapshots in snapshot.children){
                    var message=snapshots.getValue(Message::class.java)
                    messageArrayList!!.add(message!!)
                }
                messageAdapter!!.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {

            }

        } )
    }

    fun sendData(){
        btnsend.setOnClickListener(View.OnClickListener {
            if(edtMessage.text.toString().isNullOrEmpty()){
                edtMessage.setError(getString(R.string.message_error))
            }else{
                var date=Date()
                var message=Message(edtMessage.text.toString(),senderId,date.time)
                firebaseDatabase.reference
                    .child("chats")
                    .child(senderId!!)
                    .child("Message")
                    .push()
                    .setValue(message).addOnCompleteListener(OnCompleteListener {
                        firebaseDatabase.reference
                            .child("chats")
                            .child(receiverUid!!)
                            .child("Message")
                            .push()
                            .setValue(message).addOnCompleteListener(OnCompleteListener {
                                edtMessage.setText("")
                            })
                    })


            }
        })

    }
}