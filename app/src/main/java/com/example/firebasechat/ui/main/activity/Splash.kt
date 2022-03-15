package com.example.firebasechat.ui.main.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.firebasechat.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class Splash : AppCompatActivity() {
    lateinit var auth:FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

    }

    override fun onResume() {
        super.onResume()
        GlobalScope.launch(context = Dispatchers.IO) {
            Thread.sleep(2000)
            openActivity()
        }
    }

    fun openActivity()
    {
        auth = FirebaseAuth.getInstance();
        if(auth.currentUser==null){
            startActivity(Intent(this, Registration::class.java))
        }else{
            startActivity(Intent(this, MainActivity::class.java))
        }
    }
}