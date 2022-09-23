package com.example.project2mygrocery

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class GroceryViewModel (private val repository: GroceryRepository): ViewModel(){
    fun insert(items: GroceryItems) = GlobalScope.launch{
        repository.insert(items)
    }
    fun delete(items: GroceryItems) = GlobalScope.launch {
        repository.delete(items)
    }

    fun searchDatabase(searchQuery: String): LiveData<List<GroceryItems>> {
        return repository.searchDatabase(searchQuery)
    }


    fun getItems() = repository.getAllItems()
}
