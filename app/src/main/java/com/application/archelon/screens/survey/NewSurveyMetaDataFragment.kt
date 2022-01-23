package com.application.archelon.screens.survey

import android.app.DatePickerDialog.OnDateSetListener
import android.app.TimePickerDialog.OnTimeSetListener
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.application.archelon.R
import com.application.archelon.extensions.replaceFragment
import com.application.archelon.interfaces.IOnBackPressed
import com.application.archelon.utils.TimePickingUtil
import kotlinx.android.synthetic.main.fragment_new_survey_initialmeta.view.*
import androidx.databinding.DataBindingUtil
import com.application.archelon.databinding.FragmentNewSurveyInitialmetaBinding

/**
 * NewSurveyMetaDataFragment
 * This is the fragment representing the survey metadata (beach, location, etc)
 */

class NewSurveyMetaDataFragment : Fragment(), View.OnClickListener, IOnBackPressed {

    private lateinit var viewLayout: View
    private lateinit var surveyViewModel: NewSurveyViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.run {
            surveyViewModel = ViewModelProvider(this).get(NewSurveyViewModel::class.java)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding: FragmentNewSurveyInitialmetaBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_new_survey_initialmeta, container, false
        )
        binding.let {
            it.surveyViewModel = surveyViewModel
            it.lifecycleOwner = this
        }
        viewLayout = binding.root
        surveyViewModel.setCurrentTitle(viewLayout.context.getString(R.string.new_survey_title))
        prepareListeners()
        initializeRelevantModelData()

        return viewLayout
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.surveyTimePickButton -> handleActionPickTime()
            R.id.surveyDatePickButton -> handleActionPickDate()
            R.id.startSurveyButton -> handleActionStartSurvey()
        }
    }

    override fun onBackPressed() {
        showCancelConfirmation()
    }

    private fun initializeRelevantModelData() {
        surveyViewModel.initializeWeatherObjectData(viewLayout.context)
    }

    private fun prepareListeners() {
        viewLayout.surveyTimePickButton.setOnClickListener(this)
        viewLayout.surveyDatePickButton.setOnClickListener(this)
        viewLayout.startSurveyButton.setOnClickListener(this)
    }

    private fun showCancelConfirmation() {
        (activity as NewSurveyActivity).showConfirmationForCancelSurvey()
    }

    private fun handleActionStartSurvey() {
        activity?.replaceFragment(NewSurveyMenuFragment.newInstance(), R.id.contentContainer, true)
    }

    private fun handleActionPickDate() {
        TimePickingUtil.actionPickDateForContext(
            viewLayout.context,
            OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
                surveyViewModel.setDateForSurveyDateTime(year, monthOfYear, dayOfMonth)
            })
    }

    private fun handleActionPickTime() {
        TimePickingUtil.actionPickTimeForContext(
            viewLayout.context,
            OnTimeSetListener { _, hourOfDay, minute ->
                surveyViewModel.setTimeForSurveyDateTime(hourOfDay, minute)
            })
    }

    companion object {
        fun newInstance(): NewSurveyMetaDataFragment {
            return NewSurveyMetaDataFragment()
        }
    }
}