package com.example.hospital_application.Responses

data class Customer_ResponseItem(
    val _links: Links,
    val avatar_url: String,
    val billing: Billing,
    val date_created: String,
    val date_created_gmt: String,
    val date_modified: String,
    val date_modified_gmt: String,
    val email: String,
    val first_name: String,
    val id: Int,
    val is_paying_customer: Boolean,
    val last_name: String,
    val meta_data: List<MetaData>,
    val role: String,
    val shipping: Shipping,
    val username: String
)