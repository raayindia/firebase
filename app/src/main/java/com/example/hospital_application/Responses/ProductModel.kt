package com.example.hospital_application.Responses

data class ProductModel(
    var id: Int = 0,
    var name: String = "",
    var price: String = "",
    var designation: String = "",
    var email: String = "",
    var phone: String = "",
    var password: String = "",

    var isChecked: Boolean = false
)