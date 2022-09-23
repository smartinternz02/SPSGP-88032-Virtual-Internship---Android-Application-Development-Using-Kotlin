package com.example.project2mygrocery

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.nio.file.Files.delete
import java.text.NumberFormat

class GroceryAdapter (
    var list : List<GroceryItems>,
    val groceryItemClickInterface:GroceryItemsClickInterface
): RecyclerView.Adapter<GroceryAdapter.GroceryViewHolder>(){


    inner class GroceryViewHolder(itemView : View): RecyclerView.ViewHolder(itemView){
        val name  = itemView.findViewById<TextView>(R.id.Itemname)
        val quan = itemView.findViewById<TextView>(R.id.Itemquan)
        val rate = itemView.findViewById<TextView>(R.id.Itemrate)
        val amount = itemView.findViewById<TextView>(R.id.Totalamt)
        val delete = itemView.findViewById<ImageView>(R.id.delete)



    }
    interface GroceryItemsClickInterface{
        fun onItemsClick(groceryItems: GroceryItems)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroceryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_list,parent,false)
        return GroceryViewHolder(view)
    }

    override fun onBindViewHolder(holder: GroceryViewHolder, position: Int) {


        holder.name.text = list.get(position).itemName

        holder.quan.text = "Qty: "+list.get(position).itemQuantity.toString()



        holder.rate.text = "Rs. "+list.get(position).itemPrice.toString()
        val itemTotal: Double = list.get(position).itemPrice * list.get(position).itemQuantity
        holder.amount.text = NumberFormat.getCurrencyInstance().format(itemTotal)
        holder.delete.setOnClickListener {
            groceryItemClickInterface.onItemsClick(list.get(position))
        }


    }

    override fun getItemCount(): Int {
        return list.size
    }
    fun setData(newList : List<GroceryItems>){
        list = newList
        notifyDataSetChanged()
    }
}

