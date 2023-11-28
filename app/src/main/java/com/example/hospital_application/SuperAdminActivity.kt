package com.example.hospital_application

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class SuperAdminActivity : AppCompatActivity() {
    private val BASE_URL = "https://limratechnologies.tech/api/"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_super_admin)

//        val retrofit = Retrofit.Builder()
//            .baseUrl(BASE_URL)
//            .addConverterFactory(GsonConverterFactory.create())
//            .build()
//
//        val apiService = ApiClient.buildService(Api_Interface::class.java)
//
//        // Make the GET request
//        val call: Call<List<AllloginResponse>> = apiService.getAllLogins()
//        call.enqueue(object : Callback<List<AllloginResponse>> {
//            override fun onResponse(call: Call<List<AllloginResponse>>, response: Response<List<AllloginResponse>>) {
//                if (response.isSuccessful) {
////                    val loginDataList = response.body()
////                    val recyclerView = findViewById<RecyclerView>(R.id.rvliglist)
////                    recyclerView.layoutManager = LinearLayoutManager(this)
////                    val adapter = LoginDataAdapter(loginDataList) // Create a custom adapter
////                    recyclerView.adapter = adapter
//                } else {
//                    // Handle HTTP error
//                }
//            }
//
//            override fun onFailure(call: Call<List<AllloginResponse>>, t: Throwable) {
//                // Handle network or request failure
//            }
//        })
    }




    }
