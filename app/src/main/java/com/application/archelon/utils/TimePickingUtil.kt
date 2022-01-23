package com.application.archelon.utils

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import java.util.*

class TimePickingUtil {
    companion object {
        fun actionPickDateForContext(
            context: Context,
            listener: DatePickerDialog.OnDateSetListener
        ): DatePickerDialog {
            val c: Calendar = Calendar.getInstance()
            val datePickerDialog = DatePickerDialog(
                context,
                listener,
                c.get(Calendar.YEAR),
                c.get(Calendar.MONTH),
                c.get(Calendar.DAY_OF_MONTH)
            )
            datePickerDialog.show()
            return datePickerDialog
        }
        fun actionPickTimeForContext(
            context: Context,
            listener: TimePickerDialog.OnTimeSetListener
        ): TimePickerDialog {
            val c = Calendar.getInstance()
            val timePickerDialog = TimePickerDialog(
                context,
                listener,
                c[Calendar.HOUR_OF_DAY],
                c[Calendar.MINUTE],
                false
            )
            timePickerDialog.show()
            return timePickerDialog
        }
    }
}