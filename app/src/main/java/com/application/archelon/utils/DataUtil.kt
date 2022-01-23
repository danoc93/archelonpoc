package com.application.archelon.utils

object DataUtil {
    val LABEL_ROOT = "AchelonPhotoCapture"

    fun getPhotoIdentifier(): String {
        val currentTime = System.currentTimeMillis()
        return "${LABEL_ROOT}-${currentTime}"
    }
}