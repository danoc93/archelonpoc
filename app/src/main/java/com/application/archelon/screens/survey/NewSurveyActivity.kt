package com.application.archelon.screens.survey

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.application.archelon.R
import com.application.archelon.extensions.replaceFragment
import com.application.archelon.interfaces.IOnBackPressed
import com.application.archelon.screens.home.HomeActivity
import kotlinx.android.synthetic.main.activity_generic.*

/**
 * NewSurveyActivity
 * This activity contains all the behaviour to create/maintain a survey.
 * It uses fragment transitions at different stages.
 */

class NewSurveyActivity : AppCompatActivity(), IOnBackPressed {

    private lateinit var initialSurveyFragment: NewSurveyMetaDataFragment
    private lateinit var viewModel: NewSurveyViewModel;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_survey)
        setSupportActionBar(toolbar)
        viewModel = ViewModelProvider(this).get(NewSurveyViewModel::class.java)
        listenForTitle(viewModel)
        loadHomeFragment()
    }

    override fun onBackPressed() {
        /**
         * This allows the activity to keep track of the fragments.
         * Because of IOnBackPressed, we can delegate the handler to the fragment in case they implemented the behaviour.
         * This will only work as long as the fragment has defined the right tags when being pushed to the stack.
         */
        val fm = supportFragmentManager
        if (fm.backStackEntryCount > 0) {
            val tail = fm.getBackStackEntryAt(fm.backStackEntryCount - 1)
            val fragName = tail.name
            val fragment = fm.findFragmentByTag(fragName)
            if (fragment is IOnBackPressed) {
                (fragment as IOnBackPressed).onBackPressed()
            } else {
                fm.popBackStack()
            }
        } else {
            initialSurveyFragment.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.new_survey_menu, menu)
        super.onCreateOptionsMenu(menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle item selection
        when (item.itemId) {
            R.id.action_survey_cancel_progress -> showConfirmationForCancelSurvey()
            else -> super.onOptionsItemSelected(item)
        }
        return true
    }

    private fun loadHomeFragment() {
        initialSurveyFragment = NewSurveyMetaDataFragment.newInstance()
        replaceFragment(initialSurveyFragment, R.id.contentContainer)
    }

    private fun listenForTitle(viewModel: NewSurveyViewModel) {
        viewModel.currentTitle.observe(this, Observer { currentTitle ->
            toolbarTitle.text = currentTitle
        })
    }

    fun finishSurveyEvent() {
        // Because these are in the frame stack, it is just a matter of popping them.
        val fm = supportFragmentManager
        fm.popBackStack()
        fm.popBackStack()
    }

    fun showConfirmationForDiscardEvent() {
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setMessage(R.string.survey_event_cancel_confirmation)
            .setCancelable(false)
            .setTitle(R.string.new_survey_cancel_title)
            .setPositiveButton(
                R.string.new_survey_cancel_confirm
            ) { _, _ -> finishSurveyEvent() }
            .setNegativeButton(
                R.string.cancel
            ) { dialog, _ -> dialog.cancel() }
        val alert: AlertDialog = builder.create()
        alert.show()
    }

    fun showConfirmationForCancelSurvey() {
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setMessage(R.string.new_survey_cancel_warning)
            .setCancelable(false)
            .setTitle(R.string.new_survey_cancel)
            .setPositiveButton(
                R.string.new_survey_confirmation_proceed
            ) { _, _ -> goToHomeActivity() }
            .setNegativeButton(
                R.string.new_survey_confirmation_cancel
            ) { dialog, _ -> dialog.cancel() }
        val alert: AlertDialog = builder.create()
        alert.show()
    }

    fun goToHomeActivity() {
        val intent = Intent(this, HomeActivity::class.java);
        //We do this to clear the back button as to avoid duplicates in the graph.
        //This essentially makes the navigation path fresh.
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        this.startActivity(intent)
        this.finish();
    }

}
