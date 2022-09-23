package com.example.project2mygrocery

import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.room.Dao

@Dao
interface Dao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: GroceryItems)

    @Delete
    suspend fun delete(item : GroceryItems)


    @Query("SELECT * FROM grocery_items ")
    fun getItems(): LiveData<List<GroceryItems>>
    @Query("SELECT *FROM grocery_items WHERE Name LIKE :searchQuery")
    fun searchDatabase(searchQuery: String) : LiveData<List<GroceryItems>>





}