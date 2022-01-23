package com.application.archelon.repositories

import com.application.archelon.models.SimpleItem

/**
 * BeachRepository
 * Manage all the data for Beaches
 */

object BeachRepository {
    fun getAll(): List<SimpleItem> {
        // At this point this data is not coming from any API.
        return listOf(
            SimpleItem(id = 1, name = "Mavrovouni"),
            SimpleItem(id = 2, name = "Selinitsa"),
            SimpleItem(id = 3, name = "Vathi"),
            SimpleItem(id = 4, name = "Valtaki")
        )
    }
}