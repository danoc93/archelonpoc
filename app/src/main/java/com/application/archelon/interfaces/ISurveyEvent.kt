package com.application.archelon.interfaces

import com.application.archelon.repositories.EventType

/**
 * ISurveyEvent
 * General interface for a Survey Event.
 */

interface ISurveyEvent {
    fun getEventType(): EventType
}