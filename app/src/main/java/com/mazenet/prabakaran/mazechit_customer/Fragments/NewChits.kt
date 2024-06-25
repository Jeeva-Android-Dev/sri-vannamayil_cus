package com.mazenet.prabakaran.mazechit_customer.Fragments


import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import com.mazenet.prabakaran.mazechit_customer.Activities.HomeActivity
import com.mazenet.prabakaran.mazechit_customer.Adapters.AdapterNewChits
import com.mazenet.prabakaran.mazechit_customer.Adapters.IAdapterClickListener
import com.mazenet.prabakaran.mazechit_customer.Model.NewChitData
import com.mazenet.prabakaran.mazechit_customer.Model.NewChitModel

import com.mazenet.prabakaran.mazechit_customer.R
import com.mazenet.prabakaran.mazechit_customer.Retrofit.ICallService
import com.mazenet.prabakaran.mazechit_customer.Retrofit.RetrofitBuilder
import com.mazenet.prabakaran.mazechit_customer.utilities.Constants
import retrofit2.Call
import retrofit2.Response


class NewChits : BaseFragment() {
    var newchitslist = ArrayList<NewChitData>()
    lateinit var Recycler_newchits: RecyclerView
    lateinit var newchits_adapter: AdapterNewChits
    lateinit var pb_newchits: ProgressBar
    lateinit var swiper_newchits: SwipeRefreshLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_new_chits, container, false)
        (activity as HomeActivity)
            .setActionBarTitle("New Commenced Chits")
//        (activity as HomeActivity).setActionBarColor(R.color.lime_dash)
        Recycler_newchits = view.findViewById(R.id.recycler_newchits) as RecyclerView
        pb_newchits = view.findViewById(R.id.pb_newchit) as ProgressBar
        swiper_newchits = view.findViewById(R.id.swiper_newchits) as SwipeRefreshLayout
        Recycler_newchits.setHasFixedSize(true)
        val mLayoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        Recycler_newchits.setLayoutManager(mLayoutManager)
//        Recycler_newchits.addItemDecoration(DividerItemDecoration(context,0))
        Recycler_newchits.setItemAnimator(DefaultItemAnimator())
        pb_newchits.visibility = View.GONE
        swiper_newchits.setOnRefreshListener {
            get_mychits()
        }
        get_mychits()
        return view
    }

    private fun get_mychits() {
        pb_newchits.visibility = View.VISIBLE
        val checklogin: ICallService = RetrofitBuilder.buildservice(ICallService::class.java)
        val loginparameters = HashMap<String, String>()
        loginparameters.put("tenant_id", getPrefsString(Constants.TENANT_ID, ""))
        loginparameters.put("db",getPrefsString(Constants.DB,""))
        val RequestCall = checklogin.get_newchits(loginparameters)
        RequestCall.enqueue(object : retrofit2.Callback<NewChitModel> {
            override fun onFailure(call: Call<NewChitModel>, t: Throwable) {
                swiper_newchits.isRefreshing = false
                pb_newchits.visibility = View.GONE
                t.printStackTrace()
            }

            override fun onResponse(call: Call<NewChitModel>, response: Response<NewChitModel>) {
                pb_newchits.visibility = View.GONE
                swiper_newchits.isRefreshing = false
                if (response.isSuccessful) {

                    when {
                        response.code().equals(200) -> {

                            if (response.body()!!.getStatus().equals("Success")) {
                                val resultlist = response.body()!!.getData()
                                if (resultlist!!.size > 0) {
                                    integrateList(resultlist)
                                } else {
                                    toast("Still No Group details Available")
                                }
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

    fun integrateList(leadslist: ArrayList<NewChitData>) {
        newchitslist.clear()
        newchitslist.addAll(leadslist)
        newchits_adapter = AdapterNewChits(newchitslist, object : IAdapterClickListener {
            override fun onPositionClicked(view: View, position: Int) {
                val bundle = Bundle()
                bundle.putString("schemeid", newchitslist.get(position).getSchemeId())
                bundle.putString("schemename", newchitslist.get(position).getSchemeName())
                bundle.putString("months", newchitslist.get(position).getNoOfMonths())
                bundle.putString("members", newchitslist.get(position).getNoOfMembers())
                bundle.putString("chitvalue", newchitslist.get(position).getChitValue())
                bundle.putString("monthlyamnt", newchitslist.get(position).getMonthlyDueAmount())
                doFragmentTransactionWithBundle(InterestPage(), "schemeinterest", true, bundle)
            }

            override fun onLongClicked(position: Int) {
            }
        })
        Recycler_newchits.setAdapter(newchits_adapter)
    }

}
