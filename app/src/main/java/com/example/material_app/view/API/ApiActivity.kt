package com.example.material_app.view.API

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.material_app.R
import com.example.material_app.databinding.ActivityApiBinding

class ApiActivity : AppCompatActivity() {
    lateinit var binding: ActivityApiBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.MyTheme)
        binding = ActivityApiBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.viewPager.adapter = ViewPagerAdapter(supportFragmentManager)
        binding.tabLayout.setupWithViewPager(binding.viewPager)


        setCustomTabs()
    }



    private fun setCustomTabs() {
        binding.tabLayout.getTabAt(0)?.customView =
            layoutInflater.inflate(R.layout.activity_api_tabitem_tom, null)
        binding.tabLayout.getTabAt(1)?.customView =
            layoutInflater.inflate(R.layout.activity_api_tabitem_kuki, null)
        binding.tabLayout.getTabAt(2)?.customView =
            layoutInflater.inflate(R.layout.activity_api_tabitem_murzik, null)
    }
}

