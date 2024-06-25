package com.mazenet.prabakaran.mazechit_customer.Adapters

import android.content.res.Resources
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.mazenet.prabakaran.mazechit_customer.Model.AccountCopyData
import com.mazenet.prabakaran.mazechit_customer.Model.NewChitData
import com.mazenet.prabakaran.mazechit_customer.R
import com.mazenet.prabakaran.mazechit_customer.utilities.Constants

import java.lang.ref.WeakReference
import java.util.ArrayList
import java.text.ParseException
import java.text.SimpleDateFormat


class AdapterNewChits(
    private val itemsList: ArrayList<NewChitData>,
    private val listener: IAdapterClickListener
) : RecyclerView.Adapter<AdapterNewChits.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.newchits_listitem, parent, false)
        return MyViewHolder(view, listener)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        holder.txt_chitvalue.text=Constants.money_convertor(Constants.isEmtytoZero(
            itemsList[position].getChitValue()!!
        ), false)
        holder.txt_chitvalue.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_rupee_white,0,0,0)
        holder.txt_chitvalue.compoundDrawablePadding=4
        holder.txt_groupname.text = itemsList[position].getSchemeName()!!
        holder.txt_paid.text =
            Constants.money_convertor(Constants.isEmtytoZero(itemsList[position].getMonthlyDueAmount()!!), false)
        holder.txt_months.text = itemsList[position].getNoOfMonths()!!+" Months"
    }

    override fun getItemCount(): Int {
        return itemsList.size
    }

    class MyViewHolder(itemView: View, listener: IAdapterClickListener) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener, View.OnLongClickListener {


        var txt_chitvalue: TextView
        var txt_groupname: TextView
        var txt_paid: TextView
        var txt_months: TextView


        private val listenerRef: WeakReference<IAdapterClickListener>

        init {
            listenerRef = WeakReference(listener)
            txt_chitvalue = itemView.findViewById(com.mazenet.prabakaran.mazechit_customer.R.id.txt_chitvalue)
            txt_groupname =
                itemView.findViewById(com.mazenet.prabakaran.mazechit_customer.R.id.txt_groupname) as TextView
            txt_paid = itemView.findViewById(com.mazenet.prabakaran.mazechit_customer.R.id.txt_paid) as TextView
            txt_months = itemView.findViewById(com.mazenet.prabakaran.mazechit_customer.R.id.txt_months) as TextView

            itemView.setOnClickListener(this)
        }

        // onClick Listener for view
        override fun onClick(v: View) {
            listenerRef.get()!!.onPositionClicked(v, adapterPosition)
        }


        //onLongClickListener for view
        override fun onLongClick(v: View): Boolean {

            return true
        }
    }
}