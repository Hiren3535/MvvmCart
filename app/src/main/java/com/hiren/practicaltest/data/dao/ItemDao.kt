package com.hiren.practicaltest.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.hiren.practicaltest.data.entity.ItemEntity

@Dao
interface ItemDao {

    /**
     * Insert bulk item in table
     * @param list = list of items
     * */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertItems(list: List<ItemEntity>)

    /**
     * Fetching list of items
     * @return Livedata ItemEntity
     * */
    @Query("select * from ITEM order by name desc")
    fun getAllNotes(): LiveData<List<ItemEntity>>

    /**
     * Fetch total item stored in db
     * @return Total numbers of item
     */
    @Query("SELECT COUNT() FROM ITEM")
    fun getItemCount(): Int
}