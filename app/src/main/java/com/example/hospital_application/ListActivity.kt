package com.example.hospital_application

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout


class ListActivity : AppCompatActivity() {

    private var userDataList = mutableListOf<PersonData>() // List of User objects
    private lateinit var pager: ViewPager // creating object of ViewPager
    private lateinit var tab: TabLayout  // creating object of TabLayout


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.datadisplaylayout)
        pager = findViewById(R.id.viewPager)
        tab = findViewById(R.id.tab_tablayout)


        var adapter = TabsPage_Adapter(supportFragmentManager)
//        adapter.addFragment(FirstFragment(), "ToDo")
//        adapter.addFragment(SecondFragment(), "Already Done")
        pager.adapter = adapter
        tab.setupWithViewPager(pager)




    }





}