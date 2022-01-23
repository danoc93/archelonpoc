package com.application.archelon.models

import com.squareup.moshi.Json

/**
 * Survey
 * Model to maintain the data for a Survey, entities are mapped to the Achelon API.
 */

data class Survey(
    @Json(name = "surveyId")
    val surveyId: String? = null,

    val date: String,

    val area: String? = null,

    val beach: String,

    val sector: String,

    @Json(name = "subsector")
    val subSector: String? = null,

    @Json(name = "emergence_event")
    val emergenceEvent: String? = null,

    val nest: Boolean,

    @Json(name = "nest_code")
    val nestCode: String? = null,

    val comments: String? = null,

    val tags: String? = null,

    @Json(name = "distance_to_sea")
    val distanceToSea: Int? = 0,

    @Json(name = "track_type")
    val trackType: String? = null,

    @Json(name = "non_nesting_attempts")
    val nonNestingAttempts: Int? = 0,

    @Json(name = "gps_latitude")
    val gpsLatitude: Double? = 0.0,

    @Json(name = "gps_longitude")
    val gpsLongitude: Double? = 0.0,

    @Json(name = "photo_id")
    val photoId: String? = null
)