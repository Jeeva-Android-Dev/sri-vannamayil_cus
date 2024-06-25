package com.mazenet.prabakaran.mazechit_customer.Model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName



class SigninModel {
    @SerializedName("status")
    @Expose
    private var status: String? = null
    @SerializedName("msg")
    @Expose
    private var msg: String? = null
    @SerializedName("customer_primary_id")
    @Expose
    private var customerPrimaryId: String? = null
    @SerializedName("customer_name")
    @Expose
    private var customerName: String? = null
    @SerializedName("tenant_id")
    @Expose
    private var tenantId: String? = null
    @SerializedName("tenant_name")
    @Expose
    private var tenantName: String? = null
    @SerializedName("tenant_address_1")
    @Expose
    private var tenantAddress1: String? = null
    @SerializedName("tenant_address_2")
    @Expose
    private var tenantAddress2: String? = null
    @SerializedName("state_name")
    @Expose
    private var stateName: String? = null
    @SerializedName("district_name")
    @Expose
    private var districtName: String? = null
    @SerializedName("city_name")
    @Expose
    private var cityName: String? = null
    @SerializedName("pincode")
    @Expose
    private var pincode: String? = null
    @SerializedName("mobile_no")
    @Expose
    private var mobileNo: String? = null
    @SerializedName("phone_no")
    @Expose
    private var phoneNo: String? = null
    @SerializedName("branch_id")
    @Expose
    private var branchId: String? = null
    @SerializedName("branch_name")
    @Expose
    private var branchName: String? = null
    @SerializedName("profile_photo")
    @Expose
    private var profile_photo: String? = null
    @SerializedName("customer_code")
    @Expose
    private var customer_code: String? = null

    /**
     * No args constructor for use in serialization
     *
     */

    /**
     *
     * @param customerName
     * @param tenantId
     * @param status
     * @param cityName
     * @param msg
     * @param tenantName
     * @param phoneNo
     * @param pincode
     * @param tenantAddress1
     * @param districtName
     * @param branchId
     * @param stateName
     * @param branchName
     * @param customerPrimaryId
     * @param tenantAddress2
     * @param mobileNo
     */
    fun SigninModel(
        status: String,
        msg: String,
        customerPrimaryId: String,
        customerName: String,
        tenantId: String,
        tenantName: String,
        tenantAddress1: String,
        tenantAddress2: String,
        stateName: String,
        districtName: String,
        cityName: String,
        pincode: String,
        mobileNo: String,
        phoneNo: String,
        branchId: String,
        branchName: String,
        profile_photo:String,
        customer_code:String
    ){

        this.status = status
        this.msg = msg
        this.customerPrimaryId = customerPrimaryId
        this.customerName = customerName
        this.tenantId = tenantId
        this.tenantName = tenantName
        this.tenantAddress1 = tenantAddress1
        this.tenantAddress2 = tenantAddress2
        this.stateName = stateName
        this.districtName = districtName
        this.cityName = cityName
        this.pincode = pincode
        this.mobileNo = mobileNo
        this.phoneNo = phoneNo
        this.branchId = branchId
        this.branchName = branchName
        this.profile_photo = profile_photo
        this.customer_code = customer_code
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

    fun getCustomerName(): String? {
        return customerName
    }

    fun setCustomerName(customerName: String) {
        this.customerName = customerName
    }

    fun getTenantId(): String? {
        return tenantId
    }

    fun setTenantId(tenantId: String) {
        this.tenantId = tenantId
    }

    fun getTenantName(): String? {
        return tenantName
    }

    fun setTenantName(tenantName: String) {
        this.tenantName = tenantName
    }

    fun getTenantAddress1(): String? {
        return tenantAddress1
    }

    fun setTenantAddress1(tenantAddress1: String) {
        this.tenantAddress1 = tenantAddress1
    }

    fun getTenantAddress2(): String? {
        return tenantAddress2
    }

    fun setTenantAddress2(tenantAddress2: String) {
        this.tenantAddress2 = tenantAddress2
    }

    fun getStateName(): String? {
        return stateName
    }

    fun setStateName(stateName: String) {
        this.stateName = stateName
    }

    fun getDistrictName(): String? {
        return districtName
    }

    fun setDistrictName(districtName: String) {
        this.districtName = districtName
    }

    fun getCityName(): String? {
        return cityName
    }

    fun setCityName(cityName: String) {
        this.cityName = cityName
    }

    fun getPincode(): String? {
        return pincode
    }

    fun setPincode(pincode: String) {
        this.pincode = pincode
    }

    fun getMobileNo(): String? {
        return mobileNo
    }

    fun setMobileNo(mobileNo: String) {
        this.mobileNo = mobileNo
    }

    fun getPhoneNo(): String? {
        return phoneNo
    }

    fun setPhoneNo(phoneNo: String) {
        this.phoneNo = phoneNo
    }

    fun getBranchId(): String? {
        return branchId
    }

    fun setBranchId(branchId: String) {
        this.branchId = branchId
    }

    fun getBranchName(): String? {
        return branchName
    }

    fun setBranchName(branchName: String) {
        this.branchName = branchName
    }
    fun getProfile_photo(): String? {
        return profile_photo
    }

    fun setProfile_photo(profile_photo: String) {
        this.profile_photo = profile_photo
    }
    fun getCustomer_code(): String? {
        return customer_code
    }

    fun setCustomer_code(customer_code: String) {
        this.customer_code = customer_code
    }
}