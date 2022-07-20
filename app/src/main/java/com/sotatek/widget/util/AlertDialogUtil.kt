package com.sotatek.widget.util

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import com.sotatek.widget.R
import com.sotatek.widget.databinding.DialogAlertMessageBinding

/**
 * Created by khiemnt on 05/12/2021
 */

object AlertDialogUtil {
    fun showMessage(context: Context,
                    message: String,
                    cancelable: Boolean,
                    onButtonClicked: () -> Unit) {
        val inflater = LayoutInflater.from(context)
        val binding = DialogAlertMessageBinding.inflate(inflater)
        val dialog = createAlertDialog(context, cancelable, binding.root)

        binding.txtMessage.text = message
        binding.btnOK.setOnClickListener {
            dialog.dismiss()
            onButtonClicked()
        }

        dialog.show()
    }

    // ---------------------------------------------------------------------------------------------
    fun createAlertDialog(context: Context,
                          cancelable: Boolean,
                          view: View,
                          isUseDefaultBackground: Boolean = true,
                          isTransparentBackground: Boolean = false): AlertDialog {
        val dialog = AlertDialog.Builder(context)
                .setCancelable(cancelable)
                .setView(view)
                .create()

        dialog.run {
            // Config background
            window?.decorView?.setBackgroundResource(android.R.color.transparent)
            if (isUseDefaultBackground) {
                window?.setBackgroundDrawableResource(R.drawable.bg_rounded_16dp)
            }
            if (isTransparentBackground) {
                window?.setDimAmount(0F)
            }

            // Config size
            val width = 320
            val height = ViewGroup.LayoutParams.WRAP_CONTENT
            window?.setLayout(width, height)

            // Config window animations
            window?.setWindowAnimations(R.style.DialogAnimationNormal)
        }

        return dialog
    }
}