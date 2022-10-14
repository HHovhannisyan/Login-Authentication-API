package com.example.ui

import android.app.Activity
import android.view.View
import com.google.android.material.snackbar.Snackbar
import net.example.data.network.Resource
import net.example.ui.auth.MainActivity


fun View.visible(isVisible: Boolean) {
    visibility = if (isVisible) View.VISIBLE else View.GONE
}

fun View.enable(enabled: Boolean) {
    isEnabled = enabled
    alpha = if (enabled) 1f else 0.5f
}


fun Activity.snackbar(message: String, action: (() -> Unit)? = null) {
    val snackBar = Snackbar.make(window.decorView.rootView, message, Snackbar.LENGTH_LONG)
    action?.let {
        snackBar.setAction("Retry") {
            it()
        }
    }
    snackBar.show()
}



fun Activity.handleApiError(failure: Resource.Failure) {
    when {
        failure.isNetworkError -> Snackbar.make(
            window.decorView.rootView,
            "Please check your internet connection",
            Snackbar.LENGTH_LONG
        ).show()

        failure.errorCode == 500 -> {
            if (this is MainActivity) {
                this.snackbar("You've entered incorrect fields")
            }
        }
        else -> {
            val error = failure.errorBody?.string().toString()
            this.snackbar(error)
            Snackbar.make(window.decorView.rootView, error, Snackbar.LENGTH_LONG).show()
        }
    }
}
