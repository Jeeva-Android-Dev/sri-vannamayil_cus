package com.mazenet.prabakaran.mazechit_customer.Model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName



class NewChitModel {
    @SerializedName("status")
    @Expose
    private var status: String? = null
    @SerializedName("msg")
    @Expose
    private var msg: String? = null
    @SerializedName("data")
    @Expose
    private var data: ArrayList<NewChitData>? = null

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
    fun NewChitModel(status: String, msg: String, data: ArrayList<NewChitData>) {

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

    fun getData(): ArrayList<NewChitData>? {
        return data
    }

    fun setData(data: ArrayList<NewChitData>) {
        this.data = data
    }
}