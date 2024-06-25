package com.mazenet.prabakaran.mazechit_customer.Retrofit

import com.mazenet.prabakaran.mazechit_customer.Model.*
import retrofit2.Call
import retrofit2.http.POST
import retrofit2.http.QueryMap

interface ICallService {

    @POST("customer-signin")
    fun sign_in(@QueryMap hashMap: HashMap<String,String>): Call<SigninModel>

    @POST("customer-signup-otp")
    fun sign_up(@QueryMap hashMap: HashMap<String,String>): Call<SignupModel>

    @POST("customer-update-password")
    fun update_password(@QueryMap hashMap: HashMap<String,String>): Call<successmsgmodel>

    @POST("customer-my-chits")
    fun get_mychits(@QueryMap hashMap: HashMap<String,String>): Call<ArrayList<MyChitsModel>>

    @POST("customer-account-copy")
    fun get_myacntcopy(@QueryMap hashMap: HashMap<String,String>): Call<AccountcopyModel>

    @POST("enrollment-wise-receipt-details")
    fun get_enrollwise_receipts(@QueryMap hashMap: HashMap<String,String>): Call<AccountcopyModel>

    @POST("customer-new-commenced-chits")
    fun get_newchits(@QueryMap hashMap: HashMap<String,String>): Call<NewChitModel>

    @POST("receipt-details")
    fun get_receipt_details(@QueryMap  hashMap: HashMap<String,String>):Call<ReceiptDetailsModel>

    @POST("customer-interest-message")
    fun send_interest(@QueryMap  hashMap: HashMap<String,String>):Call<successmsgmodel>
}