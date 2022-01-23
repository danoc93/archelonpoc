package com.application.archelon.viewmodels

import android.content.Context
import android.os.Build
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.application.archelon.screens.survey.NewSurveyViewModel
import org.junit.Rule
import org.junit.Test
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.application.archelon.interfaces.ISurveyEvent
import com.application.archelon.repositories.EventType
import com.application.archelon.test_utils.getOrAwaitValue
import org.junit.Assert.assertEquals
import org.junit.runner.RunWith
import org.robolectric.annotation.Config

@Config(sdk = [Build.VERSION_CODES.P])
@RunWith(AndroidJUnit4::class)
class TasksViewModelTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Test
    fun initializeWeatherData_setsWeatherData() {
        val surveyModel = NewSurveyViewModel()
        val context: Context = ApplicationProvider.getApplicationContext()
        surveyModel.initializeWeatherObjectData(context)
        // Then the new task event is triggered
        assert(surveyModel.availableWeatherSurf.isNotEmpty())
        assert(surveyModel.availableWeatherSky.isNotEmpty())
        assert(surveyModel.availableWeatherPrecipitation.isNotEmpty())
        assert(surveyModel.availableWeatherWindDirection.isNotEmpty())
        assert(surveyModel.availableWeatherWindIntensity.isNotEmpty())
    }

    @Test
    fun unInitializeWeatherData_emptyData() {
        val surveyModel = NewSurveyViewModel()
        val context: Context = ApplicationProvider.getApplicationContext()
        // Then the new task event is triggered
        assert(surveyModel.availableWeatherSurf.isEmpty())
        assert(surveyModel.availableWeatherSky.isEmpty())
        assert(surveyModel.availableWeatherPrecipitation.isEmpty())
        assert(surveyModel.availableWeatherWindDirection.isEmpty())
        assert(surveyModel.availableWeatherWindIntensity.isEmpty())
    }

    @Test
    fun changingObserverData_updatesObserverData() {
        val surveyModel = NewSurveyViewModel()

        val testObserverName = "Test observer";
        surveyModel.observerLeaderValue.value = testObserverName
        val observerData = surveyModel.observerLeaderValue.getOrAwaitValue()

        // Then the new task event is triggered
        assertEquals(observerData, testObserverName)
    }

    @Test
    fun eventListIsChangedIfAddingNewEvent() {
        val surveyModel = NewSurveyViewModel()

        val e = MockEvent()
        val e2 = MockEvent()

        surveyModel.addEventToSurvey(e as ISurveyEvent)
        surveyModel.addEventToSurvey(e2 as ISurveyEvent)

        val eventData = surveyModel.eventList.getOrAwaitValue()

        // Then the new task event is triggered
        assertEquals(eventData.size, 2)
    }

}

class MockEvent: ISurveyEvent {
    override fun getEventType(): EventType {
        return EventType.InundationOrWash
    }
}