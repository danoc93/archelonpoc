package com.application.archelon.models

/**
 * SimpleItem
 * Very light model to maintain data for UI elements that are not sourced from the API.
 * For example weather options, etc.
 */

data class SimpleItem(
    val id: Int,

    val name: String
)