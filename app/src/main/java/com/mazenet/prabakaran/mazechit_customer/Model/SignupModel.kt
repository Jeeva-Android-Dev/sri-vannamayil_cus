package com.mazenet.prabakaran.mazechit_customer.Model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName



class SignupModel {
    @SerializedName("status")
    @Expose
    private var status: String? = null
    @SerializedName("msg")
    @Expose
    private var msg: String? = null
    @SerializedName("customer_primary_id")
    @Expose
    private var customerPrimaryId: String? = null
    @SerializedName("otp")
    @Expose
    private var otp: String? = null
    @SerializedName("customer_name")
    @Expose
    private var customerName: String? = null

    /**
     * No args constructor for use in serialization
     *
     */


    /**
     *
     * @param customerName
     * @param status
     * @param customerPrimaryId
     * @param otp
     * @param msg
     */
    fun SignupModel(status: String, msg: String, customerPrimaryId: String, otp: String, customerName: String){

        this.status = status
        this.msg = msg
        this.customerPrimaryId = customerPrimaryId
        this.otp = otp
        this.customerName = customerName
    }

    fun getStatus(): String? {
        return status
    }

    fun setStatus(status: String) {
        this.status = status
    }

    fun getMsg(): String? {
        return msg
    }

    fun setMsg(msg: String) {
        this.msg = msg
    }

    fun getCustomerPrimaryId(): String? {
        return customerPrimaryId
    }

    fun setCustomerPrimaryId(customerPrimaryId: String) {
        this.customerPrimaryId = customerPrimaryId
    }

    fun getOtp(): String? {
        return otp
    }

    fun setOtp(otp: String) {
        this.otp = otp
    }

    fun getCustomerName(): String? {
        return customerName
    }

    fun setCustomerName(customerName: String) {
        this.customerName = customerName
    }
}