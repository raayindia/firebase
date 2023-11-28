package com.example.hospital_application
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity


class EqualBottomBarActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_equal_bottom_bar)

        var fragment = supportFragmentManager.findFragmentById(R.id.frame) as EqualBottomBarFragment?
        if (fragment == null) {
            fragment = EqualBottomBarFragment()
            addFragment(fragment, R.id.frame)
        }
    }

    private fun addFragment(fragment: EqualBottomBarFragment, id: Int) {
        val ft = supportFragmentManager.beginTransaction()
        ft.add(id, fragment)
        ft.commit()
    }

}
