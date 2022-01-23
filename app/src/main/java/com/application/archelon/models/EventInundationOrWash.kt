package com.application.archelon.models

import com.application.archelon.interfaces.ISurveyEvent
import com.application.archelon.repositories.EventType
import java.time.LocalDateTime

/**
 * EventInundationOrWash
 * Model to maintain data for Inundation or Wash events.
 */

data class EventInundationOrWash(
    val eventDateTime: LocalDateTime?,

    val nest: Nest?,

    val eventToNest: String?,

    val distanceFromNestToWaterLine: Double?,

    val heightOfSandDeposited: Double?,

    val photoRecordId: String?,

    val additionalComments: String?

) : ISurveyEvent {
    override fun getEventType(): EventType {
        return EventType.InundationOrWash
    }
}