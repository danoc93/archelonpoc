package com.application.archelon.screens.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.application.archelon.R
import com.application.archelon.adapters.SectionCardAdapter
import com.application.archelon.interfaces.OnItemClickListener
import com.application.archelon.models.SimpleCategoryItem
import com.application.archelon.screens.survey.NewSurveyActivity
import com.application.archelon.utils.ActivityUtil.showNotImplementedToast
import kotlinx.android.synthetic.main.fragment_content_grid_list.view.*

class HomeFragment : Fragment(),
    OnItemClickListener {
    private lateinit var viewModel: HomeViewModel
    private lateinit var viewLayout: View

    private lateinit var adapter: SectionCardAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        prepareObservers()
        loadView(inflater.inflate(R.layout.fragment_content_grid_list, container, false))
        return this.viewLayout
    }

    private fun loadView(view: View) {
        viewLayout = view
    }

    private fun prepareObservers() {
        viewModel.homeSectionOptions.observe(viewLifecycleOwner, Observer { homeSectionData ->
            adapter = SectionCardAdapter(
                homeSectionData as ArrayList<SimpleCategoryItem>
            )
            adapter.setOnItemClickListener(this)
            viewLayout.rvList.layoutManager = GridLayoutManager(context, 2)
            viewLayout.rvList.adapter = adapter
        })
    }

    private fun onNewSurveyClick() {
        startActivity(Intent(activity, NewSurveyActivity::class.java))
    }

    override fun onItemClick(item: SimpleCategoryItem) {
        when (item.itemId) {
            SECTION_NEW_SURVEY -> onNewSurveyClick()
            SECTION_NEST_CHECK -> showNotImplementedToast(viewLayout.context, item.title)
            SECTION_START_EXCAVATION -> showNotImplementedToast(viewLayout.context, item.title)
            SECTION_EMERGENCY_RELOCATION -> showNotImplementedToast(viewLayout.context, item.title)
        }
    }

    companion object {
        fun newInstance(): HomeFragment {
            return HomeFragment()
        }
    }
}