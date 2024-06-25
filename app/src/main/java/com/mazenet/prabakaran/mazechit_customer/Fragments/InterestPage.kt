package com.mazenet.prabakaran.mazechit_customer.Fragments


import android.os.Bundle
import android.provider.SyncStateContract
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import com.mazenet.prabakaran.mazechit_customer.Activities.login
import com.mazenet.prabakaran.mazechit_customer.Model.successmsgmodel

import com.mazenet.prabakaran.mazechit_customer.R
import com.mazenet.prabakaran.mazechit_customer.Retrofit.ICallService
import com.mazenet.prabakaran.mazechit_customer.Retrofit.RetrofitBuilder
import com.mazenet.prabakaran.mazechit_customer.utilities.Constants
import kotlinx.android.synthetic.main.fragment_interest_page.*
import kotlinx.android.synthetic.main.fragment_interest_page.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class InterestPage : BaseFragment() {

    lateinit var txt_schemename:TextView
    lateinit var txt_chitvalue:TextView
    lateinit var txt_totalmonths:TextView
    lateinit var txt_monthlyamnt:TextView
    lateinit var txt_totmembers:TextView
    lateinit var txt_remark:EditText
    lateinit var pb_interest:ProgressBar

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view=inflater.inflate(R.layout.fragment_interest_page, container, false)
        txt_schemename=view.findViewById(R.id.txt_schemename) as TextView
        txt_chitvalue=view.findViewById(R.id.txt_chitvalue) as TextView
        txt_totalmonths=view.findViewById(R.id.txt_totalmonths) as TextView
        txt_monthlyamnt=view.findViewById(R.id.txt_monthlyamnt) as TextView
        txt_totmembers=view.findViewById(R.id.txt_totmembers) as TextView
        txt_remark=view.findViewById(R.id.txt_remark) as EditText
        pb_interest=view.findViewById(R.id.pb_interest) as ProgressBar
        pb_interest.visibility=View.GONE
        txt_schemename.setText(arguments!!.getString("schemename"))
        txt_chitvalue.setText(arguments!!.getString("chitvalue"))
        txt_totalmonths.setText(arguments!!.getString("months"))
        txt_monthlyamnt.setText(arguments!!.getString("monthlyamnt"))
        txt_totmembers.setText(arguments!!.getString("members"))
        view.btn_send_interest.setOnClickListener {
            val remarks=Constants.isEmtytoZero(view.txt_remark.text.toString())
            send_interest(remarks)
        }
        return view
    }

    fun send_interest(
        REMARKS: String
    ) {
        pb_interest.visibility=View.VISIBLE
        val loginrequest: ICallService = RetrofitBuilder.buildservice(ICallService::class.java)
        val loginparameters = HashMap<String, String>()
        loginparameters.put("customer_id", getPrefsString(Constants.CUST_PID,""))
        loginparameters.put("tenant_id",  getPrefsString(Constants.TENANT_ID,""))
        loginparameters.put("branch_id",  getPrefsString(Constants.BRANCH_ID,""))
        arguments!!.getString("schemeid")?.let { loginparameters.put("scheme_id", it) }
        loginparameters.put("remarks", REMARKS)
        loginparameters.put("db",getPrefsString(Constants.DB,""))

        val RequestCall = loginrequest.send_interest(loginparameters)
        RequestCall.enqueue(object : Callback<successmsgmodel> {
            override fun onFailure(call: Call<successmsgmodel>, t: Throwable) {
                pb_interest.visibility=View.GONE
                toast("Sending Interest failed")
                t.printStackTrace()
            }

            override fun onResponse(call: Call<successmsgmodel>, response: Response<successmsgmodel>) {
                pb_interest.visibility=View.GONE
                if (response.isSuccessful) {
                    if (response.code().equals(200)) {
                        toast(response.body()!!.getMsg()!!)
                        val fragmentManager = fragmentManager
                        fragmentManager!!.popBackStack(
                            fragmentManager.getBackStackEntryAt(fragmentManager.backStackEntryCount - 1).id,
                            FragmentManager.POP_BACK_STACK_INCLUSIVE
                        )
                    } else {
                    }
                } else {
                    toast("Sending Interest Failed")
                }
            }
        })
    }
}
