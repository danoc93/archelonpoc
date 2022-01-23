package com.application.archelon.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * NavigationItem
 * Model to maintain data for a navigation item, these are used in menu views.
 */

@Parcelize
data class NavigationItem(
    val id: NavigationItemType,
    val title: String? = "",
    val subtitle: String? = ""
) : Parcelable