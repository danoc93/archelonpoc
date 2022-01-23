package com.application.archelon.utils

import android.content.Context
import android.widget.Toast

object ActivityUtil {
    fun showNotImplementedToast(context: Context, title: Int) {
        showNotImplementedToast(context, context.getString(title))
    }

    fun showNotImplementedToast(context: Context, title: String?) {
        Toast.makeText(
            context,
            "$title has not been implemented yet.",
            Toast.LENGTH_SHORT
        ).show();
    }
}