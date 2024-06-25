package com.mazenet.prabakaran.mazechit_customer.Model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName



class MyChitsModel {
    @SerializedName("enrollment_id")
    @Expose
    private var enrollmentId: String? = null
    @SerializedName("group_name")
    @Expose
    private var groupName: String? = null
    @SerializedName("ticket_no")
    @Expose
    private var ticketNo: String? = null
    @SerializedName("paid")
    @Expose
    private var paid: String? = null
    @SerializedName("install_amount")
    @Expose
    private var installAmount: String? = null
    @SerializedName("pending_amount")
    @Expose
    private var pendingAmount: String? = null
    @SerializedName("advance_amount")
    @Expose
    private var advanceAmount: String? = null
    @SerializedName("chit_value")
    @Expose
    private var chitValue: String? = null

    /**
     * No args constructor for use in serialization
     *
     */


    /**
     *
     * @param paid
     * @param ticketNo
     * @param groupName
     * @param enrollmentId
     * @param chitValue
     * @param installAmount
     * @param pendingAmount
     * @param advanceAmount
     */
    fun MyChitsModel(
        enrollmentId: String,
        groupName: String,
        ticketNo: String,
        paid: String,
        installAmount: String,
        pendingAmount: String,
        advanceAmount: String,
        chitValue: String
    ){

        this.enrollmentId = enrollmentId
        this.groupName = groupName
        this.ticketNo = ticketNo
        this.paid = paid
        this.installAmount = installAmount
        this.pendingAmount = pendingAmount
        this.advanceAmount = advanceAmount
        this.chitValue = chitValue
    }

    fun getEnrollmentId(): String? {
        return enrollmentId
    }

    fun setEnrollmentId(enrollmentId: String) {
        this.enrollmentId = enrollmentId
    }

    fun getGroupName(): String? {
        return groupName
    }

    fun setGroupName(groupName: String) {
        this.groupName = groupName
    }

    fun getTicketNo(): String? {
        return ticketNo
    }

    fun setTicketNo(ticketNo: String) {
        this.ticketNo = ticketNo
    }

    fun getPaid(): String? {
        return paid
    }

    fun setPaid(paid: String) {
        this.paid = paid
    }

    fun getInstallAmount(): String? {
        return installAmount
    }

    fun setInstallAmount(installAmount: String) {
        this.installAmount = installAmount
    }

    fun getPendingAmount(): String? {
        return pendingAmount
    }

    fun setPendingAmount(pendingAmount: String) {
        this.pendingAmount = pendingAmount
    }

    fun getAdvanceAmount(): String? {
        return advanceAmount
    }

    fun setAdvanceAmount(advanceAmount: String) {
        this.advanceAmount = advanceAmount
    }

    fun getChitValue(): String? {
        return chitValue
    }

    fun setChitValue(chitValue: String) {
        this.chitValue = chitValue
    }

}