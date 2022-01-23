package com.application.archelon.screens

import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.application.archelon.R
import com.application.archelon.extensions.inTransaction
import com.application.archelon.system.SharedPrefs.Companion.getSharedPreferencesForContext

/**
 * BaseGeneralActivity
 * A general activity with useful starting properties.
 * Here we also build a singleton of the SharedPreferences for use in the rest of the application.
 *
 * This was adapted from the AtomUI sample application.
 */

abstract class BaseGeneralActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(getLayoutId())

        if (supportFragmentManager.findFragmentByTag(loadTag()) == null) {
            loadFragment {
                supportFragmentManager.inTransaction {
                    add(R.id.fragment, it, loadTag())
                }
            }
        }
        // IMPORTANT: This will be used by the rest of the application.
        if(globalPreferences == null){
            globalPreferences = getSharedPreferencesForContext(this)
        }
        setToolBar()
    }

    open fun getLayoutId(): Int = R.layout.activity_generic

    abstract fun setToolBar()

    abstract fun loadFragment(callback: (f: Fragment) -> Unit)

    abstract fun loadTag(): String

    //If we implement the OnClickListener, then all child classes must override the onClick method.
    fun onBackButtonClick(view: View) {
        onBackPressed()
    }

    companion object {
        // A static reference useful for accessing these anywhere.
        var globalPreferences: SharedPreferences? = null;
    }
}