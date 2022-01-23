package com.application.archelon.screens.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.application.archelon.R
import com.application.archelon.models.SimpleCategoryItem

/**
 * HomeViewModel
 * ViewModel for the HomeActivity and its fragments.
 */

const val SECTION_NEW_SURVEY = "newSurvey";
const val SECTION_NEST_CHECK = "nestCheck";
const val SECTION_EMERGENCY_RELOCATION = "emergencyRelocation";
const val SECTION_START_EXCAVATION = "startExcavation";

class HomeViewModel : ViewModel() {

    val homeSectionOptions: MutableLiveData<List<SimpleCategoryItem>> by lazy {
        val homeSections = ArrayList<SimpleCategoryItem>()
        homeSections.add(
            SimpleCategoryItem(
                itemId = SECTION_NEW_SURVEY,
                title = R.string.home_page_option_new_survey,
                subtitle = null,
                backgroundPhotoDrawableRes = R.drawable.call_center
            )
        )
        homeSections.add(
            SimpleCategoryItem(
                itemId = SECTION_NEST_CHECK,
                title = R.string.home_page_option_new_check,
                subtitle = null,
                backgroundPhotoDrawableRes = R.drawable.turtle
            )
        )
        homeSections.add(
            SimpleCategoryItem(
                itemId = SECTION_EMERGENCY_RELOCATION,
                title = R.string.home_page_option_emergency_relocation,
                subtitle = null,
                backgroundPhotoDrawableRes = R.drawable.exchange
            )
        )
        homeSections.add(
            SimpleCategoryItem(
                itemId = SECTION_START_EXCAVATION,
                title = R.string.home_page_option_start_excavation,
                subtitle = null,
                backgroundPhotoDrawableRes = R.drawable.tools
            )
        )
        MutableLiveData<List<SimpleCategoryItem>>(homeSections)
    }

}