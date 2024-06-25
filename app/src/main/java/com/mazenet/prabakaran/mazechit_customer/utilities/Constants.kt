package com.mazenet.prabakaran.mazechit_customer.utilities

import android.app.Activity
import android.widget.Toast
import java.text.NumberFormat
import java.util.*

object Constants {

    //Preferences variables
    val DB = "sa_chits"
    val UserPref = "UserPreferences"
    val IS_ONLINE = "isonline"
    val First_time = "firsttime"
    val DAYCHECK_COLL = "DAYCHECKCOLL"
    val WEEKCHECK_COLL = "WEEKCHECK_COLL"
    val online_cache_date = "cache_date"
    val fingerprint_set = "Finngerprint_set"
    val application_logged_in = "application_logged_in"
    val str_keyvariable = "mazechit_finger_key"
    val str_doesnt_support_fingerprint = "Your device doesn't support fingerprint authentication"
    val str_enable_fingerprint_permission = "Please enable the fingerprint permission"
    val str_fingerprint_not_configured =
        "No fingerprint configured. Please register at least one fingerprint in your device's Settings"
    val str_enable_security = "Please enable lockscreen security in your device's Settings"
    val SPLASH_TIME_OUT = 500
    val CUST_PID = "custid"
    val CUST_NAME="custname"
    val TENANT_ID="tenantid"
    val BRANCH_ID="branchid"
    val BRANCH_NAME="BRANCH_NAME"
    val TENANT_NAME="TENANT_NAME"
    val TENANT_ADD1="TENANT_ADD1"
    val TENANT_ADD2="TENANT_ADD2"
    val TENANT_STATE="TENANT_STATE"
    val TENANT_DISTRICT="TENANT_DISTRICT"
    val TENANT_PINCODE="TENANT_PINCODE"
    val TENANT_MOBILENO="TENANT_MOBILENO"
    val TENANT_PHONE="TENANT_PHONE"
    val PROFILE_IMAGE="PROFILE_IMAGE"
    val CUST_CODE="CUST_CODE"


    fun showToast(string: String, activity: Activity) {
        Toast.makeText(
            activity,
            string,
            Toast.LENGTH_SHORT
        ).show()
    }
    fun money_convertor(string_amount: String, showminus: Boolean?): String {
        var tobe_converted = string_amount
        var minus: Boolean? = false
        val curLocale = Locale("en", "IN")
        //get current locale and display number
        //Locale curLocale = Locale.getDefault();
        try {
            try {
                tobe_converted = tobe_converted.trim { it <= ' ' }.replace(" ".toRegex(), "")
                if (tobe_converted.contains("-")) {
                    minus = true
                    tobe_converted = tobe_converted.replace("-".toRegex(), "")
                }
                try {
                } catch (e: NullPointerException) {
                    e.printStackTrace()
                }

                tobe_converted = tobe_converted.replace(",".toRegex(), "")
            } catch (e: Exception) {
                e.printStackTrace()
            }

            if (tobe_converted.contains("-")) {
                tobe_converted = "0"
            }
            if (tobe_converted.trim { it <= ' ' }.equals("", ignoreCase = true)) {
                tobe_converted = "0"
            }
            if (tobe_converted.trim { it <= ' ' }.equals("null", ignoreCase = true)) {
                tobe_converted = "0"
            }
            val d = java.lang.Double.parseDouble(tobe_converted.trim { it <= ' ' })
            tobe_converted = NumberFormat.getNumberInstance(curLocale).format(d)

            if (minus!!) {
                if (showminus!!) {
                    tobe_converted = "(-)$tobe_converted"
                }
            }

        } catch (e: NumberFormatException) {
            tobe_converted = string_amount
            e.printStackTrace()
        }

        return tobe_converted
    }

    fun isEmtytoZero(string: String): String {
        var result = string
        if (string.isEmpty()) {
            result = "0"
        } else {
            result = string
        }
        return result
    }

    fun stringToInt(string: String): Int {
        var result: Int
        if (string.isEmpty()) {
            result = 0
        } else {
            try {
                result = string.toInt()
            } catch (e: java.lang.NumberFormatException) {
                result = 0
            }

        }
        return result
    }
}