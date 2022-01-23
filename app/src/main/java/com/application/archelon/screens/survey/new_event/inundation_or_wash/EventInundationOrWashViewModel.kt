package com.application.archelon.screens.survey.new_event.inundation_or_wash

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.application.archelon.models.EventInundationOrWash
import com.application.archelon.models.Nest
import com.application.archelon.models.SimpleItem
import com.application.archelon.repositories.NestRepository
import com.application.archelon.utils.DataUtil.getPhotoIdentifier
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.lang.Exception

import java.time.LocalDateTime

/**
 * EventInundationOrWashViewModel
 *
 * This ViewModel maintains the state of this specific type of event.
 * On submission, its state is passed to the top level survey EventModel.
 */

class EventInundationOrWashViewModel : ViewModel() {

    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    var inFetchNestError = MutableLiveData<Boolean>(false)

    private val _availableSubEventTypes: List<SimpleItem> = getSupportedSubEventTypes()
    val availableSubEventTypes: List<String>
        get() = _availableSubEventTypes.map { i -> i.name }
    private val _currentSubEventType = MutableLiveData<Int?>()
    val currentSubEventType: MutableLiveData<Int?>
        get() = _currentSubEventType

    private var _availableNestDataList: List<Nest> = listOf()
    private val _availableNests = MutableLiveData<List<String>>()
    val availableNests: MutableLiveData<List<String>>
        get() = _availableNests

    private val _currentNestForEvent = MutableLiveData<Int?>()
    val currentNestForEvent: MutableLiveData<Int?>
        get() = _currentNestForEvent

    private val _additionalCommentsValue = MutableLiveData<String>()
    val additionalCommentsValue: MutableLiveData<String>
        get() = _additionalCommentsValue

    private val _distFromNestToWaterValue = MutableLiveData<String>()
    val distFromNestToWaterValue: MutableLiveData<String>
        get() = _distFromNestToWaterValue

    private val _heightSandFromNest = MutableLiveData<String>()
    val heightSandFromNest: MutableLiveData<String>
        get() = _heightSandFromNest

    private val _photoIdentifier = MutableLiveData<String>()
    val photoIdentifier: MutableLiveData<String>
        get() = _photoIdentifier

    private val _eventDateTime = MutableLiveData<LocalDateTime>()
    val eventDateTime: MutableLiveData<LocalDateTime>
        get() = _eventDateTime

    init {
        _eventDateTime.value = LocalDateTime.now()
        coroutineScope.launch {
            try {
                _availableNestDataList = NestRepository.getAll()
                _availableNests.value = _availableNestDataList.map { nest ->
                    nest.nestCode
                }
            } catch (e: Exception) {
                Log.e("EventInundationOrWash", "Error Fetching Nests", e)
                inFetchNestError.value = true
            }

        }
    }

    fun setTimeForEventDateTime(hourOfDay: Int, minute: Int) {
        val currValue = _eventDateTime.value;
        _eventDateTime.value = LocalDateTime.of(
            currValue?.year ?: Int.MIN_VALUE,
            currValue?.monthValue ?: 1,
            currValue?.dayOfMonth ?: 1,
            hourOfDay,
            minute
        )
    }

    fun setDateForEventDateTime(year: Int, monthOfYear: Int, dayOfMonth: Int) {
        val currValue = _eventDateTime.value;
        _eventDateTime.value = LocalDateTime.of(
            year,
            monthOfYear + 1,
            dayOfMonth,
            currValue?.hour ?: 0,
            currValue?.minute ?: 0
        )
    }

    fun getCurrentEventModelData(): EventInundationOrWash {

        val currentNestIndex = _currentNestForEvent.value ?: 0
        val currentNest =
            if (_availableNestDataList.isNotEmpty())
                _availableNestDataList[currentNestIndex] else null
        val eventSelectedIndex = _currentSubEventType.value ?: 0
        val eventSelected = _availableSubEventTypes[eventSelectedIndex]
        val currentDistanceToWaterLine = _distFromNestToWaterValue.value?.toDouble() ?: 0.0
        val currentHeight = _heightSandFromNest.value?.toDouble() ?: 0.0
        val photoRecordId = _photoIdentifier.value ?: ""
        val comments = _additionalCommentsValue.value ?: ""

        return EventInundationOrWash(
            eventDateTime = _eventDateTime.value,
            nest = currentNest,
            eventToNest = eventSelected.name,
            distanceFromNestToWaterLine = currentDistanceToWaterLine,
            heightOfSandDeposited = currentHeight,
            photoRecordId = photoRecordId,
            additionalComments = comments
        )
    }

    fun clearPhotoIdentifierForCapturedImage() {
        _photoIdentifier.value = null

    }

    fun setPhotoIdentifierForCapturedImage() {
        _photoIdentifier.value = getPhotoIdentifier()
    }

    private fun getSupportedSubEventTypes(): List<SimpleItem> {
        return listOf(SimpleItem(1, "I"), SimpleItem(2, "W"))
    }

    override fun onCleared() {
        viewModelJob.cancel()
        super.onCleared()
    }

}