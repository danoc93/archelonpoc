package com.application.archelon.repositories

import com.application.archelon.models.SimpleItem

/**
 * SectorRepository
 * This assumes they are all independent, this can alternatively be modeled per beach.
 */

object SectorRepository {
    fun getAll(): List<SimpleItem> {
        return listOf(
            SimpleItem(id = 1, name = "East"),
            SimpleItem(id = 2, name = "West")
        )
    }
}