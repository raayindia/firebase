package com.example.hospital_application

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.MenuItem
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.AppBarConfiguration
import com.example.hospital_application.Responses.Checksubresponse
import com.example.hospital_application.fragments.HomeFragment
import com.example.hospital_application.fragments.ProfileFirstFragment
import com.gauravk.bubblenavigation.BubbleNavigationLinearView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.navigation.NavigationView
import de.hdodenhof.circleimageview.CircleImageView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.concurrent.TimeUnit

class HospReceptionistActivity : AppCompatActivity() , HomeFragment.BottomSheetStateListener {
    lateinit var receiveruser: TextView
    lateinit var navparent: RelativeLayout
    private lateinit var countdownTimer: CountDownTimer
    private lateinit var notificon: ImageView
    private lateinit var menuIcon: ImageView
    private lateinit var profileImageView: CircleImageView
    private lateinit var offerbtn: ImageView
    private lateinit var navController: NavController
    private lateinit var fragmentContainer: FrameLayout
    private lateinit var frameLayout: FrameLayout
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var bottomNavigationView: BubbleNavigationLinearView
    private lateinit var checksubscriptionresponse: Checksubresponse
    private lateinit var timertext: TextView
    private lateinit var textlist: TextView
    private lateinit var subsbtn: RelativeLayout
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hosp_receptionist)

        fragmentContainer = findViewById(R.id.recepnav_host_fragment)
        menuIcon = findViewById(R.id.recepcustomHamburgerIcon)
        notificon = findViewById(R.id.recepnotificon)
        drawerLayout = findViewById(R.id.recepdrawerLayout)
        offerbtn = findViewById(R.id.recepoffer)
        timertext = findViewById(R.id.receptimer)
        subsbtn= findViewById(R.id.recepsubsbtn2)
        textlist= findViewById(R.id.receptext)
        navparent = findViewById(R.id.recepnavhost_parent)
//        frameLayout = findViewById(R.id.frame_welcome)
        TimeCheckSubscription()
        val nav_menu = findViewById<NavigationView>(R.id.recepnavView)
        val headerView = nav_menu.getHeaderView(0)
        profileImageView = headerView.findViewById(R.id.profilepic)
        val username = intent.getStringExtra("username")
        drawerLayout = findViewById(R.id.recepdrawerLayout)


        notificon.setOnClickListener{
            startActivity(Intent(this, NotificationActivity::class.java))
        }
        menuIcon.setOnClickListener{
            drawerLayout.openDrawer(GravityCompat.END) // Use GravityCompat.END for a right-sided drawer
        }
        val sharedPreferences = getSharedPreferences("MyPrefs", Activity.MODE_PRIVATE)
        val profileImageUriString = sharedPreferences.getString("profile_image_uri", "")
        if (profileImageUriString != null) {
            if (profileImageUriString != null && profileImageUriString.isNotEmpty()) {
                val profileImageUri = Uri.parse(profileImageUriString)
                profileImageView.setImageURI(profileImageUri)
            } else {
                // Set a default image if no image is set
                profileImageView.setImageResource(R.drawable.logo)
            }
        }
        nav_menu.setNavigationItemSelectedListener { item: MenuItem ->
            // Handle item clicks here
            val itemId = item.itemId
            var fragment: Fragment? = null

            when (itemId) {
                R.id.Profile -> fragment = ProfileFirstFragment()
                R.id.Logout ->  { // Handle Logout option
                    val intent = Intent(this, OtpAccessActivity::class.java)
                    startActivity(intent)
                }
//                R.id.Settings -> fragment = HospitalSettingsFragment()
//                R.id.Logout -> fragment = BlankFragment3()
                // Add more cases for other items
            }

            if (fragment != null) {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.recepframe, fragment)
                    .commit()

                drawerLayout.closeDrawer(GravityCompat.END)
                true
            } else {
                false
            }
        }
        offerbtn.setOnClickListener {
            val bottomSheetDialog = BottomSheetDialog(this)

            // Inflate the bottom sheet layout
            val view = layoutInflater.inflate(R.layout.offer_bottom_layout, null)
            // Set the view for the BottomSheetDialog
            bottomSheetDialog.setContentView(view)
            // Show the BottomSheetDialog
            bottomSheetDialog.show()
        }
        subsbtn.setOnClickListener {
            val bottomSheetDialog = BottomSheetDialog(this)

            // Inflate the bottom sheet layout
            val view = layoutInflater.inflate(R.layout.offer_bottom_layout, null)
            // Set the view for the BottomSheetDialog
            bottomSheetDialog.setContentView(view)
            // Show the BottomSheetDialog
            bottomSheetDialog.show()
        }
        navController = Navigation.findNavController(this, R.id.recepnav_host_fragment)
        bottomNavigationView= findViewById(R.id.recepbottom_navigation)
        val appBarConfiguration = AppBarConfiguration.Builder(
            R.id.homeFragment,
            R.id.dashboardFragment,
            R.id.notificationsFragment

        ).build()
        textlist.setText("Add Staff List")
        bottomNavigationView.setNavigationChangeListener { _, position ->
            when (position) {

                0 -> {

                    navController.navigate(R.id.homeFragment)
                    val textTintList = resources.getColorStateList(R.color.blue)
                    textlist.setText("Add Staff List")
                    fragmentContainer.setBackgroundColor(getColor(R.color.blue))
                    fragmentContainer.setBackgroundColor(getColor(R.color.blue))
                    val selectedColor = ContextCompat.getColor(this, R.color.blue)
                    val unselectedColor = ContextCompat.getColor(this, R.color.disabled)
                    bottomNavigationView.setBackgroundResource(R.drawable.rounded_rect_white)
                    fragmentContainer.setBackgroundColor(getColor(R.color.blue))
                    navparent.setBackgroundColor(getColor(R.color.blue))

//                    setToolbarColor(R.color.blue)
                }
                1 -> {
                    navController.navigate( R.id.dashboardFragment)
                    textlist.setText("View Staff List")

                    val textTintList = resources.getColorStateList(R.color.purple_inactive)
                    fragmentContainer.setBackgroundColor(getColor(R.color.frag2))
                    fragmentContainer.setBackgroundColor(getColor(R.color.frag2))
                    val selectedColor = ContextCompat.getColor(this, R.color.purple_inactive)
                    val unselectedColor = ContextCompat.getColor(this, R.color.purple_inactive)
                    bottomNavigationView.setBackgroundResource(R.drawable.rounded_rect_white)
                    fragmentContainer.setBackgroundColor(getColor(R.color.frag2))
                    navparent.setBackgroundColor(getColor(R.color.frag2))
//                    setToolbarColor(R.color.blue)
                }

                2 -> {
                    navController.navigate( R.id.notificationsFragment)
                    textlist.setText("Graph")
                    val textTintList = resources.getColorStateList(R.color.green_inactive)
                    fragmentContainer.setBackgroundColor(getColor(R.color.frag3))
                    fragmentContainer.setBackgroundColor(getColor(R.color.frag3))
                    val selectedColor = ContextCompat.getColor(this, R.color.green_inactive)
                    val unselectedColor = ContextCompat.getColor(this, R.color.green_inactive)
                    bottomNavigationView.setBackgroundResource(R.drawable.rounded_rect_white)
                    fragmentContainer.setBackgroundColor(getColor(R.color.frag3))
                    navparent.setBackgroundColor(getColor(R.color.frag3))
//                    setToolbarColor(R.color.blue)

                }
            }
        }

        val toggle = ActionBarDrawerToggle(this, drawerLayout, R.string.nav_open, R.string.nav_close)
        toggle.isDrawerIndicatorEnabled = true
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()


    }






    private fun formatTime(millisUntilFinished: Long): String {
        val hours = TimeUnit.MILLISECONDS.toHours(millisUntilFinished) % 24
        val minutes = TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) % 60
        val seconds = TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) % 60

        return String.format("%02d:%02d:%02d", hours, minutes, seconds)
    }

    override fun onDestroy() {
        super.onDestroy()
        countdownTimer.cancel()
    }

    private fun TimeCheckSubscription() {

        val hospitalid = "45"
        val loginService = ApiClient.buildService(Api_Interface::class.java)
        val requestCall = loginService.checkSubscriptionInHome(hospitalid)
        requestCall.enqueue(object : Callback<Checksubresponse> {
            @SuppressLint("SuspiciousIndentation")
            override fun onResponse(
                call: Call<Checksubresponse>,
                response: Response<Checksubresponse>
            ) {
                if (response.isSuccessful) { // Status code between 200 to 299
                    val logincheckresponse: Checksubresponse? = response.body()
                    if (logincheckresponse != null) {
                        if (logincheckresponse.status == "200") {
                            subsbtn.visibility= View.GONE
                            offerbtn.visibility= View.VISIBLE
                            timertext.visibility= View.VISIBLE
                        } else if (logincheckresponse.status == "400"){
                            subsbtn.visibility= View.VISIBLE
                            offerbtn.visibility= View.GONE
                            timertext.visibility = View.GONE
                        }
                    }

                    if (logincheckresponse != null) {
                        timertext.setText("Day:" + " "+logincheckresponse.Days  + " "+ "Hr: "+logincheckresponse.hr + " ")

                    }
                    // Check for null before using logincheckresponse
                    logincheckresponse?.let {
                        // Handle the response here
                    }
                } else if (response.code() == 401) { // Unauthorized
                    Toast.makeText(
                        applicationContext,
                        getString(R.string.session_exp),
                        Toast.LENGTH_SHORT
                    ).show()
                } else { // Application-level failure
                    Toast.makeText(
                        applicationContext,
                        getString(R.string.server_error),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
            override fun onFailure(call: Call<Checksubresponse>, t: Throwable) {
                Toast.makeText(
                    applicationContext,
                    getString(R.string.session_exp),
                    Toast.LENGTH_SHORT
                ).show()
            }
        })

    }



    override fun onResume() {
        super.onResume()
        val hospitalid = "46"
        val loginService = ApiClient.buildService(Api_Interface::class.java)
        val requestCall = loginService.checkSubscriptionInHome(hospitalid)
        requestCall.enqueue(object : Callback<Checksubresponse> {
            @SuppressLint("SuspiciousIndentation")
            override fun onResponse(
                call: Call<Checksubresponse>,
                response: Response<Checksubresponse>
            ) {
                if (response.isSuccessful) { // Status code between 200 to 299
                    val logincheckresponse: Checksubresponse? = response.body()
                    // Check for null before using logincheckresponse
                    logincheckresponse?.let {
                        // Handle the response here
                    }
                } else if (response.code() == 401) { // Unauthorized
                    Toast.makeText(
                        applicationContext,
                        getString(R.string.session_exp),
                        Toast.LENGTH_SHORT
                    ).show()
                } else { // Application-level failure
                    Toast.makeText(
                        applicationContext,
                        getString(R.string.server_error),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<Checksubresponse>, t: Throwable) {
                Toast.makeText(
                    applicationContext,
                    getString(R.string.session_exp),
                    Toast.LENGTH_SHORT
                ).show()
            }
        })



    }

    override fun onBackPressed() {
        val builder = AlertDialog.Builder(this)
        builder.setMessage("Are you sure you want to exit?")
            .setCancelable(false)
            .setPositiveButton("Ok") { dialog, id ->

                val intent = Intent(this@HospReceptionistActivity, LoginActivity::class.java)
                startActivity(intent)
                finish();
            }
            .setNegativeButton(
                "Cancel"
            ) { dialog, id -> dialog.cancel() }
        builder.show()
    }

    override fun onBottomSheetStateChanged(isVisible: Boolean) {
        if (isVisible) {
            bottomNavigationView.visibility = View.GONE
        } else {
            bottomNavigationView.visibility = View.VISIBLE
        }
    }

}


