package com.example.material_app.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.material_app.R
import com.example.material_app.view.picture.PictureOfTheDayFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if(savedInstanceState==null){
            supportFragmentManager.beginTransaction().replace(R.id.container,PictureOfTheDayFragment.newInstance()).commit()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }
}