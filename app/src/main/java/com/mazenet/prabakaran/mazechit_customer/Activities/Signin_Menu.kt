package com.mazenet.prabakaran.mazechit_customer.Activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.mazenet.prabakaran.mazechit_customer.R
import kotlinx.android.synthetic.main.activity_signin__menu.*

class Signin_Menu : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signin__menu)
        img_menuimage.setImageDrawable(resources.getDrawable(R.drawable.srivanmayilog))
        btn_menu_signin.setOnClickListener {
            val i = Intent(this, login::class.java)
            startActivity(i)
        }
        btn_menu_signup.setOnClickListener {
            val i = Intent(this, SignUp::class.java)
            startActivity(i)
        }
    }
}
