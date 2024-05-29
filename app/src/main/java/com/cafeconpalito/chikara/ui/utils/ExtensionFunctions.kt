package com.cafeconpalito.chikara.ui.utils

import android.util.Log
import android.view.View
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

fun View.isKeyboardVisible(): Boolean {
    Log.d("keyboard", "fun isKeyboardVisible start")
    val insets = ViewCompat.getRootWindowInsets(this)
    return insets?.isVisible(WindowInsetsCompat.Type.ime()) ?: false
}