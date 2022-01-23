package com.application.archelon.models

import android.os.Parcelable
import androidx.annotation.DrawableRes
import kotlinx.android.parcel.Parcelize

/**
 * SimpleCategoryItem
 * Model to maintain the data for the UI category cards, which are used in places like the homepage.
 */

@Parcelize
data class SimpleCategoryItem(
    val itemId: String,
    val title: Int,
    val subtitle: Int?,
    @DrawableRes val backgroundPhotoDrawableRes: Int? = -1
) : Parcelable