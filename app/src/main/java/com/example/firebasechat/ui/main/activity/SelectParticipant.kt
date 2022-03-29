package com.example.firebasechat.ui.main.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.firebasechat.R
import com.example.firebasechat.data.model.users
import com.example.firebasechat.ui.base.BaseActivity
import com.example.firebasechat.ui.main.adapter.ParticipantAdapter
import com.example.firebasechat.ui.main.adapter.UserAdapter
import com.example.firebasechat.utils.onAdapterClick
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.userlist
import kotlinx.android.synthetic.main.activity_select_participant.*

class SelectParticipant : BaseActivity(), onAdapterClick {
    lateinit var firebaseDatabase: FirebaseDatabase
    lateinit var userArraylists:ArrayList<users>
    lateinit var selectedUserArraylists:ArrayList<users>
    lateinit var adapter: ParticipantAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_participant)
        init()
    }

    fun init(){
        userArraylists= ArrayList()
        selectedUserArraylists= ArrayList()
        firebaseDatabase= FirebaseDatabase.getInstance()
        userlist.layoutManager= LinearLayoutManager(this)
        adapter=ParticipantAdapter(this,userArraylists,this)
        userlist.adapter=adapter
        populateData()

        proceed.setOnClickListener(View.OnClickListener {
            var intent=Intent(this,CreateGroup::class.java)
            intent.putParcelableArrayListExtra("data",selectedUserArraylists)
            startActivity(intent)
            finish()
        })



    }
    fun populateData(){
        var auth= FirebaseAuth.getInstance()
        progress!!.showSweetDialog()
        var reference=firebaseDatabase.getReference().child("user")
        reference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for(snap in dataSnapshot.children){
                    var user=snap.getValue(users::class.java)
                    if(!auth.currentUser!!.uid.equals(user!!.uid))
                        userArraylists.add(user!!)
                }
                adapter.notifyDataSetChanged()
                progress!!.dismissSweet()
            }

            override fun onCancelled(databaseError: DatabaseError) {
                progress!!.dismissSweet()
            }
        })
    }

    override fun onclick(position: Int, type: Boolean) {
       if(type){
           selectedUserArraylists.add(userArraylists.get(position))

       }else{
           selectedUserArraylists.remove(userArraylists.get(position))
       }
    }
}