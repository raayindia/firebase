package com.example.hospital_application

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

class EqualBottomBarFragment : Fragment() {

    private lateinit var inflatedView: View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        inflatedView = inflater.inflate(R.layout.fragment_equal_bottom_bar, container, false)
        return inflatedView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val fragList = ArrayList<ScreenSlidePageFragment>()
        fragList.add(ScreenSlidePageFragment.newInstance(getString(R.string.shop), R.color.blue_inactive))
        fragList.add(ScreenSlidePageFragment.newInstance(getString(R.string.photos), R.color.purple_inactive))
        fragList.add(ScreenSlidePageFragment.newInstance(getString(R.string.call), R.color.green_inactive))

//        val pagerAdapter = ScreenSlidePagerAdapter(fragList, childFragmentManager)
//        val viewPager = inflatedView.findViewById<ViewPager2>(R.id.view_pager)
//        viewPager.adapter = pagerAdapter
//
//        // Disable swipe gesture on the ViewPager
//        viewPager.isUserInputEnabled = false
//
//        val equalNavigationBar = inflatedView.findViewById<EqualNavigationBar>(R.id.equal_navigation_bar)
//        equalNavigationBar.setNavigationChangeListener { _, position ->
//            viewPager.setCurrentItem(position, true)
//        }

//        // Change the initial active element
//        val newInitialPosition = 2
//        equalNavigationBar.setCurrentActiveItem(newInitialPosition)
//        viewPager.setCurrentItem(newInitialPosition, false)
    }
}
