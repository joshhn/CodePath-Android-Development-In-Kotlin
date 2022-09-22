package com.joshhn.wishlist

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
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

        binding?.btnSubmit?.setOnClickListener {
            val inputName = binding?.etName?.text.toString()
            val inputPrice = binding?.etPrice?.text.toString().toDouble()
            val inputUrl = binding?.etUrl?.text.toString()

            hideKeyboard()
            binding?.etName?.setText("")
            binding?.etPrice?.setText("")
            binding?.etUrl?.setText("")

            if(isValid(inputName) && isValidPrice(inputPrice) && isValid(inputUrl)){
                val newWish = Wish(inputName,inputPrice,inputUrl)
                mWishList.add(newWish)
                adapter.notifyDataSetChanged()
            }else{
                Toast.makeText(this@MainActivity,"Please add valid input", Toast.LENGTH_LONG).show()
            }
        }


    }

    private fun isValidPrice(inputPrice: Double): Boolean {
        return true
    }

    private fun isValid(inputName: String): Boolean {
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