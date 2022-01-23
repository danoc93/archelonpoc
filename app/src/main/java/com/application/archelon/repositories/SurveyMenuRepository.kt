package com.application.archelon.repositories

import android.content.Context
import com.application.archelon.R
import com.application.archelon.models.NavigationItem
import com.application.archelon.models.NavigationItemType

/**
 * SurveyMenuRepository
 * This is used to manage the menus for the Survey views.
 */

object SurveyMenuRepository {
    fun getAllOptions(context: Context): List<NavigationItem> {
        val options = ArrayList<NavigationItem>()
        options.add(
            NavigationItem(
                id = NavigationItemType.SURVEY_NEW_EVENT,
                title = context.getString(R.string.new_survey_event_new)
            )
        )
        options.add(
            NavigationItem(
                id = NavigationItemType.SURVEY_PREVIOUS_ENTRY,
                title = context.getString(R.string.new_survey_previous_entry)
            )
        )
        options.add(
            NavigationItem(
                id = NavigationItemType.SURVEY_CHECK_NEST,
                title = context.getString(R.string.new_survey_check_nest)
            )
        )
        options.add(
            NavigationItem(
                id = NavigationItemType.SURVEY_EMERGENCY_RELOCATION,
                title = context.getString(R.string.new_survey_emergency_relocation)
            )
        )
        options.add(
            NavigationItem(
                id = NavigationItemType.SURVEY_ADD_REMARK,
                title = context.getString(R.string.new_event_add_survey_remark)
            )
        )
        options.add(
            NavigationItem(
                id = NavigationItemType.SURVEY_FINISH_SURVEY,
                title = context.getString(R.string.new_event_end_survey)
            )
        )
        return options;
    }

    fun getAllEventOptions(context: Context): List<NavigationItem> {
        val options = ArrayList<NavigationItem>()
        options.add(
            NavigationItem(
                id = NavigationItemType.SURVEY_EVENT_INUNDATION_OR_WASH,
                title = context.getString(R.string.new_survey_event_inundation_or_wash)
            )
        )
        return options;
    }
}