package com.application.archelon.utils

import org.junit.Test

import org.junit.Assert.*

class DataUtilTests {

    @Test
    fun validLabelIsReturned() {
        val id  = DataUtil.getPhotoIdentifier()
        assertTrue(id.startsWith(DataUtil.LABEL_ROOT))
    }

}
