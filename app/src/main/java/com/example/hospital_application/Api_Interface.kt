package com.example.hospital_application

import com.example.hospital_application.Responses.AddstafResponse
import com.example.hospital_application.Responses.AdminLoginResponse
import com.example.hospital_application.Responses.Checksubresponse
import com.example.hospital_application.Responses.GetAllHospitalDataResponse
import com.example.hospital_application.Responses.LogincheckResponse
import com.example.hospital_application.Responses.LoginwithPwConformationResp
import com.example.hospital_application.Responses.Loginwithrole_Response
import com.example.hospital_application.Responses.RegisterResponse
import com.example.hospital_application.Responses.RegisterverificationResponse
import com.example.hospital_application.Responses.UpdatehospitalStaf_Response
import com.example.hospital_application.Responses.ViewStaffResponse
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.QueryMap

interface Api_Interface {
//
//        @GET("leads")
//        fun getLeads(@Query("authtoken") authToken: String): Call<Customer_ResponseItem>

//    @FormUrlEncoded
//    @POST("addhospitaldetails")
//    fun registerdetails(
//        @Field("Hadmincontac") Hadmincontac: String,
//        @Field("Hemail") Hemail: String,
//        @Field("Hname")Hname: String,
//        @Field("Hadminname")Hadminname: String,
//
//    ): Call<RegisterResponse>
@FormUrlEncoded
@POST("addhospital")
fun registerdetails(
    @Field("hospitalname") hospitalname: String,
    @Field("hospitalmobile") hospitalmobile: String,
    @Field("hospitalemail")hospitalemail: String,
    @Field("hospitaladminname")hospitaladminname: String,

    ): Call<RegisterResponse>

    @FormUrlEncoded
    @POST("hospitaladminlogin?")
    fun adminloginmobilenumber(
        @Field("Hadmincontac") Hadmincontac: String,

        ): Call<AdminLoginResponse>

    @FormUrlEncoded
    @POST("addhospitalstaffdetails")
    fun fromadminaddstaf(
        @Field("Hospital_id") Hospital_id: String,
        @Field("Staff_phoneno") Staff_phoneno: String,
        @Field("Staff_email") Staff_email: String,
        @Field("Staff_name") Staff_name: String,
        @Field("Staff_role") Staff_role: String
        ): Call<AddstafResponse>

    @FormUrlEncoded
    @POST("updatehospitalstaffdetails")
    fun updatehospitalstafdetails(
        @Field("Hospital_id") Hospital_id: String,
        @Field("Staff_phoneno") Staff_phoneno: String,
        @Field("Staff_email") Staff_email: String,
        @Field("Staff_name") Staff_name: String,
        @Field("Staff_role") Staff_role: String
    ): Call<UpdatehospitalStaf_Response>

    @GET("api/getallhospital")
    fun getAllHospitals(): Call<GetAllHospitalDataResponse>


    @FormUrlEncoded
    @POST("checkvalidation")
    fun registerverification(
        @Field("hospitalname") hospitalname: String,
        @Field("hospitalmobile") hospitalmobile: String,
        @Field("hospitalemail")hospitalemail: String,
        @Field("hospitaladminname")hospitaladminname: String,
    ): Call<RegisterverificationResponse>
    @FormUrlEncoded
    @POST("loginwithpassword")
    fun checkregmobilenumber(
        @Field("mobileno") mobileno: String,
        @Field("password") password: String


    ): Call<LoginwithPwConformationResp>



    @FormUrlEncoded
    @POST("addstaff")
    fun addstafdetails(
        @Field("hospitalid") hospitalid: String,
        @Field("hospitalname") hospitalname: String,
        @Field("staffname") hospitalmobile: String,
        @Field("staffdesignation")staffdesignation: String,
        @Field("staffphoneno")staffphoneno: String,
        @Field("staffemail")staffemail: String

    ): Call<AddstafResponse>


    @FormUrlEncoded
    @POST("viewstaff")
    fun viewadedstafdetails(
        @Field("hospitalid") hospitalid: String,

    ): Call<AddstafResponse>
    @FormUrlEncoded
    @POST("checklogintype")
    fun checklogintype(
        @Field("mobileno") mobileno: String,

        ): Call<LogincheckResponse>
    @FormUrlEncoded
    @POST("checksubscription")
    fun checkSubscriptionInHome(
        @Field("hospitalid") hospitalid: String,

        ): Call<Checksubresponse>

    @FormUrlEncoded
    @POST("viewstaff")
    fun viewStaffDetails(
        @Field("hospitalid") hospitalid: String,

        ): Call<ViewStaffResponse>
//    @FormUrlEncoded
//    @POST("getusers")
//    fun loginthroughmobilenumber(
//        @Field("MobileNo") MobileNo: String,
//        ): Call<MobilenumberResponse>
//
//    @GET("AllLogins")
//    fun getAllLogins(): Call<List<AllloginResponse>>
@FormUrlEncoded
    @POST("deletestaff")
    fun deleteItem(@Field("hid") hid: String, @Field("sid") sid: String): Call<ViewStaffResponse>

    @FormUrlEncoded
    @POST("updatestaff")
    fun updatesafedetailsfromhome(
        @Field("staffid") staffid: String,
        @Field("hospitalid") hospitalid: String,
        @Field("staffname") staffname: String,
        @Field("staffdesignation") staffdesignation: String,
        @Field("staffphoneno") staffphoneno: String
    ): Call<AddstafResponse>
    @FormUrlEncoded
    @POST("deletestaff")
    fun deletestaff(
        @Field("hid") hospitalid: String,
        @QueryMap queryMap: Map<String, String>
    ): Call<AddstafResponse>
    @FormUrlEncoded
    @POST("checkmobilenumber")
    fun checkreg_mobilenumber(
        @Field("hospitalmobile") hospitalmobile: String,

        ): Call<Loginwithrole_Response>

}