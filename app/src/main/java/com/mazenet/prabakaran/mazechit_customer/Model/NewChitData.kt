package com.mazenet.prabakaran.mazechit_customer.Model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName



class NewChitData {
    @SerializedName("scheme_id")
    @Expose
    private var schemeId: String? = null
    @SerializedName("scheme_name")
    @Expose
    private var schemeName: String? = null
    @SerializedName("chit_value")
    @Expose
    private var chitValue: String? = null
    @SerializedName("no_of_months")
    @Expose
    private var noOfMonths: String? = null
    @SerializedName("no_of_members")
    @Expose
    private var noOfMembers: String? = null
    @SerializedName("monthly_due_amount")
    @Expose
    private var monthlyDueAmount: String? = null
    @SerializedName("total_vacanct")
    @Expose
    private var totalVacanct: String? = null

    /**
     * No args constructor for use in serialization
     *
     */
    /**
     *
     * @param schemeId
     * @param totalVacanct
     * @param chitValue
     * @param noOfMembers
     * @param schemeName
     * @param monthlyDueAmount
     * @param noOfMonths
     */
    fun NewChitData(
        schemeId: String,
        schemeName: String,
        chitValue: String,
        noOfMonths: String,
        noOfMembers: String,
        monthlyDueAmount: String,
        totalVacanct: String
    ){
      
        this.schemeId = schemeId
        this.schemeName = schemeName
        this.chitValue = chitValue
        this.noOfMonths = noOfMonths
        this.noOfMembers = noOfMembers
        this.monthlyDueAmount = monthlyDueAmount
        this.totalVacanct = totalVacanct
    }

    fun getSchemeId(): String? {
        return schemeId
    }

    fun setSchemeId(schemeId: String) {
        this.schemeId = schemeId
    }

    fun getSchemeName(): String? {
        return schemeName
    }

    fun setSchemeName(schemeName: String) {
        this.schemeName = schemeName
    }

    fun getChitValue(): String? {
        return chitValue
    }

    fun setChitValue(chitValue: String) {
        this.chitValue = chitValue
    }

    fun getNoOfMonths(): String? {
        return noOfMonths
    }

    fun setNoOfMonths(noOfMonths: String) {
        this.noOfMonths = noOfMonths
    }

    fun getNoOfMembers(): String? {
        return noOfMembers
    }

    fun setNoOfMembers(noOfMembers: String) {
        this.noOfMembers = noOfMembers
    }

    fun getMonthlyDueAmount(): String? {
        return monthlyDueAmount
    }

    fun setMonthlyDueAmount(monthlyDueAmount: String) {
        this.monthlyDueAmount = monthlyDueAmount
    }

    fun getTotalVacanct(): String? {
        return totalVacanct
    }

    fun setTotalVacanct(totalVacanct: String) {
        this.totalVacanct = totalVacanct
    }
}