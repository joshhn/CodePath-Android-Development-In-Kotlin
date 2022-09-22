package com.joshhn.wishlist

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.joshhn.wishlist.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private var binding: ActivityMainBinding? = null
    private lateinit var mWishList: MutableList<Wish>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        mWishList = mutableListOf()

        val adapter = WishListAdapter(mWishList)
        binding?.rvWishList?.adapter = adapter
        binding?.rvWishList?.layoutManager = LinearLayoutManager(this)

        //Handle Item Click
        adapter.setOnClickListener(object: WishListAdapter.OnItemClickListener{
            override fun onItemClick(position: Int) {
                try {
                    val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(mWishList[position].url))
                    ContextCompat.startActivity(this@MainActivity, browserIntent, null)
                } catch (e: ActivityNotFoundException) {
                    Toast.makeText(this@MainActivity, "Invalid URL for " + mWishList[position].url, Toast.LENGTH_LONG).show()
                }
            }
        })
        //Handle Long Pressing Click
        adapter.setOnLongClickListener(object: WishListAdapter.OnItemLongClickListener{
            override fun onItemLongClick(position: Int) {
                mWishList.removeAt(position)
                adapter.notifyDataSetChanged()
            }
        })



        binding?.btnSubmit?.setOnClickListener {
            val inputName = binding?.etName?.text.toString()
            val inputPrice = binding?.etPrice?.text.toString().toDouble()
            val inputUrl = binding?.etUrl?.text.toString()

            hideKeyboard()

            if(inputName.isNotEmpty() && inputUrl.isNotEmpty() && isValidPrice(inputPrice)){
                val newWish = Wish(inputName,inputPrice,inputUrl)
                mWishList.add(newWish)
                adapter.notifyDataSetChanged()
            }else{
                Toast.makeText(this@MainActivity,"Please add valid input", Toast.LENGTH_LONG).show()
            }
            binding?.etName?.setText("")
            binding?.etPrice?.setText("")
            binding?.etUrl?.setText("")
        }
    }

    private fun isValidPrice(inputPrice: Double): Boolean {
        if(inputPrice < 0 || inputPrice.toString().isEmpty()){
            return false
        }
        return true
    }

    private fun hideKeyboard() {
        val view = this.currentFocus
        if (view != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }
}