package com.hiren.practicaltest.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.hiren.practicaltest.data.entity.ItemEntity
import com.hiren.practicaltest.databinding.RawCartListBinding
import com.hiren.practicaltest.utils.BindingAdapters

class CartAdapter(private val listener: (ItemEntity, Int, Int) -> Unit) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var itemList = mutableListOf<ItemEntity>()

    fun setData(items: MutableList<ItemEntity>) {
        val newList: MutableList<ItemEntity> = getClonedList(items)
        val result = DiffUtil.calculateDiff(object : DiffUtil.Callback() {
            override fun getOldListSize(): Int {
                return itemList.size
            }

            override fun getNewListSize(): Int {
                return newList.size
            }

            override fun areItemsTheSame(
                oldItemPosition: Int,
                newItemPosition: Int
            ): Boolean {
                return itemList[oldItemPosition].id == newList[newItemPosition].id
            }

            override fun areContentsTheSame(
                oldItemPosition: Int,
                newItemPosition: Int
            ): Boolean {
                val newItem: ItemEntity = newList[newItemPosition]
                val oldItem: ItemEntity = itemList[oldItemPosition]
                return oldItem.equals(newItem)
            }
        })
        this.itemList = newList
        result.dispatchUpdatesTo(this)
    }

    private fun getClonedList(list: MutableList<ItemEntity>): MutableList<ItemEntity> {
        val clone: MutableList<ItemEntity> = ArrayList(list.size)
        for (item in list) {
            try {
                clone.add(item.clone() as ItemEntity)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        return clone
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ViewHolder).bind(itemList[position], listener)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            RawCartListBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    inner class ViewHolder(private val binding: RawCartListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(
            item: ItemEntity,
            listener: (ItemEntity, Int, Int) -> Unit
        ) {
            binding.item = item
            binding.companion = BindingAdapters.Companion
            binding.btnPlus.setOnClickListener {
                listener.invoke(item, adapterPosition, 1)
            }
            binding.btnMinus.setOnClickListener {
                listener.invoke(item, adapterPosition, 2)
            }
        }
    }

    fun getData(): List<ItemEntity> {
        return itemList
    }
}