package com.example.material_app.view.API

import KukiFragment
import MurzikFragment
import TomFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

private const val TOM_FRAGMENT = 0
private const val KUKI_FRAGMENT = 1
private const val MURZIK_FRAGMENT = 2

class ViewPagerAdapter(private  val fm: FragmentManager): FragmentStatePagerAdapter(fm) {

    private val fragments = arrayOf(TomFragment(),KukiFragment(),MurzikFragment())

    override fun getCount()= fragments.size

    override fun getItem(position: Int): Fragment {
        return when(position){
            TOM_FRAGMENT ->fragments[TOM_FRAGMENT]
            KUKI_FRAGMENT ->fragments[KUKI_FRAGMENT ]
            MURZIK_FRAGMENT ->fragments[MURZIK_FRAGMENT]
            else -> fragments[TOM_FRAGMENT]
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when(position){
            TOM_FRAGMENT ->"TOM"
            KUKI_FRAGMENT ->"KUKI"
            MURZIK_FRAGMENT ->"MURZIK"
            else -> "EARTH"
        }
    }
}