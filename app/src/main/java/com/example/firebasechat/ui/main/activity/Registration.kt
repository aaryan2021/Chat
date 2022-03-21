package com.example.firebasechat.ui.main.activity

import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.BaseAdapter
import android.widget.Toast
import com.example.firebasechat.R
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_registration.*
import androidx.core.app.ActivityCompat.startActivityForResult
import com.example.firebasechat.data.model.users
import com.example.firebasechat.ui.base.BaseActivity
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage


class Registration : BaseActivity() {
    lateinit var auth:FirebaseAuth
    lateinit var firebaseDatabase: FirebaseDatabase
    lateinit var firebaseStorage: FirebaseStorage

    var uri:Uri?=null
    var ImageUri:String?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)
        initFireBase()
        newUser.setOnClickListener(View.OnClickListener {
            startActivity(Intent(this,Login::class.java))
            finish()
        })

        signUp.setOnClickListener(View.OnClickListener {
            progress!!.showSweetDialog()
            doSignUp()
        })

        profile_image.setOnClickListener(View.OnClickListener {
            val intent = Intent()
            intent.type = "image/*"
            intent.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), 1)
        })

    }

    fun initFireBase(){
        auth= FirebaseAuth.getInstance()
        firebaseDatabase= FirebaseDatabase.getInstance()
        firebaseStorage= FirebaseStorage.getInstance()
    }



    fun doSignUp(){
        var status="Hey There, I am using Firebase chat."
        var name=reg_name.text.toString()
        var email=reg_email.text.toString()
        var password=reg_Password.text.toString()
        var confirm_password=reg_confirmPassword.text.toString()

        if(name.isNullOrEmpty()){
            reg_name.setError(getString(R.string.name_error))
            progress!!.dismissSweet()
        }
        else if(email.isNullOrEmpty()){
            reg_email.setError(getString(R.string.name_error))
            progress!!.dismissSweet()
        }
        else if(password.isNullOrEmpty()){
            reg_Password.setError(getString(R.string.name_error))
            progress!!.dismissSweet()
        }
        else if(password.length<6){
            reg_Password.setError(getString(R.string.password_length_error))
            progress!!.dismissSweet()
        }
        else if(confirm_password.isNullOrEmpty()){
            reg_confirmPassword.setError(getString(R.string.name_error))
            progress!!.dismissSweet()
        }
        else if(password!=confirm_password){
            Toast.makeText(this,"Password and confirm password does not match",Toast.LENGTH_LONG).show()
            progress!!.dismissSweet()
        }else{
            auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(
                OnCompleteListener {
                    if(it.isSuccessful){
                        var reference=firebaseDatabase.getReference("user").child(auth.uid!!)
                        var storageReference=firebaseStorage.getReference("uploads").child(auth.uid!!)

                        if(uri!=null){
                            storageReference.putFile(uri!!).addOnCompleteListener(OnCompleteListener {
                                if (it.isSuccessful){
                                    storageReference.downloadUrl.addOnSuccessListener {
                                        ImageUri=it.toString()
                                        var user=users(auth.uid!!,name,email, ImageUri!!,status)
                                        reference.setValue(user).addOnCompleteListener(
                                            OnCompleteListener {
                                                if(it.isSuccessful){
                                                    progress!!.dismissSweet()
                                                    startActivity(Intent(this@Registration,MainActivity::class.java))
                                                    finish()
                                                }else{
                                                    progress!!.dismissSweet()
                                                    Toast.makeText(this@Registration,"Error while creating user",Toast.LENGTH_LONG).show()
                                                }
                                            })
                                    }
                                }

                            })
                        }
                        else{
                            ImageUri="https://firebasestorage.googleapis.com/v0/b/fir-chat-58c37.appspot.com/o/deff.png?alt=media&token=e3b382c0-6680-4f02-8d03-d68b65d475b5"
                            var user=users(auth.uid!!,name,email, ImageUri!!,status)
                            reference.setValue(user).addOnCompleteListener(
                                OnCompleteListener {
                                    if(it.isSuccessful){
                                        progress!!.dismissSweet()
                                        startActivity(Intent(this@Registration,MainActivity::class.java))
                                        finish()
                                    }else{
                                        progress!!.dismissSweet()
                                        Toast.makeText(this@Registration,"Error while creating user",Toast.LENGTH_LONG).show()
                                    }
                                })
                        }
                    }else{
                        progress!!.dismissSweet()
                        Toast.makeText(this@Registration,"Something went wrong",Toast.LENGTH_LONG).show()
                    }
                })
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1) {
            if(data!=null){
                uri=data.data
                profile_image.setImageURI(uri)
            }
        }
    }
}