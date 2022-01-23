package com.application.archelon.utils

import android.app.Activity
import android.app.DatePickerDialog
import org.junit.Test

import org.junit.Assert.*

class TimePickingUtilTests {

    @Test
    fun datePickerIsValidPickerReturned() {
        val datePickerDialog = TimePickingUtil.actionPickDateForContext(
            Activity(),
            DatePickerDialog.OnDateSetListener { _, _, _, _ -> {} }
        )
        assertEquals(datePickerDialog.context, null)
    }

}
