package com.application.archelon.adapters

import com.application.archelon.models.SimpleCategoryItem
import org.junit.Assert.*
import org.junit.Test

class SectionCardAdapterTests {

    @Test
    fun testCountMatches() {

        val items = ArrayList<SimpleCategoryItem>()
        items.add(SimpleCategoryItem(itemId = "Test 1", title = 1, subtitle = null))
        items.add(SimpleCategoryItem(itemId = "Test 2", title = 2, subtitle = null))
        val adapter = SectionCardAdapter(items)
        assertEquals(adapter.itemCount, items.size)

    }

}