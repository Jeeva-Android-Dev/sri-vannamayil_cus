package com.mazenet.prabakaran.mazechit_customer.Activities

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import com.mazenet.prabakaran.mazechit_customer.utilities.Constants
import com.mazenet.prabakaran.mazechit_customer.R
import kotlinx.android.synthetic.main.activity_splash.*

class splash : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
       spalsh.setImageResource(R.drawable.srivanmayilog)

        Handler().postDelayed({
            var counter = getPrefsInt(Constants.First_time, 0)
            if (counter > 0) {

            } else {
                counter += 1
                setPrefsInt(Constants.First_time, counter)
            }
            if (getPrefsString(Constants.application_logged_in, "no").equals("yes")) {
                if (checkForInternet()) {
                    val i = Intent(this, HomeActivity::class.java)
                    startActivity(i)
                    finish()
                } else {

                }
                finish()
            } else {
                val i = Intent(this, Signin_Menu::class.java)
                startActivity(i)
                finish()
            }

        }, Constants.SPLASH_TIME_OUT.toLong())
    }
}
