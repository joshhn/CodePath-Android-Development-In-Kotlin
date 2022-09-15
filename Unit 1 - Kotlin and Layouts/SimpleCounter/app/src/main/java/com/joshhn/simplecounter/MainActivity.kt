package com.joshhn.simplecounter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat

class MainActivity : AppCompatActivity() {

    var counter = 0
    private var addedAmount = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        hideSystemBars()

        val btnUpgrade = findViewById<Button>(R.id.btn_upgrade)
        val ibTouch = findViewById<ImageButton>(R.id.ib_touch)
        val tvCounter = findViewById<TextView>(R.id.tv_counter)

        ibTouch.setOnClickListener {
            //Toast.makeText(it.context, "Clicked Button!", Toast.LENGTH_SHORT).show()
            counter += addedAmount
            tvCounter.text = counter.toString()

            if (counter >= 100){
                btnUpgrade.visibility = View.VISIBLE
                btnUpgrade.text = "Upgrade to add ${addedAmount + 1}"
            }else{
                btnUpgrade.visibility = View.INVISIBLE
            }
        }

        btnUpgrade.setOnClickListener {
            addedAmount++
            btnUpgrade.visibility = View.INVISIBLE
            counter -= 100
            tvCounter.text = counter.toString()
        }


    }

    private fun hideSystemBars() {
        val windowInsetsController =
            ViewCompat.getWindowInsetsController(window.decorView) ?: return
        // Configure the behavior of the hidden system bars
        windowInsetsController.systemBarsBehavior =
            WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        // Hide both the status bar and the navigation bar
        windowInsetsController.hide(WindowInsetsCompat.Type.systemBars())
    }
}