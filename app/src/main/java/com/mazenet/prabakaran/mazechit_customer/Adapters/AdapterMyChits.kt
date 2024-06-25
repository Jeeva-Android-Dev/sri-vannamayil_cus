package com.mazenet.prabakaran.mazechit_customer.Adapters

import android.content.res.Resources
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.mazenet.prabakaran.mazechit_customer.Model.MyChitsModel
import com.mazenet.prabakaran.mazechit_customer.R
import com.mazenet.prabakaran.mazechit_customer.utilities.Constants

import java.lang.ref.WeakReference
import java.util.ArrayList

class AdapterMyChits(
    private val itemsList: ArrayList<MyChitsModel>,
    private val listener: IAdapterClickListener
) : RecyclerView.Adapter<AdapterMyChits.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.mychits_listitem, parent, false)
        return MyViewHolder(view, listener)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        holder.txt_chitvalue.text=Constants.money_convertor(Constants.isEmtytoZero(
            itemsList[position].getChitValue()!!
        ), false)
        holder.txt_chitvalue.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_rupee_white,0,0,0)
        holder.txt_chitvalue.compoundDrawablePadding=4
        holder.txt_groupname.text= itemsList[position].getGroupName()+" / "+itemsList[position].getTicketNo()
        holder.txt_paid.text= Constants.money_convertor(Constants.isEmtytoZero(itemsList[position].getPaid()!!), false)
        holder.txt_pending.text= Constants.money_convertor(Constants.isEmtytoZero(itemsList[position].getPendingAmount()!!), false)
        holder.txt_advanceamount.text= Constants.money_convertor(Constants.isEmtytoZero(itemsList[position].getAdvanceAmount()!!), false)
    }

    override fun getItemCount(): Int {
        return itemsList.size
    }

    class MyViewHolder(itemView: View, listener: IAdapterClickListener) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener, View.OnLongClickListener {


        var txt_chitvalue: TextView
        var txt_groupname: TextView
        var txt_paid: TextView
        var txt_pending: TextView
        var txt_advanceamount: TextView


        private val listenerRef: WeakReference<IAdapterClickListener>

        init {
            listenerRef = WeakReference(listener)
             txt_chitvalue = itemView.findViewById(R.id.txt_chitvalue)
             txt_groupname = itemView.findViewById(R.id.txt_groupname)
             txt_paid = itemView.findViewById(R.id.txt_paid) as TextView
            txt_pending = itemView.findViewById(R.id.txt_pending) as TextView
            txt_advanceamount = itemView.findViewById(R.id.txt_advanceamount) as TextView

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