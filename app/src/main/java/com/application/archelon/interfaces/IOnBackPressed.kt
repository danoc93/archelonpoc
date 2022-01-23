package com.application.archelon.interfaces

/**
 * IOnBackPressed
 * Useful to implement custom "back-pressed" behaviours in fragments, which unlike activities do not
 * understand these events.
 */

interface IOnBackPressed {
    fun onBackPressed()
}