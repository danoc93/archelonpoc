package com.application.archelon.adapters

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.application.archelon.R
import com.application.archelon.extensions.inflate
import com.application.archelon.components.MenuListOptionViewHolder
import com.application.archelon.interfaces.OnNavigationItemClickListener
import com.application.archelon.models.NavigationItem

/**
 * MenuListOptionAdapter
 * Used to render individual options for the menu views, e.g. New Survey Menu, New Event Menu.
 */

class MenuListOptionAdapter(private var items: List<NavigationItem>) :
    RecyclerView.Adapter<MenuListOptionViewHolder>() {

    private var onNavigationItemClickListener: OnNavigationItemClickListener? = null

    constructor(
        items: List<NavigationItem>,
        onNavigationItemClickListener: OnNavigationItemClickListener
    ) : this(items) {
        this.onNavigationItemClickListener = onNavigationItemClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuListOptionViewHolder {
        return MenuListOptionViewHolder(
            parent.inflate(
                R.layout.adapter_menu_list_option,
                false
            )
        )
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: MenuListOptionViewHolder, position: Int) {
        holder.bind(items[position], onNavigationItemClickListener)
    }
}