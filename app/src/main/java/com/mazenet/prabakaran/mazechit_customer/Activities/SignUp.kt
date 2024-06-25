package com.mazenet.prabakaran.mazechit_customer.Activities

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.support.constraint.ConstraintLayout
import android.text.TextUtils
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import com.mazenet.prabakaran.mazechit_customer.Model.SignupModel
import com.mazenet.prabakaran.mazechit_customer.Model.successmsgmodel
import com.mazenet.prabakaran.mazechit_customer.R
import com.mazenet.prabakaran.mazechit_customer.Retrofit.ICallService
import com.mazenet.prabakaran.mazechit_customer.Retrofit.RetrofitBuilder
import com.mazenet.prabakaran.mazechit_customer.utilities.Constants
import kotlinx.android.synthetic.main.activity_sign_up.*
import kotlinx.android.synthetic.main.activity_sign_up.pb_signup
import kotlinx.android.synthetic.main.showotpdilog.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception


class SignUp : BaseActivity() {
    private val TAG = SignUp::class.java.getSimpleName()
    var ChosenEmail = ""
    internal var otp_login_popup: PopupWindow? = null
    internal var change_password_popup: PopupWindow? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.mazenet.prabakaran.mazechit_customer.R.layout.activity_sign_up)
        val i = intent

        try {
            ChosenEmail = i.getStringExtra("email").toString()
            edt_signup_userid.setText(ChosenEmail)
        } catch (e: Exception) {

        }
        btn_generateotp.setOnClickListener {
            val email = edt_signup_userid.text.toString()
            if(email=="9876543210"){
                setPrefsString(Constants.DB,"vannamayil_chits")
            }
            else{
                setPrefsString(Constants.DB,"vannamayil_chits")
            }
            when {

                TextUtils.isEmpty(email) -> toast("Enter Valid User Id or Mobile No.")
                else -> checklogin(email)
            }
        }

    }

    private fun checklogin(email: String) {
        pb_signup.visibility = View.VISIBLE
        val checklogin: ICallService = RetrofitBuilder.buildservice(ICallService::class.java)
        val loginparameters = HashMap<String, String>()
        loginparameters.put("user_id", email)
        loginparameters.put("db",getPrefsString(Constants.DB,""))
        val RequestCall = checklogin.sign_up(loginparameters)
        RequestCall.enqueue(object : retrofit2.Callback<SignupModel> {
            override fun onFailure(call: Call<SignupModel>, t: Throwable) {
                pb_signup.visibility = View.GONE
                t.printStackTrace()
            }

            override fun onResponse(call: Call<SignupModel>, response: Response<SignupModel>) {
                if (response.isSuccessful)  {
                    when {
                        response.code().equals(200) -> {
                            pb_signup.visibility = View.GONE
                            System.out.println("response token  ${response.body()?.getStatus()}")
                            if (response.body()?.getStatus().equals("Success")) {
                                toast(response.body()!!.getMsg().toString())
                                setPrefsString(Constants.CUST_PID, response.body()!!.getCustomerPrimaryId().toString())
                                setPrefsString(Constants.CUST_NAME, response.body()!!.getCustomerName().toString())
                                showotpdilog(
                                    response.body()!!.getOtp()!!.toInt(),
                                    response.body()!!.getCustomerPrimaryId()!!,
                                    email, this@SignUp
                                )
                            } else if (response.body()?.getStatus().equals("Signin")) {
                                val i = Intent(this@SignUp, login::class.java)
                                startActivity(i)
                                finish()
                                toast(response.body()!!.getMsg().toString())
                            }else if (response.body()?.getStatus().equals("Error")) {
                                toast(response.body()!!.getMsg().toString())
                            }else
                            {
                                toast(response.body()!!.getMsg().toString())
                            }
                        }
                        response.code().equals(401) -> toast(response.body()!!.getMsg().toString())
                        response.code().equals(500) -> toast("Internal server Error")
                    }
                } else {
                    pb_signup.visibility = View.GONE
                    when {
                        response.code().equals(401) -> toast(response.message())
                        response.code().equals(500) -> toast("Internal server Error")
                    }
                    Log.e(TAG, "Response not Successfull")
                }

            }


        })
    }

    fun showotpdilog(
        receivedOtp: Int,
        customerid: String,
        email: String, context: Activity
    ) {

        val layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val viewGroup =
            context.findViewById(com.mazenet.prabakaran.mazechit_customer.R.id.showotplayout) as? ConstraintLayout
        val layout = layoutInflater.inflate(com.mazenet.prabakaran.mazechit_customer.R.layout.showotpdilog, viewGroup)

        otp_login_popup = PopupWindow(context)
        otp_login_popup!!.setContentView(layout)
        otp_login_popup!!.setWidth(ConstraintLayout.LayoutParams.MATCH_PARENT)
        otp_login_popup!!.setHeight(ConstraintLayout.LayoutParams.MATCH_PARENT)
        otp_login_popup!!.setAnimationStyle(com.mazenet.prabakaran.mazechit_customer.R.style.AnimationPopup);
        otp_login_popup!!.setFocusable(true)
        otp_login_popup!!.setBackgroundDrawable(null)
        otp_login_popup!!.showAtLocation(layout, Gravity.CENTER, 0, 0)


        val edt_otp = layout.findViewById(com.mazenet.prabakaran.mazechit_customer.R.id.edt_otp) as? EditText
        val txt_submit = layout.findViewById(com.mazenet.prabakaran.mazechit_customer.R.id.btn_submitotp) as? TextView
        val txt_timer = layout.findViewById(com.mazenet.prabakaran.mazechit_customer.R.id.txt_timer) as TextView


        val timer = object : CountDownTimer(60000, 1000) {
            override fun onFinish() {
                txt_timer!!.setText("Resend OTP")
            }

            override fun onTick(millisUntilFinished: Long) {
                val time: Int = (millisUntilFinished / 1000).toInt()
                val minutes = (time % 3600) / 60;
                val seconds = time % 60;
                val timeString = String.format("%02d:%02d", minutes, seconds);
                txt_timer!!.setText(timeString)
            }

        }
        timer.start()
        txt_timer!!.setOnClickListener {
            val text = txt_timer!!.toString()
            if (text.equals("Resend OTP")) {
                otp_login_popup!!.dismiss()
                checklogin(email)
            } else {

            }
        }
        txt_submit!!.setOnClickListener {
            val text = txt_timer!!.text.toString()

            if (text.equals("Resend OTP")) {
                toast("OTP expired, Sign in Again")
            } else {
                val enteredOtpString = edt_otp!!.text.toString()
                if (enteredOtpString.isBlank()) {
                    toast("Enter OTP")
                    return@setOnClickListener
                } else {
                    if (enteredOtpString.equals(receivedOtp.toString())) {
                        otp_login_popup!!.dismiss()
                        toast("OTP Verified")
                        change_password(customerid, context)
                    } else {
                        toast("Enter Correct OTP")
                    }
                }

            }

        }
    }

    fun change_password(
        customerid: String,
        context: Activity
    ) {

        val layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val viewGroup =
            context.findViewById(com.mazenet.prabakaran.mazechit_customer.R.id.changepasslayout) as? ConstraintLayout
        val layout =
            layoutInflater.inflate(com.mazenet.prabakaran.mazechit_customer.R.layout.change_password_dilog, viewGroup)

        change_password_popup = PopupWindow(context)
        change_password_popup!!.setContentView(layout)
        change_password_popup!!.setWidth(ConstraintLayout.LayoutParams.MATCH_PARENT)
        change_password_popup!!.setHeight(ConstraintLayout.LayoutParams.MATCH_PARENT)
        change_password_popup!!.setAnimationStyle(com.mazenet.prabakaran.mazechit_customer.R.style.AnimationPopup);
        change_password_popup!!.setFocusable(true)
        change_password_popup!!.setBackgroundDrawable(null)
        change_password_popup!!.showAtLocation(layout, Gravity.CENTER, 0, 0)


        val edt_newpass = layout.findViewById(com.mazenet.prabakaran.mazechit_customer.R.id.edt_newpass) as? EditText
        val btn_changepassword =
            layout.findViewById(com.mazenet.prabakaran.mazechit_customer.R.id.btn_changepassword) as? Button
        val edt_confirmpass =
            layout.findViewById(com.mazenet.prabakaran.mazechit_customer.R.id.edt_confirmpass) as EditText


        btn_changepassword!!.setOnClickListener {
            val newpass = edt_newpass!!.text.toString()
            val confirmpass = edt_confirmpass!!.text.toString()
            if (newpass.isNullOrEmpty()) {
                toast("Enter New Password")
                return@setOnClickListener
            } else if (confirmpass.isNullOrEmpty()) {
                toast("Enter Password Again")
                return@setOnClickListener
            } else if (newpass.equals(confirmpass)) {
                update_password(customerid,newpass)
            } else {
                toast("Password does not match")
                return@setOnClickListener
            }
        }
    }

    fun update_password(
        userid: String,
        password: String
    ) {

        val loginrequest: ICallService = RetrofitBuilder.buildservice(ICallService::class.java)
        val loginparameters = HashMap<String, String>()
        loginparameters.put("cust_primary_id", userid)
        loginparameters.put("password", password)
        loginparameters.put("db",getPrefsString(Constants.DB,""))

        val RequestCall = loginrequest.update_password(loginparameters)
        RequestCall.enqueue(object : Callback<successmsgmodel> {
            override fun onFailure(call: Call<successmsgmodel>, t: Throwable) {
                toast("Password updation Failed")
                t.printStackTrace()
            }

            override fun onResponse(call: Call<successmsgmodel>, response: Response<successmsgmodel>) {
                if (response.isSuccessful) {
                    if (response.code().equals(200)) {
                        val i = Intent(applicationContext, login::class.java)
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                        i.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
                        startActivity(i)
                        finish()
                        toast("Password updated successfully")
                    } else {
                    }
                } else {
                    toast("Password updation Failed")
                }
            }
        })
    }
}
