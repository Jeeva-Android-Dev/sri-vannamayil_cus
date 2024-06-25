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
import android.widget.TextView
import com.mazenet.prabakaran.mazechit_customer.Activities.HomeActivity
import com.mazenet.prabakaran.mazechit_customer.Adapters.AdapterMyAcntCopy
import com.mazenet.prabakaran.mazechit_customer.Adapters.IAdapterClickListener
import com.mazenet.prabakaran.mazechit_customer.Model.AccountCopyData
import com.mazenet.prabakaran.mazechit_customer.Model.AccountcopyModel
import com.mazenet.prabakaran.mazechit_customer.Model.MyChitsModel
import com.mazenet.prabakaran.mazechit_customer.Model.ReceiptDetailsModel

import com.mazenet.prabakaran.mazechit_customer.R
import com.mazenet.prabakaran.mazechit_customer.Retrofit.ICallService
import com.mazenet.prabakaran.mazechit_customer.Retrofit.RetrofitBuilder
import com.mazenet.prabakaran.mazechit_customer.utilities.Constants
import kotlinx.android.synthetic.main.fragment_groupwise__receipts.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class my_acnt_copy : BaseFragment() {
    var Grouplist = ArrayList<AccountCopyData>()
    lateinit var Recycler_acntcopy: RecyclerView
    lateinit var myacntcopy_adapter: AdapterMyAcntCopy
    lateinit var pb_acntcopy: ProgressBar
    lateinit var txt_total: TextView
    lateinit var swiper_acntcopy: SwipeRefreshLayout
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_my_acnt_copy, container, false)
        (activity as HomeActivity)
            .setActionBarTitle("My Account Copy")
//        (activity as HomeActivity).setActionBarColor(R.color.yellow_dash)
        Recycler_acntcopy = view.findViewById(R.id.recycler_acntcopy) as RecyclerView
        pb_acntcopy = view.findViewById(R.id.pb_acntcopy) as ProgressBar
        txt_total = view.findViewById(R.id.txt_total) as TextView
        swiper_acntcopy = view.findViewById(R.id.swiper_acntcopy) as SwipeRefreshLayout
        Recycler_acntcopy.setHasFixedSize(true)
        val mLayoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        Recycler_acntcopy.setLayoutManager(mLayoutManager)
//        Recycler_newchits.addItemDecoration(DividerItemDecoration(context,0))
        Recycler_acntcopy.setItemAnimator(DefaultItemAnimator())
        pb_acntcopy.visibility = View.GONE
        swiper_acntcopy.setOnRefreshListener {
            get_mychits()
        }
        get_mychits()

        return view
    }

    private fun get_mychits() {
        pb_acntcopy.visibility = View.VISIBLE
        val checklogin: ICallService = RetrofitBuilder.buildservice(ICallService::class.java)
        val loginparameters = HashMap<String, String>()
        loginparameters.put("customer_id", getPrefsString(Constants.CUST_PID, ""))
        loginparameters.put("tenant_id", getPrefsString(Constants.TENANT_ID, ""))
        loginparameters.put("db",getPrefsString(Constants.DB,""))
        val RequestCall = checklogin.get_myacntcopy(loginparameters)
        RequestCall.enqueue(object : retrofit2.Callback<AccountcopyModel> {
            override fun onFailure(call: Call<AccountcopyModel>, t: Throwable) {
                swiper_acntcopy.isRefreshing = false
                pb_acntcopy.visibility = View.GONE
                t.printStackTrace()
            }

            override fun onResponse(call: Call<AccountcopyModel>, response: Response<AccountcopyModel>) {
                pb_acntcopy.visibility = View.GONE
                swiper_acntcopy.isRefreshing = false
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
                            } else {
                                toast("No Receipt details Available")
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

    fun integrateList(leadslist: ArrayList<AccountCopyData>) {
        Grouplist.clear()
        Grouplist.addAll(leadslist)
        myacntcopy_adapter = AdapterMyAcntCopy(Grouplist, object : IAdapterClickListener {
            override fun onPositionClicked(view: View, position: Int) {
                get_receipt_details(
                    Grouplist.get(position).getReceiptNo().toString(),
                    Grouplist.get(position).getReceiptType().toString()
                )
            }

            override fun onLongClicked(position: Int) {
            }
        })
        var total_collected = 0
        for (i in Grouplist) {
            if (i.getReceiptType().equals("Due")) {
                total_collected = total_collected + i.getTotalReceivedAmount()!!.toInt()
            }

        }
        txt_total.text = ": " + Constants.money_convertor(Constants.isEmtytoZero(total_collected.toString()), false)
        Recycler_acntcopy.setAdapter(myacntcopy_adapter)
    }

    private fun get_receipt_details(receiptno: String, receipt_type: String) {
        pb_acntcopy.visibility = View.VISIBLE
        val getleadslist = RetrofitBuilder.buildservice(ICallService::class.java)
        val loginparameters = java.util.HashMap<String, String>()
        loginparameters.put("tenant_id", getPrefsString(Constants.TENANT_ID, ""))
        loginparameters.put("receipt_no", receiptno)
        loginparameters.put("receipt_type", receipt_type)

        loginparameters.put("db",getPrefsString(Constants.DB,""))
        val LeadListRequest = getleadslist.get_receipt_details(loginparameters)
        LeadListRequest.enqueue(object : Callback<ReceiptDetailsModel> {
            override fun onFailure(call: Call<ReceiptDetailsModel>, t: Throwable) {
                pb_acntcopy.visibility = View.GONE
                t.printStackTrace()
            }

            override fun onResponse(
                call: Call<ReceiptDetailsModel>, response: Response<ReceiptDetailsModel>
            ) {
                pb_acntcopy.visibility = View.GONE
                when {
                    response.isSuccessful -> {

                        when {
                            response.code().equals(200) -> {
                                val bundle = Bundle()
                                bundle.putString("recptno", response.body()!!.getReceiptNo())
                                bundle.putString(
                                    "recptdate",
                                    response.body()!!.getReceiptDate() + " / " + response.body()!!.getReceiptTime()
                                )
                                bundle.putString("customername", response.body()!!.getCustomerName())
                                bundle.putString("customermobile", response.body()!!.getMobileNo())
                                bundle.putString("customerid", response.body()!!.getCustomerCode())
                                bundle.putString("groupname", response.body()!!.getGroupname())
                                bundle.putString("ticketno", response.body()!!.getTicketno())
                                bundle.putString("penalty", response.body()!!.getPenaltyAmount())
                                bundle.putString("bonus", response.body()!!.getBonusAmount())
                                bundle.putString("receivedamount", response.body()!!.getReceivedAmount())
                                bundle.putString("paymentmode", response.body()!!.getPayemntType())
                                bundle.putString("chequeno", response.body()!!.getChequeNo())
                                bundle.putString("chequedate", response.body()!!.getChequeDate())
                                bundle.putString("chequebank", response.body()!!.getBankNameId())
                                bundle.putString("chequebranch", response.body()!!.getBranchName())
                                bundle.putString("transactionno", response.body()!!.getTransactionNo())
                                bundle.putString("transactiondate", response.body()!!.getTransactionDate())
                                bundle.putString("installmentno", response.body()!!.getInstallmentno())
                                bundle.putString("isprinted", response.body()!!.getIsPrinted())
                                bundle.putString("username", response.body()!!.getEmployeeName())
                                doFragmentTransactionWithBundle(ReceiptPreview(), "viewpreview", true, bundle)
                            }
                        }
                    }
                    else -> pb_acntcopy.visibility = View.GONE
                }
            }
        })

    }
}
