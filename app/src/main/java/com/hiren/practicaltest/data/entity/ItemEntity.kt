package com.hiren.practicaltest.data.entity

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.Gson

@Entity(tableName = "Item")
data class ItemEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var name: String,
    var description: String,
    var image: String,
    var amount: Double,
    @Ignore
    var isSelected: Boolean,
    @Ignore
    var quantity: Int
) : Cloneable {
    constructor() : this(0, "", "", "", 0.0, false, 0)

    public override fun clone(): Any {
        return super.clone()
    }

    fun equals(item: ItemEntity): Boolean {
        return this.id == item.id &&
                this.name.equals(item.name, ignoreCase = false) &&
                this.description.equals(item.description, ignoreCase = false) &&
                this.image.equals(item.image, ignoreCase = false) &&
                this.amount == item.amount &&
                this.isSelected == item.isSelected &&
                this.quantity == item.quantity
    }
}