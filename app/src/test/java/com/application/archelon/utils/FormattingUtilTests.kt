package com.application.archelon.utils

import org.junit.Test

import org.junit.Assert.*
import java.time.LocalDateTime

class FormattingUtilTests {

    @Test
    fun dateIsCorrectlyFormatted() {
        val date = LocalDateTime.of(0, 1, 1, 0, 0)
        assertEquals(FormattingUtil.getDateFromDateTime(date), "0000-01-01")
    }

    @Test
    fun timeIsCorrectlyFormatted() {
        val date = LocalDateTime.of(0, 1, 1, 0, 0)
        assertEquals(FormattingUtil.getTimeFromDateTime(date), "00:00")
    }

}
