package com.application.archelon.screens.survey.new_event.inundation_or_wash

import android.app.DatePickerDialog.OnDateSetListener
import android.app.TimePickerDialog.OnTimeSetListener
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.application.archelon.R
import com.application.archelon.databinding.FragmentNewEventInundationOrWashBinding
import com.application.archelon.interfaces.IOnBackPressed
import com.application.archelon.screens.survey.NewSurveyActivity
import com.application.archelon.screens.survey.NewSurveyViewModel
import com.application.archelon.utils.TimePickingUtil
import kotlinx.android.synthetic.main.fragment_new_event_inundation_or_wash.view.*

/**
 * EventInundationOrWashFragment
 * This is the fragment representing the data for a new Inundation or Wash model.
 */

class EventInundationOrWashFragment : Fragment(), View.OnClickListener, IOnBackPressed {

    private lateinit var viewLayout: View
    private lateinit var surveyViewModel: NewSurveyViewModel
    private lateinit var inundationOrWashViewModel: EventInundationOrWashViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.run {
            surveyViewModel = ViewModelProvider(this).get(NewSurveyViewModel::class.java)
        }
        // This model is only scoped to this fragment.
        // This is used to feed model data to the NewSurveyViewModel when an event is updated/added.
        inundationOrWashViewModel =
            ViewModelProvider(this).get(EventInundationOrWashViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentNewEventInundationOrWashBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_new_event_inundation_or_wash, container, false
        )
        binding.let {
            it.eventViewModel = inundationOrWashViewModel
            it.lifecycleOwner = this
        }
        viewLayout = binding.root
        surveyViewModel.setCurrentTitle(viewLayout.context.getString(R.string.new_survey_event_inundation_or_wash))

        prepareListeners()
        return viewLayout
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.eventDatePickerButton -> handleActionPickDate()
            R.id.eventTimePickerButton -> handleActionPickTime()
            R.id.eventTakePhotoButton -> handleActionPhoto()
            R.id.cancelEntryButton -> handleCancelEntry()
            R.id.confirmEntryButton -> handleConfirmEntry()
        }
    }

    private fun prepareListeners() {
        viewLayout.eventDatePickerButton.setOnClickListener(this)
        viewLayout.eventTimePickerButton.setOnClickListener(this)
        viewLayout.eventTakePhotoButton.setOnClickListener(this)
        viewLayout.cancelEntryButton.setOnClickListener(this)
        viewLayout.confirmEntryButton.setOnClickListener(this)

    }

    private fun handleActionPhoto() {
        // Not much else to do now as the API does not handle anything related to photos except for identifiers.
        // We can handle the file here if this is ever implemented.
        // For now just show a UI message and generate the identifier.
        Toast.makeText(viewLayout.context, "**Shutter Sounds** a pic was taken", Toast.LENGTH_SHORT)
            .show()
        inundationOrWashViewModel.setPhotoIdentifierForCapturedImage()
    }


    private fun handleActionPickDate() {
        TimePickingUtil.actionPickDateForContext(
            viewLayout.context,
            OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
                inundationOrWashViewModel.setDateForEventDateTime(year, monthOfYear, dayOfMonth)
            })
    }

    private fun handleActionPickTime() {
        TimePickingUtil.actionPickTimeForContext(
            viewLayout.context,
            OnTimeSetListener { _, hourOfDay, minute ->
                inundationOrWashViewModel.setTimeForEventDateTime(hourOfDay, minute)
            })
    }

    private fun handleConfirmEntry() {
        //Take this model's data, and set it on the SurveyModel so it can be part of the enclosing survey.
        //This way data will be pushed only at the point the whole survey is saved.
        surveyViewModel.addEventToSurvey(inundationOrWashViewModel.getCurrentEventModelData())
        Toast.makeText(
            viewLayout.context,
            R.string.event_saved,
            Toast.LENGTH_SHORT
        ).show()
        (activity as NewSurveyActivity).finishSurveyEvent()
    }

    private fun handleCancelEntry() {
        (activity as NewSurveyActivity).showConfirmationForDiscardEvent()
    }

    companion object {
        const val TAG = "EventInundationOrWashFragment"
        fun newInstance(): EventInundationOrWashFragment {
            return EventInundationOrWashFragment()
        }
    }

    override fun onBackPressed() {
        handleCancelEntry()
    }
}