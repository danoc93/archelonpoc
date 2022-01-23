package com.application.archelon.interfaces

import android.view.View
import com.application.archelon.models.NavigationItem

/**
 * OnNavigationItemClickListener
 * General interface to implement custom event on navigation items (used in Menu Views).
 */

interface OnNavigationItemClickListener {
    fun onNavigationItemClick(view: View, item: NavigationItem)
}