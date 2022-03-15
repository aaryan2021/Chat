package com.example.firebasechat.utils
import android.app.Activity
import cn.pedant.SweetAlert.SweetAlertDialog
import com.example.firebasechat.R
import com.tapadoo.alerter.Alerter

class CustomAlert(private val activity: Activity) {

    fun showError(message: String) {
        Alerter.create(activity).setTitle(message)
            ?.setBackgroundColorRes(R.color.errorAlert)
            ?.enableSwipeToDismiss()
            ?.hideIcon()
            ?.show()
    }

    fun showInfo(message: String) {
        Alerter.create(activity).setTitle(message)
            ?.setBackgroundColorRes(R.color.infoAlert)
            ?.enableSwipeToDismiss()
            ?.hideIcon()
            ?.show()
    }

    fun showSuccess(message: String) {
        Alerter.create(activity).setTitle(message)
            ?.setBackgroundColorRes(R.color.successAlert)
            ?.enableSwipeToDismiss()
            ?.hideIcon()
            ?.show()
    }

    fun hide() {
        Alerter.hide()
    }


    fun showSuccessDialog(
        title: String?,
        content: String?,
        clickListener: SweetAlertDialog.OnSweetClickListener
    ) {
        SweetAlertDialog(activity, SweetAlertDialog.SUCCESS_TYPE)
            .setTitleText(title)
            .setContentText(content)
            .setConfirmText(activity.getString(R.string.ok))
            .setConfirmClickListener(clickListener)
            .show()
    }

    fun showInfoDialog(
        title: String?,
        content: String?
    ) {
        SweetAlertDialog(activity, SweetAlertDialog.WARNING_TYPE)
            .setTitleText(title)
            .setContentText(content)
            .setConfirmText(activity.getString(R.string.ok))
            .setConfirmClickListener {
                it.dismissWithAnimation()
            }
            .show()
    }

    fun confirmationDialog(
        title: String?,
        content: String?,
        clickListener: SweetAlertDialog.OnSweetClickListener
    ): SweetAlertDialog {
        val sweetAlertDialog = SweetAlertDialog(activity, SweetAlertDialog.WARNING_TYPE)
        sweetAlertDialog.titleText = title
        sweetAlertDialog.contentText = content
        sweetAlertDialog.confirmText = activity.getString(R.string.yes)
        sweetAlertDialog.cancelText = activity.getString(R.string.no)
        sweetAlertDialog.setConfirmClickListener(clickListener)
        sweetAlertDialog.setCancelClickListener {
            it.dismiss()
        }
        sweetAlertDialog.show()
        return sweetAlertDialog
    }
}
