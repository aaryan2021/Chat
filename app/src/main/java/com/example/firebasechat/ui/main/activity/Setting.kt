package com.example.firebasechat.ui.main.activity

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.firebasechat.R
import com.example.firebasechat.data.model.users
import com.example.firebasechat.ui.base.BaseActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_registration.*
import kotlinx.android.synthetic.main.activity_setting.*
import kotlinx.android.synthetic.main.reset_password_dialog.*
import org.w3c.dom.Text

class Setting : BaseActivity() {
    lateinit var firebaseDatabase:FirebaseDatabase
    lateinit var firebaseStorage:FirebaseStorage
    lateinit var firebaseAuth: FirebaseAuth
    var username=""
    var email=""
    var imageUrl=""
    var userstatus=""
    var uri: Uri?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)
        init()
    }

    fun init(){
        firebaseAuth= FirebaseAuth.getInstance()
        firebaseDatabase= FirebaseDatabase.getInstance()
        firebaseStorage= FirebaseStorage.getInstance()
        var reference=firebaseDatabase.getReference().child("user").child(firebaseAuth.currentUser!!.uid)
        var storageRefernce=firebaseStorage.getReference().child("uploads").child(firebaseAuth.currentUser!!.uid)
        reference.addValueEventListener(object:ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                 username=snapshot.child("name").getValue().toString()
                 email=snapshot.child("email").getValue().toString()
                 userstatus=snapshot.child("status").getValue().toString()
                 imageUrl=snapshot.child("imageUri").getValue().toString()

                name.setText(username)
                status.setText(userstatus)
                if(imageUrl!=null)
                Picasso.get().load(imageUrl).into(profile_images);

            }

            override fun onCancelled(error: DatabaseError) {
            }

        })

        updatePassword.setOnClickListener(View.OnClickListener {
            showResetDialog()
        })

        submit.setOnClickListener(View.OnClickListener {
            progress!!.showSweetDialog()
            if(uri!=null){
                storageRefernce.putFile(uri!!).addOnCompleteListener(OnCompleteListener {
                storageRefernce.downloadUrl.addOnSuccessListener {
                    var finaluri=it.toString()
                    var user= users(firebaseAuth.uid!!,name.text.toString(),email, finaluri!!,status.text.toString())
                    reference.setValue(user).addOnCompleteListener(
                        OnCompleteListener {
                            if(it.isSuccessful){
                                progress!!.dismissSweet()
                                Toast.makeText(this@Setting,"Data Updated Successfully",Toast.LENGTH_LONG).show()
                                startActivity(Intent(this@Setting,MainActivity::class.java))
                                finish()
                            }else{
                                progress!!.dismissSweet()
                                Toast.makeText(this@Setting,"Error while updating user", Toast.LENGTH_LONG).show()
                            }
                        })
                }
                })
            }else{
                var user= users(firebaseAuth.uid!!,name.text.toString(),email, imageUrl!!,status.text.toString())
                reference.setValue(user).addOnCompleteListener(
                    OnCompleteListener {
                        if(it.isSuccessful){
                            progress!!.dismissSweet()
                            Toast.makeText(this@Setting,"Data Updated Successfully",Toast.LENGTH_LONG).show()
                            startActivity(Intent(this@Setting,MainActivity::class.java))
                            finish()

                        }else{
                            progress!!.dismissSweet()
                            Toast.makeText(this@Setting,"Error while updating user", Toast.LENGTH_LONG).show()
                        }
                    })
            }

        })

        profile_images.setOnClickListener(View.OnClickListener {
            val intent = Intent()
            intent.type = "image/*"
            intent.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), 1)
        })

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1) {
            if(data!=null){
                uri=data.data
                profile_images.setImageURI(uri)
            }
        }
    }

    private fun showResetDialog() {
        val dialog = Dialog(this)
        dialog.setContentView(R.layout.reset_password_dialog)
        dialog.getWindow()?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        var btncancel=dialog.findViewById<TextView>(R.id.cancel)
        var ResetPassword=dialog.findViewById<TextView>(R.id.ResetPassword)
        var email=dialog.findViewById<EditText>(R.id.email)
        btncancel.setOnClickListener {
            dialog.dismiss()
        }

        ResetPassword.setOnClickListener {
            ResetPasswordServiceCall(email.text.toString())
            dialog.dismiss()
        }

        dialog.show()
    }
    fun ResetPasswordServiceCall(email: String) {
       firebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener(OnCompleteListener {
           if(it.isSuccessful){
               Toast.makeText(this@Setting,"Please check your mail",Toast.LENGTH_LONG).show()
           }
       })
    }

}