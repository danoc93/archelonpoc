package com.application.archelon.components

import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.application.archelon.interfaces.OnItemClickListener
import com.application.archelon.models.SimpleCategoryItem
import kotlinx.android.synthetic.main.adapter_section_card.view.*

/**
 * SectionCardViewHolder
 * This is manages the view for a section card.
 */

class SectionCardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
    View.OnClickListener {

    private var listener: OnItemClickListener? = null
    private var item: SimpleCategoryItem? = null

    init {
        itemView.setOnClickListener(this)
    }

    fun bind(item: SimpleCategoryItem, listener: OnItemClickListener) {
        this.item = item
        this.listener = listener

        // Decide the state of the card based on the properties available.
        itemView.tvItemTitle.text = itemView.context.getString(item.title)
        if (item.subtitle != null) {
            itemView.tvItemSubtitle.text = itemView.context.getString(item.subtitle)
        } else {
            itemView.tvItemSubtitle.visibility = View.INVISIBLE
        }
        itemView.ivItemBg.setImageDrawable(item.backgroundPhotoDrawableRes?.let {
            ContextCompat.getDrawable(
                itemView.context,
                it
            )
        })
    }

    override fun onClick(v: View?) {
        when (v) {
            itemView -> {
                item?.let { listener?.onItemClick(it) }
            }
        }
    }
}