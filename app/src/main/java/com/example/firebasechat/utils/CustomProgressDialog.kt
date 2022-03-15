package com.example.firebasechat.utils

import android.content.Context
import androidx.core.content.ContextCompat
import cn.pedant.SweetAlert.SweetAlertDialog
import com.example.firebasechat.R

class CustomProgressDialog(private val context: Context) {

    private var sweetDialog: SweetAlertDialog? = null

    private fun createDialog() {
        sweetDialog = SweetAlertDialog(context, SweetAlertDialog.PROGRESS_TYPE)
    }


    private fun showSweet() {
        sweetDialog?.progressHelper?.barColor =
            ContextCompat.getColor(context, R.color.buttonColorBlue)
        sweetDialog?.titleText = context.getString(R.string.please_wait)
        sweetDialog?.show()
    }


    fun showSweetDialog() {
        createDialog()
        sweetDialog = SweetAlertDialog(context, SweetAlertDialog.PROGRESS_TYPE)
        sweetDialog?.setCancelable(false)
        showSweet()
    }

    fun showSweetDialogCancelable() {
        createDialog()
        sweetDialog?.setCancelable(true)
        showSweet()
    }

    fun dismissSweet() {
        sweetDialog?.dismiss()
    }

    fun updateToSuccess(title: String, message: String?) {
        sweetDialog?.titleText = title
        sweetDialog?.contentText = message
        sweetDialog?.changeAlertType(SweetAlertDialog.SUCCESS_TYPE)
    }


}