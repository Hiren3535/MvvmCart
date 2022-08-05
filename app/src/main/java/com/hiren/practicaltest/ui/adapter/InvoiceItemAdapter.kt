package com.hiren.practicaltest.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hiren.practicaltest.data.entity.ItemEntity
import com.hiren.practicaltest.databinding.RawInvoiceItemListBinding
import com.hiren.practicaltest.utils.BindingAdapters

class InvoiceItemAdapter :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var itemList = mutableListOf<ItemEntity>()

    fun setData(items: MutableList<ItemEntity>) {
        itemList = items
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ViewHolder).bind(itemList[position])
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            RawInvoiceItemListBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    inner class ViewHolder(private val binding: RawInvoiceItemListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(
            item: ItemEntity
        ) {
            binding.item = item
            binding.companion = BindingAdapters.Companion
        }
    }
}