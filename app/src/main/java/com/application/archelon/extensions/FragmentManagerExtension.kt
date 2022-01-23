package com.application.archelon.extensions

import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction

/**
 * FragmentManagerExtensions
 * These extensions were added to easily manage fragment work flows in the application.
 *
 * This was adapted from the AtomUI sample app.
 */

inline fun FragmentManager.inTransaction(func: FragmentTransaction.() -> Unit) {
    val fragmentTransaction = beginTransaction()
    fragmentTransaction.func()
    fragmentTransaction.commit()
}
