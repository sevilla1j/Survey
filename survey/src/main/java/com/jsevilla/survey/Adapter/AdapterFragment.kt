package com.jsevilla.survey.Adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class AdapterFragment(
    fragmentManager: FragmentManager,
    private val myFragments: MutableList<Fragment>
) : FragmentPagerAdapter(fragmentManager) {

    override fun getItem(position: Int): Fragment {
        return myFragments[position]
    }

    override fun getCount(): Int {
        return this.myFragments.size
    }
}
