package com.application.archelon.extensions

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity

/**
 * ActivityManagementExtensions
 * These extensions were added to easily manage fragment work flows in the application.
 */

fun AppCompatActivity.replaceFragment(fragment: Fragment, frameId: Int) {
    supportFragmentManager.inTransaction { replace(frameId, fragment) }
}

/**
 * This extension allows us to have a flow graph of fragments, and to easily find them
 * as we operate on the UI within a single Activity.
 */
fun FragmentActivity.replaceFragment(
    fragment: Fragment,
    frameId: Int,
    addFragmentToBackStack: Boolean = false,
    tag: String? = null
) {
    val fragTransaction = supportFragmentManager.beginTransaction()
    if (tag != null) {
        fragTransaction.replace(frameId, fragment, tag)
    } else {
        fragTransaction.replace(frameId, fragment)
    }
    // Add to the back stack with a tag, so we can find the fragment later if needed.
    if (addFragmentToBackStack) {
        fragTransaction.addToBackStack(tag)
    }
    fragTransaction.commit()
}