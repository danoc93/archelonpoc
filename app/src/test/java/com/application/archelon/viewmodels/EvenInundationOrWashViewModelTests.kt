package com.application.archelon.viewmodels

import android.os.Build
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import org.junit.Rule
import org.junit.Test
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.application.archelon.screens.survey.new_event.inundation_or_wash.EventInundationOrWashViewModel
import com.application.archelon.test_utils.getOrAwaitValue
import com.application.archelon.utils.DataUtil.LABEL_ROOT
import org.junit.runner.RunWith
import org.robolectric.annotation.Config

@Config(sdk = [Build.VERSION_CODES.P])
@RunWith(AndroidJUnit4::class)
class EvenInundationOrWashViewModelTests {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Test
    fun setPhotoIdentifierUpdatesTheData() {
        val eventModel = EventInundationOrWashViewModel()
        eventModel.additionalCommentsValue.value = "Fake Additional Comments"
        // Then the new task event is triggered
        assert(
            eventModel.additionalCommentsValue
                .getOrAwaitValue() == "Fake Additional Comments"
        )
    }

    @Test
    fun ensureSubTypesAreAvailable() {
        val eventModel = EventInundationOrWashViewModel()
        // All data is there?
        assert(eventModel.availableSubEventTypes.isNotEmpty())
    }

    @Test
    fun ensurePhotoIdentifierIsSetWhenDefined() {
        val eventModel = EventInundationOrWashViewModel()
        eventModel.setPhotoIdentifierForCapturedImage()
        val currentId = eventModel.photoIdentifier.getOrAwaitValue()
        // Then the new task event is triggered
        assert(currentId.startsWith(LABEL_ROOT))
    }

}
