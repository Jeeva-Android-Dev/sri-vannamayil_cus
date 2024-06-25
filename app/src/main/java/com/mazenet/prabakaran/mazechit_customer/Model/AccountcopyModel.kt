package com.mazenet.prabakaran.mazechit_customer.Model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName



class AccountcopyModel {
    @SerializedName("status")
    @Expose
    private var status: String? = null
    @SerializedName("msg")
    @Expose
    private var msg: String? = null
    @SerializedName("data")
    @Expose
    private var data: ArrayList<AccountCopyData>? = null

    /**
     * No args constructor for use in serialization
     *
     */


    /**
     *
     * @param status
     * @param data
     * @param msg
     */
    fun AccountcopyModel(status: String, msg: String, data: ArrayList<AccountCopyData>){

        this.status = status
        this.msg = msg
        this.data = data
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

    fun getData(): ArrayList<AccountCopyData>? {
        return data
    }

    fun setData(data: ArrayList<AccountCopyData>) {
        this.data = data
    }

}