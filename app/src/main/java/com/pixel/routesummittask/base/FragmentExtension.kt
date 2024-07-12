package com.pixel.routesummittask.base

import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.pixel.routesummittask.model.ViewMessage

fun Fragment.showDialog(
    message: String,
    posActionName: String? = null,
    posActionCallBack: (() -> Unit)? = null,
    negActionName: String? = null,
    negActionCallBack: (() -> Unit)? = null,
    isCancelable: Boolean = true,
): AlertDialog {
    val alertDialogBuilder = AlertDialog.Builder(requireContext())
    alertDialogBuilder.setMessage(message)

    alertDialogBuilder.setPositiveButton(
        posActionName,
    ) { dialog, _ ->
        dialog?.dismiss()
        posActionCallBack?.invoke()
    }
    alertDialogBuilder.setNegativeButton(
        negActionName,
    ) { dialog, _ ->
        dialog?.dismiss()
        negActionCallBack?.invoke()
    }

    alertDialogBuilder.setCancelable(isCancelable)
    return alertDialogBuilder.show()
}

fun Fragment.showDialog(viewMessage: ViewMessage): AlertDialog =
    showDialog(
        message = viewMessage.message,
        posActionName = viewMessage.posActionName,
        posActionCallBack = viewMessage.posAction,
        negActionName = viewMessage.negActionName,
        negActionCallBack = viewMessage.negAction,
        isCancelable = viewMessage.isDismissible,
    )
