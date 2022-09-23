package com.example.project2mygrocery

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "grocery_items")
data class GroceryItems (


    @ColumnInfo(name = "Name")
    var itemName: String,

    @ColumnInfo(name = "Quantity")
    var itemQuantity: Int,

    @ColumnInfo(name = "Price")
    var itemPrice : Double
){
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null
}
