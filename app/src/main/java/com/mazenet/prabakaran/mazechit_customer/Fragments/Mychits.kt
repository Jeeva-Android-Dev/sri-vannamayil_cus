package com.mazenet.prabakaran.mazechit_customer.Fragments


import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import com.mazenet.prabakaran.mazechit_customer.Activities.login
import com.mazenet.prabakaran.mazechit_customer.Adapters.AdapterMyChits
import com.mazenet.prabakaran.mazechit_customer.Adapters.IAdapterClickListener
import com.mazenet.prabakaran.mazechit_customer.Model.MyChitsModel
import com.mazenet.prabakaran.mazechit_customer.Model.SignupModel

import com.mazenet.prabakaran.mazechit_customer.R
import com.mazenet.prabakaran.mazechit_customer.Retrofit.ICallService
import com.mazenet.prabakaran.mazechit_customer.Retrofit.RetrofitBuilder
import com.mazenet.prabakaran.mazechit_customer.utilities.Constants
import kotlinx.android.synthetic.main.activity_sign_up.*
import kotlinx.android.synthetic.main.fragment_mychits.*
import kotlinx.android.synthetic.main.fragment_mychits.view.*
import retrofit2.Call
import retrofit2.Response
import com.mazenet.prabakaran.mazechit_customer.Activities.HomeActivity

class Mychits : BaseFragment() {
    var Grouplist = ArrayList<MyChitsModel>()
    lateinit var Recycler_groups: RecyclerView
    lateinit var Mychitsadapter: AdapterMyChits
    lateinit var pb_mychit: ProgressBar
    lateinit var swiper_mychits: SwipeRefreshLayout
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(com.mazenet.prabakaran.mazechit_customer.R.layout.fragment_mychits, container, false)
        (activity as HomeActivity).setActionBarTitle("My Chits")
//        (activity as HomeActivity).setActionBarColor(R.color.red_dash)
        Recycler_groups = view.findViewById(com.mazenet.prabakaran.mazechit_customer.R.id.reecycler_mychits) as RecyclerView
        pb_mychit = view.findViewById(com.mazenet.prabakaran.mazechit_customer.R.id.pb_mychits) as ProgressBar
        swiper_mychits = view.findViewById(com.mazenet.prabakaran.mazechit_customer.R.id.swiper_mychits) as SwipeRefreshLayout
        Recycler_groups.setHasFixedSize(true)
        val mLayoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        Recycler_groups.setLayoutManager(mLayoutManager)
//        Recycler_newchits.addItemDecoration(DividerItemDecoration(context,0))
        Recycler_groups.setItemAnimator(DefaultItemAnimator())
        pb_mychit.visibility = View.GONE
        get_mychits()
        swiper_mychits.setOnRefreshListener {
            get_mychits()
        }

        return view
    }

    private fun get_mychits() {
        pb_mychit.visibility = View.VISIBLE
        val checklogin: ICallService = RetrofitBuilder.buildservice(ICallService::class.java)
        val loginparameters = HashMap<String, String>()
        loginparameters.put("customer_id", getPrefsString(Constants.CUST_PID, ""))
        loginparameters.put("tenant_id", getPrefsString(Constants.TENANT_ID, ""))
        loginparameters.put("db",getPrefsString(Constants.DB,""))
        val RequestCall = checklogin.get_mychits(loginparameters)
        RequestCall.enqueue(object : retrofit2.Callback<ArrayList<MyChitsModel>> {
            override fun onFailure(call: Call<ArrayList<MyChitsModel>>, t: Throwable) {
                pb_mychit.visibility = View.GONE
                swiper_mychits.isRefreshing=false
                t.printStackTrace()
            }

            override fun onResponse(call: Call<ArrayList<MyChitsModel>>, response: Response<ArrayList<MyChitsModel>>) {
                pb_mychit.visibility = View.GONE
                swiper_mychits.isRefreshing=false
                if (response.isSuccessful) {

                    when {
                        response.code().equals(200) -> {
                            val resultlist = response.body()
                            if (resultlist!!.size > 0) {
                                integrateList(resultlist)
                            } else {
                                toast("Still No Group details Available")
                            }
                        }
                        response.code().equals(401) -> toast("Server Error")
                        response.code().equals(500) -> toast("Internal server Error")
                    }
                } else {
                    when {
                        response.code().equals(401) -> toast(response.message())
                        response.code().equals(500) -> toast("Internal server Error")
                    }

                }

            }


        })
    }

    fun integrateList(leadslist: ArrayList<MyChitsModel>) {
        Grouplist.clear()
        Grouplist.addAll(leadslist)
        Mychitsadapter = AdapterMyChits(Grouplist, object : IAdapterClickListener {
            override fun onPositionClicked(view: View, position: Int) {
                val bundle = Bundle()
                bundle.putString("enrolid", Grouplist.get(position).getEnrollmentId()!!)
                bundle.putString("groupname", Grouplist.get(position).getGroupName()!!)
                bundle.putString("ticketno", Grouplist.get(position).getTicketNo()!!)
                doFragmentTransactionWithBundle(Groupwise_Receipts(), "GRPWISERECIPTS", true, bundle)
            }

            override fun onLongClicked(position: Int) {
            }
        })
        Recycler_groups.setAdapter(Mychitsadapter)
    }
}
