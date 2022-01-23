package com.application.archelon.screens.survey

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.application.archelon.R
import com.application.archelon.adapters.MenuListOptionAdapter
import com.application.archelon.interfaces.OnNavigationItemClickListener
import com.application.archelon.models.NavigationItem
import com.application.archelon.models.NavigationItemType
import com.application.archelon.extensions.replaceFragment
import com.application.archelon.interfaces.IOnBackPressed
import com.application.archelon.repositories.SurveySaveState
import com.application.archelon.screens.survey.new_event.NewEventMenuFragment
import com.application.archelon.utils.ActivityUtil.showNotImplementedToast
import kotlinx.android.synthetic.main.fragment_simple_menu.view.*

/**
 * NewSurveyMenuFragment
 * This is the fragment representing the list of options for a survey (add event, etc).
 */

class NewSurveyMenuFragment : Fragment(),
    OnNavigationItemClickListener,
    IOnBackPressed {

    private lateinit var viewLayout: View
    private lateinit var viewModel: NewSurveyViewModel
    private lateinit var adapter: MenuListOptionAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.run {
            viewModel = ViewModelProvider(this).get(NewSurveyViewModel::class.java)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        prepareObservers()
        loadView(inflater.inflate(R.layout.fragment_simple_menu, container, false))
        viewModel.setCurrentTitle(viewLayout.context.getString(R.string.new_survey_title))
        return viewLayout
    }

    private fun loadView(view: View) {
        viewLayout = view
        viewLayout.menuOptions.layoutManager = LinearLayoutManager(viewLayout.context)
        viewModel.setNewSurveyMenuOptions(viewLayout.context)
    }

    private fun prepareObservers() {
        viewModel.newSurveyMenuOptions.observe(viewLifecycleOwner, Observer { options ->
            adapter =
                MenuListOptionAdapter(options, this);
            viewLayout.menuOptions.adapter = adapter
        })

        viewModel.saveState.observe(viewLifecycleOwner, Observer { state ->
            when (state) {
                SurveySaveState.SaveError -> {
                    Toast.makeText(context, R.string.survey_completed_e, Toast.LENGTH_LONG).show();
                }
                SurveySaveState.SaveSuccess -> {
                    Toast.makeText(context, R.string.survey_completed_s, Toast.LENGTH_LONG).show();
                    goToHome()
                }
                SurveySaveState.SavePartial -> {
                    Toast.makeText(context, R.string.survey_completed_p, Toast.LENGTH_LONG).show();
                }
                else -> {
                    // Do nothing
                }
            }
        })

        viewModel.eventList.observe(viewLifecycleOwner, Observer { eventList ->
            if (eventList.isNotEmpty()) {
                viewLayout.additionalDetails.text =
                    String.format(
                        viewLayout.context.getString(R.string.new_survey_pending_events),
                        eventList.size
                    )
            } else {
                viewLayout.additionalDetails.text =
                    viewLayout.context.getString(R.string.new_survey_no_pending_events)
            }
        })
    }

    override fun onNavigationItemClick(view: View, item: NavigationItem) {
        // Handle item selection
        when (item.id) {
            NavigationItemType.SURVEY_NEW_EVENT -> handleActionNewEvent()
            NavigationItemType.SURVEY_FINISH_SURVEY -> handleActionFinishSurvey()
            else -> showNotImplementedToast(viewLayout.context, item.title)
        }
    }

    override fun onBackPressed() {
        showConfirmationForCancel()
    }

    private fun showConfirmationForCancel() {
        (activity as NewSurveyActivity).showConfirmationForCancelSurvey()
    }

    private fun handleActionNewEvent() {
        activity?.replaceFragment(NewEventMenuFragment.newInstance(), R.id.contentContainer, true)
    }

    private fun handleActionFinishSurvey() {
        val builder: AlertDialog.Builder = AlertDialog.Builder(context)
        builder.setMessage(R.string.new_survey_finish_warning)
            .setCancelable(false)
            .setTitle(R.string.new_event_end_survey)
            .setPositiveButton(
                R.string.new_survey_confirmation_proceed
            ) { _, _ -> viewModel.saveSurvey() }
            .setNegativeButton(
                R.string.new_survey_confirmation_cancel
            ) { dialog, _ -> dialog.cancel() }
        val alert: AlertDialog = builder.create()
        alert.show()
    }


    private fun goToHome() {
        (activity as NewSurveyActivity).goToHomeActivity()
    }

    companion object {
        fun newInstance(): NewSurveyMenuFragment {
            return NewSurveyMenuFragment()
        }
    }
}