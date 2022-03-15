package com.example.firebasechat.ui.base
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.firebasechat.utils.CustomAlert
import com.example.firebasechat.utils.CustomProgressDialog


open class BaseActivity : AppCompatActivity() {
    var progress: CustomProgressDialog? = null
    var alert: CustomAlert? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        progress = CustomProgressDialog(this)
        alert = CustomAlert(this)
    }

}