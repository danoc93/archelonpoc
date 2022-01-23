package com.application.archelon.interfaces

import com.application.archelon.models.SimpleCategoryItem

/**
 * OnItemClickListener
 * General interface to implement custom onClick events.
 */

interface OnItemClickListener {
    fun onItemClick(item: SimpleCategoryItem)
}