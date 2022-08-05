package com.hiren.practicaltest.viewmodel

import android.app.Application
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableDouble
import androidx.databinding.ObservableField
import androidx.databinding.ObservableInt
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hiren.practicaltest.data.AppDatabase
import com.hiren.practicaltest.data.entity.ItemEntity
import com.hiren.practicaltest.data.model.Cart
import com.hiren.practicaltest.data.repository.ItemRepository
import com.hiren.practicaltest.utils.BindingAdapters

class CartViewModel : ViewModel() {

    // To refresh list
    val obsRefreshItem: ObservableBoolean = ObservableBoolean(false)

    // UI Progress for Item View
    val obsItemContent: ObservableField<String> = ObservableField(BindingAdapters.PROGRESS)

    // UI Progress for Cart list View
    val obsCartContent: ObservableField<String> = ObservableField(BindingAdapters.PROGRESS)

    // Item list
    var itemList = mutableListOf<ItemEntity>()

    // Cart list
    var cartItem = Cart()

    // Cart Item list
    var cartItemList = mutableListOf<ItemEntity>()

    val obsTotalAmount: ObservableDouble = ObservableDouble(0.0)
    val obsTotalPayable: ObservableDouble = ObservableDouble(0.0)
    val obsChangeAmount: ObservableDouble = ObservableDouble(0.0)
    val obsReceivedAmount: ObservableDouble = ObservableDouble(0.0)
    val obsRemainAmount: ObservableDouble = ObservableDouble(0.0)
    val obsQuantity: ObservableInt = ObservableInt(0)
    var discountAmount = "0.00"
    var invoiceNumber = ""
    var invoiceDate = ""

    // Item Badge count
    val obsBadge: ObservableInt = ObservableInt(0)

    fun getAllItems(application: Application): LiveData<List<ItemEntity>> {
        val itemDao = AppDatabase.getDatabase(application, viewModelScope, application.resources)
            .itemDao()
        val repository = ItemRepository(itemDao)
        return repository.getAllItem()
    }

    fun clearCart() {
        obsBadge.set(0)
        obsQuantity.set(0)
        obsRemainAmount.set(0.0)
        obsReceivedAmount.set(0.0)
        obsChangeAmount.set(0.0)
        obsTotalPayable.set(0.0)
        obsTotalAmount.set(0.0)
        invoiceNumber = ""
        invoiceDate = ""
        cartItem = Cart()
        cartItemList.clear()

        for (item in itemList) {
            item.quantity = 0
            item.isSelected = false
        }
        obsRefreshItem.set(true)
    }
}