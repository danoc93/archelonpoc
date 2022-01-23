package com.application.archelon.utils

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class FormattingUtil {
    companion object {
        fun getDateFromDateTime(dateTime: LocalDateTime): String? {
            return dateTime.format(DateTimeFormatter.ISO_DATE)
        }

        fun getTimeFromDateTime(dateTime: LocalDateTime): String? {
            return dateTime.format(DateTimeFormatter.ofPattern("HH:mm"))
        }
    }
}