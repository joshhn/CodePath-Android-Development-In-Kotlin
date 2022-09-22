package com.joshhn.wishlist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class WishListAdapter(private val wishList: MutableList<Wish>): RecyclerView.Adapter<WishListAdapter.ViewHolder>(){

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val tvName: TextView
        val tvPrice: TextView
        val tvUrl: TextView

        init {
            tvName = itemView.findViewById(R.id.tvName)
            tvPrice = itemView.findViewById(R.id.tvPrice)
            tvUrl = itemView.findViewById(R.id.tvUrl)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val wishListView = inflater.inflate(R.layout.wish_item,parent,false)

        return ViewHolder(wishListView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val wish = wishList[position]
        holder.tvName.text = wish.name
        holder.tvPrice.text = wish.price.toString()
        holder.tvUrl.text = wish.url
    }

    override fun getItemCount(): Int {
        return wishList.size
    }


}
