package com.hiren.practicaltest.data

import android.content.Context
import android.content.res.Resources
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.hiren.practicaltest.R
import com.hiren.practicaltest.data.dao.ItemDao
import com.hiren.practicaltest.data.entity.ItemEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

@Database(
    entities = [ItemEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun itemDao(): ItemDao

    companion object {

        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(
            context: Context,
            scope: CoroutineScope,
            resources: Resources
        ): AppDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }

            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "PRACTICAL_TEST_APP_DB"
                )
                    .addCallback(ItemDbCallback(scope, resources))
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }


    private class ItemDbCallback(
        private val scope: CoroutineScope,
        private val resources: Resources
    ) : RoomDatabase.Callback() {

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                val scope = CoroutineScope(Dispatchers.IO + SupervisorJob())
                scope.launch {
                    val playerDao = database.itemDao()
                    prePopulateItems(playerDao)
                }
            }
        }

        private suspend fun prePopulateItems(itemDao: ItemDao) {
            val jsonString = resources.openRawResource(R.raw.items).bufferedReader().use {
                it.readText()
            }
            val typeToken = object : TypeToken<List<ItemEntity>>() {}.type
            val tennisPlayers = Gson().fromJson<List<ItemEntity>>(jsonString, typeToken)
            itemDao.insertItems(tennisPlayers)
        }
    }
}

