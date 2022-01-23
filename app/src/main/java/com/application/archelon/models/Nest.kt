package com.application.archelon.models

import com.squareup.moshi.Json

/**
 * Nest
 * Model to maintain the data for a Nest, entities are mapped to the Achelon API.
 */

data class Nest(
    @Json(name = "nest_code")
    val nestCode: String,

    val beach: String,

    val sector: String,

    @Json(name = "subsector")
    val subSector: String? = null,

    @Json(name = "protection_measures")
    val protectionMeasures: String? = null,

    @Json(name = "inundated_during_incubation")
    val inundatedDuringIncubation: Boolean? = null,

    @Json(name = "predated_during_incubation")
    val predatedDuringIncubation: Boolean? = null,

    @Json(name = "date_of_first_hatching")
    val dateOfFirstHatching: String? = null,

    @Json(name = "date_of_last_hatching")
    val dateOfLastHatching: String? = null,

    @Json(name = "predated_during_hatching")
    val predatedDuringHatching: Boolean? = null,

    @Json(name = "inundated_during_hatching")
    val inundatedDuringHatching: Boolean? = null,

    @Json(name = "affected_by_light_pollution")
    val affectedByLightPollution: Boolean? = null,

    @Json(name = "excavation_date")
    val excavationDate: String? = null,

    @Json(name = "excavation_bottom_of_nest_depth")
    val excavationBottomNestDate: String? = null,

    @Json(name = "hatched_eggs")
    val hatchedEggs: Int? = null,

    @Json(name = "pipped_dead_hatchlings")
    val pippedDeadHatchlings: Int? = null,

    @Json(name = "pipped_live_hatchlings")
    val pippedLiveHatchlings: Int? = null,

    @Json(name = "no_embryos_in_unhatched_eggs")
    val noEmbryosInUnhatchedEggs: Int? = null,

    @Json(name = "dead_embryos_in_unhatched_eggs_eye_spot")
    val deadEmbryosInUnhatchedEggsEyeSpot: Int? = null,

    @Json(name = "dead_embryos_in_unhatched_eggs_early")
    val deadEmbryosInUnhatchedEggsEarly: Int? = null,

    @Json(name = "dead_embryos_in_unhatched_eggs_middle")
    val deadEmbryosInUnhatchedEggsMiddle: Int? = null,

    @Json(name = "dead_embryos_in_unhatched_eggs_late")
    val deadEmbryosInUnhatchedEggsLate: Int? = null,

    @Json(name = "live_embryos_in_unhatched_eggs")
    val liveEmbryosInUnhatchedEggs: Int? = null,

    @Json(name = "dead_hatchlings_in_nest")
    val deadHatchlingsInNest: Int? = null,

    @Json(name = "live_hatchlings_in_nest")
    val liveHatchlingsInNest: Int? = null,

    @Json(name = "excavation_comments")
    val excavationComments: String? = null,

    @Json(name = "general_comments")
    val generalComments: String? = null
)