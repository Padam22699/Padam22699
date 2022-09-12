package com.example.e_shoping.Adapter

import android.content.ClipData
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivities
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.e_shoping.Activity.CategoryActivity
import com.example.e_shoping.Model.categoryModel
import com.example.e_shoping.R


class catagoryAdapter(var context: Context? , var list:ArrayList<categoryModel>): RecyclerView.Adapter<catagoryAdapter.CategoryViewHolde>(){

    inner class CategoryViewHolde(view: View):RecyclerView.ViewHolder(view){
         var image:ImageView=view.findViewById(R.id.imageView)
         var catName:TextView=view.findViewById(R.id.catText)
    }
    override fun onCreateViewHolder(parent: ViewGroup , viewType: Int): CategoryViewHolde {
       return  CategoryViewHolde(LayoutInflater.from(context).inflate(R.layout.catogery_item,parent,false))
    }

    override fun onBindViewHolder(holder: CategoryViewHolde , position: Int) {
      holder.catName.text = list[position].cat
        Glide.with(context!!).load(list[position].ima).into(holder.image)

        holder.itemView.setOnClickListener{
            val intent=Intent(context,CategoryActivity::class.java)
            intent.putExtra("cat",list[position].cat)
            context!!.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
       return  list.size
    }
}

