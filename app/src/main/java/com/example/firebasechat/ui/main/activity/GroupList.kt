package com.example.firebasechat.ui.main.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.firebasechat.R
import com.example.firebasechat.data.model.users
import com.example.firebasechat.ui.base.BaseActivity
import com.example.firebasechat.ui.main.adapter.GroupAdapter
import com.example.firebasechat.ui.main.adapter.UserAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_group_list.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.userlist

class GroupList : BaseActivity() {
    lateinit var firebaseDatabase: FirebaseDatabase
    lateinit var UserGroup:ArrayList<String>
    lateinit var adapter: GroupAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_group_list)
        init()
    }

    fun init(){
        UserGroup= ArrayList()
        firebaseDatabase= FirebaseDatabase.getInstance()
        grouplist.layoutManager= LinearLayoutManager(this)
        adapter= GroupAdapter(this,UserGroup)
        grouplist.adapter=adapter
        populateData()

        addgroup.setOnClickListener(View.OnClickListener {
            startActivity(Intent(this,SelectParticipant::class.java))
        })


    }
    fun populateData(){
        var auth= FirebaseAuth.getInstance()
        progress!!.showSweetDialog()
        var reference=firebaseDatabase.getReference().child("GroupList").child(auth.currentUser!!.uid)
        reference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for(snap in dataSnapshot.children){
                    var user=snap.getValue().toString()
                    UserGroup.add(user!!)
                }
                adapter.notifyDataSetChanged()
                progress!!.dismissSweet()
            }

            override fun onCancelled(databaseError: DatabaseError) {
                progress!!.dismissSweet()
            }
        })
    }
}