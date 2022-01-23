package com.application.archelon.adapters

import com.application.archelon.models.NavigationItem
import com.application.archelon.models.NavigationItemType
import org.junit.Assert.*
import org.junit.Test

class MenuListOptionAdapterTests {

    @Test
    fun testCountMatches() {
        val items = ArrayList<NavigationItem>()
        items.add(NavigationItem(id = NavigationItemType.SURVEY_ADD_REMARK))
        items.add(NavigationItem(id = NavigationItemType.SURVEY_ADD_REMARK))
        val adapter = MenuListOptionAdapter(items)
        assertEquals(adapter.itemCount, items.size)
    }

}