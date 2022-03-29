package com.example.firebasechat.ui.main.activity

import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.firebasechat.R
import com.example.firebasechat.data.model.users
import com.example.firebasechat.ui.main.adapter.UserAdapter
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_main.*
import android.widget.Toast
import com.example.firebasechat.ui.base.BaseActivity
import com.google.firebase.auth.FirebaseAuth

import com.google.firebase.database.DatabaseError

import com.google.firebase.database.DataSnapshot




class MainActivity : BaseActivity() {
    lateinit var firebaseDatabase:FirebaseDatabase
    lateinit var userArraylists:ArrayList<users>
    lateinit var adapter: UserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()

    }
    fun init(){
        userArraylists= ArrayList()
        firebaseDatabase= FirebaseDatabase.getInstance()
        userlist.layoutManager=LinearLayoutManager(this)
        adapter=UserAdapter(this,userArraylists)
        userlist.adapter=adapter
        populateData()
        logout.setOnClickListener(View.OnClickListener {
            logout()
        })
        setting.setOnClickListener(View.OnClickListener {
            startActivity(Intent(this,Setting::class.java))
            finish()
        })

        groups.setOnClickListener(View.OnClickListener {
            startActivity(Intent(this,GroupList::class.java))
        })


    }
    fun populateData(){
        var auth=FirebaseAuth.getInstance()
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


    fun logout() {
        var dialog: AlertDialog.Builder = AlertDialog.Builder(this)
        dialog.setMessage(getString(R.string.Logout_message))
        dialog.setTitle(getString(R.string.logout))
        dialog.setPositiveButton(getString(R.string.logout), DialogInterface.OnClickListener { dialog, which ->
            dialog.cancel()
            FirebaseAuth.getInstance().signOut()
            startActivity(Intent(this,Login::class.java))

        })
        dialog.setNegativeButton(getString(R.string.no), DialogInterface.OnClickListener { dialog, which ->
            dialog.cancel()
        })
        dialog.show()
    }
}