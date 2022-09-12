package com.example.e_shoping.Adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.e_shoping.Activity.Productdetailed
import com.example.e_shoping.RoomDatabase.AppDatabase
import com.example.e_shoping.RoomDatabase.productModel
import com.example.e_shoping.databinding.LayoutCartItemBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class CartAdapter(val context: Context
,val list:List<productModel>):RecyclerView.Adapter<CartAdapter.CartViewHolder>(){


    inner class CartViewHolder(val binding:LayoutCartItemBinding):RecyclerView.ViewHolder(binding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup , viewType: Int): CartViewHolder {
       val binding=LayoutCartItemBinding.inflate(LayoutInflater.from(context),parent,false)
        return CartViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CartViewHolder , position: Int) {
        Glide.with(context).load(list[position].productImage).into(holder.binding.imageView4)
        holder.binding.name.text = list[position].productname
        holder.binding.price.text = list[position].productSp

        holder.itemView.setOnClickListener {   //navigate to product detail Activity
            val intent=Intent(context,Productdetailed::class.java)
            intent.putExtra("id",list[position].productId)
            context.startActivity(intent)
        }


        val dao = AppDatabase.getinstance(context).productdao()

        holder.binding.delete.setOnClickListener {

                GlobalScope.launch(Dispatchers.IO) {

                    dao.deleteProduct(
                        productModel(
                            list[position].productId ,
                            list[position].productname ,
                            list[position].productImage ,
                            list[position].productSp))
                }
        }
    }
    override fun getItemCount(): Int {
    return list.size
    }
}