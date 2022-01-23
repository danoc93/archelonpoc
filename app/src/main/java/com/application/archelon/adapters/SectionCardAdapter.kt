package com.application.archelon.adapters

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.application.archelon.R
import com.application.archelon.components.SectionCardViewHolder
import com.application.archelon.interfaces.OnItemClickListener
import com.application.archelon.models.SimpleCategoryItem
import com.application.archelon.extensions.inflate

/**
 * SectionCardAdapter
 * Used to render individual section cards that can be used to build the options menu.
 */

class SectionCardAdapter(private var items: ArrayList<SimpleCategoryItem>) :
    RecyclerView.Adapter<SectionCardViewHolder>() {

    private var listener: OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SectionCardViewHolder {
        return SectionCardViewHolder(
            parent.inflate(R.layout.adapter_section_card, false)
        )
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: SectionCardViewHolder, position: Int) {
        listener?.let { holder.bind(items[position], it) }
    }

    fun setOnItemClickListener(listener: OnItemClickListener?) {
        this.listener = listener
    }
}