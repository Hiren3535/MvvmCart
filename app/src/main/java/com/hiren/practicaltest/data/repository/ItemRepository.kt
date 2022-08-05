package com.hiren.practicaltest.data.repository

import androidx.lifecycle.LiveData
import com.hiren.practicaltest.data.dao.ItemDao
import com.hiren.practicaltest.data.entity.ItemEntity

class ItemRepository(private val itemDao: ItemDao) {

    fun getAllItem(): LiveData<List<ItemEntity>> {
        return itemDao.getAllNotes()
    }


}