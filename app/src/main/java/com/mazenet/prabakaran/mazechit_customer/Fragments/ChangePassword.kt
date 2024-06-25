package com.mazenet.prabakaran.mazechit_customer.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mazenet.prabakaran.mazechit_customer.R
import kotlinx.android.synthetic.main.fragment_change_password.view.*

class ChangePassword : BaseFragment() {

    /*
    * created by jeeva s on 2-12-2021
    * */

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = layoutInflater.inflate(R.layout.fragment_change_password,container,false)

        rootView.change_pass_submit.setOnClickListener {
            if(rootView.new_pass.text.isEmpty() || rootView.conf_pass.text.isEmpty()){
                toast("Please check all values are given correctly")
            }
            else{
                if(rootView.new_pass.text.toString()==rootView.conf_pass.text.toString()){

                }
                else{
                    toast("Password mismatch")
                }
            }
        }

        return rootView
    }


}