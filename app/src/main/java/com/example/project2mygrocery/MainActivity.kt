package com.example.project2mygrocery

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity(),GroceryAdapter.GroceryItemsClickInterface, SearchView.OnQueryTextListener{
    lateinit var addfab : FloatingActionButton
    lateinit var items: RecyclerView
    lateinit var list: List<GroceryItems>
    lateinit var groceryAdapter: GroceryAdapter
    lateinit var groceryViewModel: GroceryViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        items = findViewById(R.id.items)
        addfab = findViewById(R.id.fab)
        list = ArrayList<GroceryItems>()
        groceryAdapter = GroceryAdapter(list,this)
        items.layoutManager = LinearLayoutManager(this)
        items.adapter = groceryAdapter
        val groceryRepo = GroceryRepository(GroceryDatabase(this))
        val factory = GroceryViewModelFactory(groceryRepo)
        groceryViewModel =  ViewModelProvider(this,factory).get(GroceryViewModel::class.java)
        groceryViewModel.getItems().observe(this, Observer{
            groceryAdapter.list = it
            groceryAdapter.notifyDataSetChanged()
        })

        addfab.setOnClickListener{
            openDialog()
        }

    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.search_menu,menu)

        val search =  menu?.findItem(R.id.search)
        val searchView = search?.actionView as? SearchView
        searchView?.isSubmitButtonEnabled = true
        searchView?.setOnQueryTextListener(this)
        return true
    }
    override fun onQueryTextSubmit(query: String?): Boolean {
        if(query != null){
            searchDatabase(query)
        }
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        if(newText != null){
            searchDatabase(newText)
        }
        return true

    }
    private fun searchDatabase(query : String) {

        val searchQuery = "%$query%"

        groceryViewModel.searchDatabase(searchQuery).observe(this, Observer { list ->
            list?.let {
                groceryAdapter.setData(it)

            }
        })

    }

    fun openDialog(){
        val dialog = Dialog(this)
        dialog.setContentView(R.layout.add_dialog)
        val cancel = dialog.findViewById<Button>(R.id.cancel_button)
        val add = dialog.findViewById<Button>(R.id.add_button)
        val itemEdit = dialog.findViewById<EditText>(R.id.EditItemName)
        val itemPriceEdit = dialog.findViewById<EditText>(R.id.EditItemRate)
        val itemQuanEdit = dialog.findViewById<EditText>(R.id.EditItemQuantity)
        cancel.setOnClickListener {
            dialog.dismiss()
        }
        add.setOnClickListener{
            val itemName : String = itemEdit.text.toString()
            val itemPrice : String = itemPriceEdit.text.toString()
            val itemQuantity : String = itemQuanEdit.text.toString()
            val qty :Int = itemQuantity.toInt()
            val pr : Double = itemPrice.toDouble()
            if (itemName.isNotEmpty()&&itemPrice.isNotEmpty()&&itemQuantity.isNotEmpty()){

                val items = GroceryItems(itemName,qty,pr)
                groceryViewModel.insert(items)
                Toast.makeText(applicationContext,"Item Inserted", Toast.LENGTH_SHORT).show()
                groceryAdapter.notifyDataSetChanged()
                dialog.dismiss()
            }
            else{
                Toast.makeText(applicationContext,"PLease enter all fields", Toast.LENGTH_SHORT).show()
                groceryAdapter.notifyDataSetChanged()
                dialog.dismiss()
            }

        }
        dialog.show()


    }

    override fun onItemsClick(groceryItems: GroceryItems) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(R.string.dialogTitle)
        builder.setMessage(R.string.dialogMessage)

        builder.setNegativeButton("Yes") { dialogInterface, which ->

            groceryViewModel.delete(groceryItems)
            groceryAdapter.notifyDataSetChanged()
            Toast.makeText(applicationContext, "Item Deleted", Toast.LENGTH_LONG).show()
        }
        builder.setPositiveButton("No") { dialogInterface, which ->

        }
        val alertDialog: AlertDialog = builder.create()
        alertDialog.setCancelable(false)
        alertDialog.show()


    }



}





