package com.example.firebasechat.ui.main.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.firebasechat.R
import com.example.firebasechat.data.model.users
import com.example.firebasechat.ui.main.adapter.ParticipantAdapter
import com.example.firebasechat.ui.main.adapter.UserAdapter
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_create_group.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.userlist

class CreateGroup : AppCompatActivity() {
    lateinit var selectedUserArraylists:ArrayList<users>
    lateinit var firebaseDatabase: FirebaseDatabase
    var adapter:UserAdapter?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_group)
        init()


    }

    fun init(){
        selectedUserArraylists= ArrayList()
        selectedUserArraylists= intent.getParcelableArrayListExtra("data")!!
        firebaseDatabase= FirebaseDatabase.getInstance()
        participentlistforshare.layoutManager= LinearLayoutManager(this)
        adapter= UserAdapter(this,selectedUserArraylists)
        participentlistforshare.adapter=adapter

        createGroup.setOnClickListener(View.OnClickListener {
            if(groupName.text.toString().isNullOrEmpty()){
                groupName.setError(getString(R.string.name_error))
                return@OnClickListener
            }else{
                addGroupToUser()
            }
        })
    }

    fun addGroupToUser(){
        var reference=firebaseDatabase.getReference("GroupList")
        var key=reference.push().key
        reference.child(FirebaseAuth.getInstance().currentUser!!.uid!!).child(key!!).setValue(groupName.text.toString())

        for(user in selectedUserArraylists){
            reference.child(user.uid!!).child(key!!).setValue(groupName.text.toString())
        }
        Toast.makeText(this,"Group Created Successfully",Toast.LENGTH_LONG).show()
        finish()
    }
}