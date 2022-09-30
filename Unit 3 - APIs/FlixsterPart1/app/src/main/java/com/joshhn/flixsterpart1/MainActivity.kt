package com.joshhn.flixsterpart1

import android.content.Context
import android.content.pm.ActivityInfo
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Surface
import android.view.WindowManager
import com.joshhn.flixsterpart1.R.id

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val orientation = getScreenOrientation(this@MainActivity)
        val supportFragmentManager = supportFragmentManager
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        if(orientation == "SCREEN_ORIENTATION_LANDSCAPE" || orientation == "SCREEN_ORIENTATION_REVERSE_LANDSCAPE"){
            fragmentTransaction.replace(id.content, NowPlayingFilmFragment(), null).commit()
        }else{
            fragmentTransaction.replace(id.content, NowPlayingFilmFragment(), null).commit()
        }
    }

    fun getScreenOrientation(context: Context): String {
        val screenOrientation = (context.getSystemService(Context.WINDOW_SERVICE) as WindowManager).defaultDisplay.orientation
        when (screenOrientation) {
            Surface.ROTATION_0 -> return "SCREEN_ORIENTATION_PORTRAIT"
            Surface.ROTATION_90 -> return "SCREEN_ORIENTATION_LANDSCAPE"
            Surface.ROTATION_180 -> return "SCREEN_ORIENTATION_REVERSE_PORTRAIT"
            else -> return "SCREEN_ORIENTATION_REVERSE_LANDSCAPE"
        }
    }

}