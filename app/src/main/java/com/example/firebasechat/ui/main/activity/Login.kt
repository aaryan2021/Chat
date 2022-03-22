package com.example.firebasechat.ui.main.activity

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.firebasechat.R
import com.example.firebasechat.ui.base.BaseActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*

class Login : BaseActivity() {
    lateinit var auth:FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        init()
        newUser.setOnClickListener(View.OnClickListener {
            startActivity(Intent(this,Registration::class.java))
            finish()
        })
        btnsignIn.setOnClickListener(View.OnClickListener {
            progress!!.showSweetDialog()
            doLogIn()
        })

    }

    fun init(){
        auth= FirebaseAuth.getInstance()

    }

    fun doLogIn(){
        if(email.text.isNullOrEmpty()){
            progress!!.dismissSweet()
            email.setError(getString(R.string.name_error))
        }else if(password.text.isNullOrEmpty()){
            progress!!.dismissSweet()
            password.setError(getString(R.string.password_error))
        }else if(password.text.length<6){
            progress!!.dismissSweet()
            password.setError(getString(R.string.password_length_error))
        }else{

            auth.signInWithEmailAndPassword(email.text.toString(),password.text.toString()).addOnCompleteListener(
                OnCompleteListener {
                    if(it.isSuccessful){
                        progress!!.dismissSweet()
                        startActivity(Intent(this@Login,MainActivity::class.java))
                        finish()
                    }else{
                        progress!!.dismissSweet()
                        Toast.makeText(this@Login,"some thing went wrong",Toast.LENGTH_LONG).show()
                    }
                })
        }
    }
}