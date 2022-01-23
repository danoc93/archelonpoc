package com.application.archelon.screens.survey.new_event

import android.os.Bundle
import android.view.*
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
import com.application.archelon.screens.survey.NewSurveyViewModel
import com.application.archelon.screens.survey.new_event.inundation_or_wash.EventInundationOrWashFragment
import com.application.archelon.utils.ActivityUtil.showNotImplementedToast
import kotlinx.android.synthetic.main.fragment_simple_menu.view.*

/**
 * NewEventMenuFragment
 * This is the fragment representing the list of options for a new event (e.g. event types).
 */

class NewEventMenuFragment : Fragment(),
    OnNavigationItemClickListener {

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
        viewModel.setCurrentTitle(viewLayout.context.getString(R.string.new_survey_event_new))
        return viewLayout
    }

    override fun onNavigationItemClick(view: View, item: NavigationItem) {
        // Handle item selection for each event type
        when (item.id) {
            NavigationItemType.SURVEY_EVENT_INUNDATION_OR_WASH -> handleNewEventInundationOrWash()
            else -> showNotImplementedToast(view.context, item.title)
        }
    }

    private fun loadView(view: View) {
        viewLayout = view
        viewLayout.menuOptions.layoutManager = LinearLayoutManager(viewLayout.context)
        viewModel.setNewEventTypeOptions(viewLayout.context)
    }

    private fun prepareObservers() {
        viewModel.newEventTypeOptions.observe(viewLifecycleOwner, Observer { options ->
            adapter =
                MenuListOptionAdapter(options, this);
            viewLayout.menuOptions.adapter = adapter
        })
    }

    private fun handleNewEventInundationOrWash() {
        activity?.replaceFragment(
            EventInundationOrWashFragment.newInstance(),
            R.id.contentContainer,
            true,
            EventInundationOrWashFragment.TAG
        )
    }

    companion object {
        fun newInstance(): NewEventMenuFragment {
            return NewEventMenuFragment()
        }
    }

}