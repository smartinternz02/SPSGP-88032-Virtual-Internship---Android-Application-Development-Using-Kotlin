package com.example.project2mygrocery

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [GroceryItems::class], version =1)

abstract class GroceryDatabase : RoomDatabase() {

    abstract fun getGroceryDoa(): Dao

    companion object {
        @Volatile
        private var INSTANCE: GroceryDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) =
            INSTANCE ?: synchronized(LOCK) {

                INSTANCE ?: createDatabase(context).also {
                    INSTANCE = it
                }
            }

        private fun createDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                GroceryDatabase::class.java,
                "Grocery.db"
            ).build()

    }
}