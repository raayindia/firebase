package com.example.hospital_application.Responses

data class GetAllHospitalDataResponse(
    val Hospitaldetails: List<Hospitaldetail>,
    val message: String,
    val status: String
)