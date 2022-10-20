package org.sopt.sample.util

import android.content.Context
import android.view.View
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar

fun View.showSnackBar(snackBarText: String) {
    Snackbar.make(this, snackBarText, Snackbar.LENGTH_SHORT).show()
}

fun Context.showToast(toastText: String) {
    Toast.makeText(this, toastText, Toast.LENGTH_SHORT).show()
}
