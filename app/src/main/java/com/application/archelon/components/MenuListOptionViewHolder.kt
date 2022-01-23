package com.application.archelon.components

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.application.archelon.interfaces.OnNavigationItemClickListener
import com.application.archelon.models.NavigationItem
import kotlinx.android.synthetic.main.adapter_menu_list_option.view.*

/**
 * MenuListOptionViewHolder
 * This is manages the view for a menu list option.
 */

class MenuListOptionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

    private var view: View = itemView
    private lateinit var item: NavigationItem
    private var onNavigationItemClickListener: OnNavigationItemClickListener? = null

    init {
        itemView.setOnClickListener(this)
    }

    fun bind(item: NavigationItem, onNavigationItemClickListener: OnNavigationItemClickListener?) {
        this.item = item
        this.onNavigationItemClickListener = onNavigationItemClickListener
        view.tvItemTitle.text = item.title
    }

    override fun onClick(v: View) {
        this.onNavigationItemClickListener?.onNavigationItemClick(v, item)
    }
}