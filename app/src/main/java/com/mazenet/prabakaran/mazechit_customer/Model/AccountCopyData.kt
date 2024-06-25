package com.mazenet.prabakaran.mazechit_customer.Model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName



class AccountCopyData {

    @SerializedName("receipt_no")
    @Expose
    private var receiptNo: String? = null
    @SerializedName("receipt_date")
    @Expose
    private var receiptDate: String? = null
    @SerializedName("receipt_time")
    @Expose
    private var receiptTime: String? = null
    @SerializedName("group_name")
    @Expose
    private var groupName: String? = null
    @SerializedName("ticket_no")
    @Expose
    private var ticketNo: String? = null
    @SerializedName("total_received_amount")
    @Expose
    private var totalReceivedAmount: String? = null
    @SerializedName("payment_type")
    @Expose
    private var paymentType: String? = null
    @SerializedName("receipt_type")
    @Expose
    private var receiptType: String? = null
    @SerializedName("status")
    @Expose
    private var status: String? = null

    /**
     * No args constructor for use in serialization
     *
     */
   
    /**
     *
     * @param ticketNo
     * @param groupName
     * @param receiptDate
     * @param receiptType
     * @param receiptNo
     * @param paymentType
     * @param totalReceivedAmount
     * @param receiptTime
     */
    fun AccountCopyData(
        receiptNo: String,
        receiptDate: String,
        receiptTime: String,
        groupName: String,
        ticketNo: String,
        totalReceivedAmount: String,
        paymentType: String,
        receiptType: String,
        status: String
    ) {
       
        this.receiptNo = receiptNo
        this.receiptDate = receiptDate
        this.receiptTime = receiptTime
        this.groupName = groupName
        this.ticketNo = ticketNo
        this.totalReceivedAmount = totalReceivedAmount
        this.paymentType = paymentType
        this.receiptType = receiptType
        this.status = status
    }

    fun getReceiptNo(): String? {
        return receiptNo
    }

    fun setReceiptNo(receiptNo: String) {
        this.receiptNo = receiptNo
    }

    fun getReceiptDate(): String? {
        return receiptDate
    }

    fun setReceiptDate(receiptDate: String) {
        this.receiptDate = receiptDate
    }

    fun getReceiptTime(): String? {
        return receiptTime
    }

    fun setReceiptTime(receiptTime: String) {
        this.receiptTime = receiptTime
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

    fun getTotalReceivedAmount(): String? {
        return totalReceivedAmount
    }

    fun setTotalReceivedAmount(totalReceivedAmount: String) {
        this.totalReceivedAmount = totalReceivedAmount
    }

    fun getPaymentType(): String? {
        return paymentType
    }

    fun setPaymentType(paymentType: String) {
        this.paymentType = paymentType
    }

    fun getReceiptType(): String? {
        return receiptType
    }

    fun setReceiptType(receiptType: String) {
        this.receiptType = receiptType
    }
    fun getStatus(): String? {
        return status
    }

    fun setStatus(status: String) {
        this.status = status
    }
}