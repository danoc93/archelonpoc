package com.application.archelon.screens.survey

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.application.archelon.models.NavigationItem
import com.application.archelon.interfaces.ISurveyEvent
import com.application.archelon.models.SimpleItem
import com.application.archelon.models.Weather
import com.application.archelon.repositories.*
import com.application.archelon.repositories.SurveyRepository.createSurveyFromData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.time.LocalDateTime

/**
 * NewSurveyViewModel
 * This is the top level model maintaining the state of the whole survey activity.
 * Apart from holding the survey meta information, it also maintains a list of events which is
 * updated every time a new event is created.
 *
 * This ViewModel's data is what is serialized and sent to the API via the repository.
 */

class NewSurveyViewModel : ViewModel() {

    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    private val _currentTitle = MutableLiveData<String>()
    val currentTitle: MutableLiveData<String>
        get() = _currentTitle

    private val _saveState = MutableLiveData<SurveySaveState>(SurveySaveState.Unsaved)
    val saveState: MutableLiveData<SurveySaveState>
        get() = _saveState

    private val _newSurveyMenuOptions = MutableLiveData<List<NavigationItem>>()
    val newSurveyMenuOptions: MutableLiveData<List<NavigationItem>>
        get() = _newSurveyMenuOptions

    private val _eventList = MutableLiveData<MutableList<ISurveyEvent>>(mutableListOf())
    val eventList: MutableLiveData<MutableList<ISurveyEvent>>
        get() = _eventList

    private val _newEventTypeOptions = MutableLiveData<List<NavigationItem>>()
    val newEventTypeOptions: MutableLiveData<List<NavigationItem>>
        get() = _newEventTypeOptions

    private val _availableBeachList: List<SimpleItem> = BeachRepository.getAll()
    val availableBeachLabelList: List<String>
        get() = _availableBeachList.map { i -> i.name }

    private val _currentSurveyBeachPosition = MutableLiveData<Int?>()
    val currentSurveyBeachPosition: MutableLiveData<Int?>
        get() = _currentSurveyBeachPosition

    private val _availableSectorList: List<SimpleItem> = SectorRepository.getAll()
    val availableSectorLabelList: List<String>
        get() = _availableSectorList.map { i -> i.name }
    private val _currentSurveySectorPosition = MutableLiveData<Int?>()
    val currentSurveySectorPosition: MutableLiveData<Int?>
        get() = _currentSurveySectorPosition

    private var _availableWeatherSky: List<SimpleItem> = listOf()
    val availableWeatherSky: List<String>
        get() = _availableWeatherSky.map { i -> i.name }
    private val _currentWeatherSkyPosition = MutableLiveData<Int?>()
    val currentWeatherSkyPosition: MutableLiveData<Int?>
        get() = _currentWeatherSkyPosition

    private var _availableWeatherPrecipitation: List<SimpleItem> = listOf()
    val availableWeatherPrecipitation: List<String>
        get() = _availableWeatherPrecipitation.map { i -> i.name }
    private val _currentWeatherPrecipitationPosition = MutableLiveData<Int?>()
    val currentWeatherPrecipitationPosition: MutableLiveData<Int?>
        get() = _currentWeatherPrecipitationPosition

    private var _availableWeatherWindIntensity: List<SimpleItem> = listOf()
    val availableWeatherWindIntensity: List<String>
        get() = _availableWeatherWindIntensity.map { i -> i.name }
    private val _currentWeatherWindIntensityPosition = MutableLiveData<Int?>()
    val currentWeatherWindIntensityPosition: MutableLiveData<Int?>
        get() = _currentWeatherWindIntensityPosition

    private var _availableWeatherWindDirection: List<SimpleItem> = listOf()
    val availableWeatherWindDirection: List<String>
        get() = _availableWeatherWindDirection.map { i -> i.name }
    private val _currentWeatherWindDirectionPosition = MutableLiveData<Int?>()
    val currentWeatherWindDirectionPosition: MutableLiveData<Int?>
        get() = _currentWeatherWindDirectionPosition

    private var _availableWeatherSurf: List<SimpleItem> = listOf()
    val availableWeatherSurf: List<String>
        get() = _availableWeatherSurf.map { i -> i.name }
    private val _currentWeatherSurfPosition = MutableLiveData<Int?>()
    val currentWeatherSurfPosition: MutableLiveData<Int?>
        get() = _currentWeatherSurfPosition

    private val _observerLeaderValue = MutableLiveData<String>()
    val observerLeaderValue: MutableLiveData<String>
        get() = _observerLeaderValue

    private val _observerSecondValue = MutableLiveData<String>()
    val observerSecondValue: MutableLiveData<String>
        get() = _observerSecondValue

    private val _observerThirdValue = MutableLiveData<String>()
    val observerThirdValue: MutableLiveData<String>
        get() = _observerThirdValue

    private val _observerFourthValue = MutableLiveData<String>()
    val observerFourthValue: MutableLiveData<String>
        get() = _observerFourthValue

    private val _surveyDateTime = MutableLiveData<LocalDateTime>()
    val surveyDateTime: MutableLiveData<LocalDateTime>
        get() = _surveyDateTime

    init {
        _surveyDateTime.value = LocalDateTime.now()
    }

    fun setCurrentTitle(title: String) {
        currentTitle.value = title
    }

    fun setTimeForSurveyDateTime(hourOfDay: Int, minute: Int) {
        val currValue = _surveyDateTime.value;
        _surveyDateTime.value = LocalDateTime.of(
            currValue?.year ?: Int.MIN_VALUE,
            currValue?.monthValue ?: 1,
            currValue?.dayOfMonth ?: 1,
            hourOfDay,
            minute
        )
    }

    fun setDateForSurveyDateTime(year: Int, monthOfYear: Int, dayOfMonth: Int) {
        val currValue = _surveyDateTime.value;
        _surveyDateTime.value = LocalDateTime.of(
            year,
            monthOfYear + 1,
            dayOfMonth,
            currValue?.hour ?: 0,
            currValue?.minute ?: 0
        )
    }

    fun setNewEventTypeOptions(context: Context) {
        _newEventTypeOptions.value = SurveyMenuRepository.getAllEventOptions(context)
    }

    fun setNewSurveyMenuOptions(context: Context) {
        _newSurveyMenuOptions.value = SurveyMenuRepository.getAllOptions(context)
    }

    fun addEventToSurvey(event: ISurveyEvent) {
        _eventList.value?.add(event)
    }

    fun initializeWeatherObjectData(context: Context) {
        _availableWeatherPrecipitation = WeatherRepository.getPrecipitationOptions(context)
        _availableWeatherSky = WeatherRepository.getSkyOptions(context)
        _availableWeatherSurf = WeatherRepository.getSurfOptions(context)
        _availableWeatherWindDirection = WeatherRepository.getWindDirectionOptions(context)
        _availableWeatherWindIntensity = WeatherRepository.getWindIntensityOptions(context)
    }

    fun saveSurvey() {

        //Get all the relevant data for this survey.
        // Metadata and events.

        val beach = _availableBeachList[_currentSurveyBeachPosition.value ?: 0]
        val sector = _availableSectorList[currentSurveySectorPosition.value ?: 0]

        val firstObserver = _observerLeaderValue.value?.trim()
        val secondObserver = _observerSecondValue.value?.trim()
        val thirdObserver = _observerThirdValue.value?.trim()
        val fourthObserver = _observerFourthValue.value?.trim()

        val surveyTime = _surveyDateTime.value ?: LocalDateTime.now()

        val surveyWeather = Weather(
            sky = availableWeatherSky[_currentWeatherSkyPosition.value ?: 0],
            windDirection = availableWeatherWindDirection[_currentWeatherSkyPosition.value ?: 0],
            windIntensity = availableWeatherWindIntensity[_currentWeatherSkyPosition.value ?: 0],
            surf = availableWeatherSurf[_currentWeatherSkyPosition.value ?: 0],
            precipitation = availableWeatherPrecipitation[_currentWeatherSkyPosition.value ?: 0]
        )

        // Pass the survey data to the repository to save as desired.
        coroutineScope.launch {
            _saveState.value = createSurveyFromData(
                beach.name,
                sector.name,
                surveyTime,
                surveyWeather,
                _eventList.value ?: mutableListOf(),
                listOf(firstObserver, secondObserver, thirdObserver, fourthObserver)
            )
        }
    }

}