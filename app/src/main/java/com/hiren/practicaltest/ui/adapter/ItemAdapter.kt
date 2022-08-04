package com.hiren.practicaltest.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hiren.practicaltest.databinding.RawItemListBinding

class ItemAdapter(private val listener: (String, Int) -> Unit) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var itemList = mutableListOf<String>()

    fun setData(items: MutableList<String>) {
        itemList = items
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ViewHolder).bind(itemList[position], listener, position)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            RawItemListBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    inner class ViewHolder(val binding: RawItemListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(
            item: String,
            listener: (String, Int) -> Unit,
            position: Int
        ) {

        }
    }

    fun getData(): List<String> {
        return itemList
    }
}