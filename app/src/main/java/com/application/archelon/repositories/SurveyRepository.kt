package com.application.archelon.repositories

import android.util.Log
import com.application.archelon.interfaces.ISurveyEvent
import com.application.archelon.models.EventInundationOrWash
import com.application.archelon.models.Survey
import com.application.archelon.models.Weather
import com.application.archelon.services.ArchelonApi
import java.lang.Exception
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

/**
 * SurveyRepository
 * This is used to manage the Survey Data, this is where we persist it as needed.
 */

enum class SurveySaveState {
    SaveSuccess,
    SavePartial,
    SaveError,
    Unsaved
}

object SurveyRepository {
    suspend fun createSurveyFromData(
        beach: String,
        sector: String,
        surveyTime: LocalDateTime,
        surveyWeather: Weather,
        surveyEvents: List<ISurveyEvent>,
        observers: List<String?>
    ): SurveySaveState {

        /**
         * This is the raw data for the survey along with all the events that belong to it.
         *
         * Here we reconcile the application data models with the API.
         * Given that the provided API is not complete or does not adhere to the ideal survey model,
         * we can make do by storing one survey per event/nest combination as it was recommended by the Professor.
         *
         * For example, observers are not available in any API endpoint even though we allow the user to set them.
         * Weather isn't available in the API either.
         *
         * It is easy to update this logic if API gets changed since this is the "final" point.
         * Here we could also store into a database.
         */

        var totalSuccess = 0;

        for (e in surveyEvents) {
            var nestCode: String? = null
            var photoId: String? = null
            var comments: String? = null
            var emergenceEvent: String? = null
            var distanceToSea: Int? = 0

            when (e) {
                is EventInundationOrWash -> {
                    nestCode = e.nest?.nestCode
                    photoId = if (e.photoRecordId.isNullOrEmpty()) null else e.photoRecordId
                    comments = e.additionalComments
                    emergenceEvent = if (e.eventToNest.isNullOrEmpty()) null else e.eventToNest
                    distanceToSea = e.distanceFromNestToWaterLine?.toInt() ?: 0
                }
            }
            val s = Survey(
                date = surveyTime.format(DateTimeFormatter.ISO_DATE),
                beach = beach,
                emergenceEvent = emergenceEvent,
                distanceToSea = distanceToSea,
                sector = sector,
                nest = nestCode != null,
                nestCode = nestCode,
                comments = comments,
                photoId = photoId
            )
            totalSuccess += if (pushSurvey(s) === SurveySaveState.SaveSuccess) 1 else 0
        }

        if (totalSuccess == surveyEvents.size) {
            return SurveySaveState.SaveSuccess
        }
        if (totalSuccess == 0) {
            return SurveySaveState.SaveError
        }
        return SurveySaveState.SavePartial
    }

    private suspend fun pushSurvey(s: Survey): SurveySaveState {
        try {
            val response = ArchelonApi.service.saveMorningSurvey(s)
            if (!(response.isSuccessful)) {
                response.errorBody()?.string()
                    ?.let { Log.i("[Survey Repository]", "Error [${response.code()}] " + it) };
                return SurveySaveState.SaveError
            }
            return SurveySaveState.SaveSuccess
        } catch (e: Exception) {
            Log.e("[Survey Repository]", "Error making request", e)
            return SurveySaveState.SaveError
        }
    }
}