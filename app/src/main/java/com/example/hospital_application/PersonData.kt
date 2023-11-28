package com.example.hospital_application

data class PersonData(
val name: String,
val mobile: String,
val doctorname: String,
val joinDate: String,
val dischargeDate: String,

// Add a no-argument constructor

){
    constructor() : this("", "","","","")
}
