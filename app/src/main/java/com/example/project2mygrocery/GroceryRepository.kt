package com.example.project2mygrocery

import androidx.lifecycle.LiveData

class GroceryRepository(private val db : GroceryDatabase) {
    suspend fun insert(items: GroceryItems) = db.getGroceryDoa().insert(items)
    suspend fun delete(items: GroceryItems) = db.getGroceryDoa().delete(items)

    fun searchDatabase(searchQuery: String): LiveData<List<GroceryItems>> {
        return db.getGroceryDoa().searchDatabase(searchQuery)
    }



    fun getAllItems() = db.getGroceryDoa().getItems()
}