package com.mazenet.prabakaran.mazechit_customer.Adapters

import android.content.res.Resources
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.mazenet.prabakaran.mazechit_customer.Model.AccountCopyData
import com.mazenet.prabakaran.mazechit_customer.R
import com.mazenet.prabakaran.mazechit_customer.utilities.Constants

import java.lang.ref.WeakReference
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


class AdapterMyAcntCopy(
    private val itemsList: ArrayList<AccountCopyData>,
    private val listener: IAdapterClickListener
) : RecyclerView.Adapter<AdapterMyAcntCopy.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(com.mazenet.prabakaran.mazechit_customer.R.layout.myacntcopy_listitem, parent, false)
        return MyViewHolder(view, listener)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val ServerReceipttime = itemsList[position].getReceiptTime()
        var parsedTime = ""
        try {
            val sdf = SimpleDateFormat("H:mm:ss")
            val dateObj = sdf.parse(ServerReceipttime)
            parsedTime = SimpleDateFormat("hh:mm a").format(dateObj)

        } catch (e: ParseException) {
            e.printStackTrace()
        }
        val ServerDate = itemsList[position].getReceiptDate()!!
        var ParsedDate: String = ""


        val ServerDateFormat = SimpleDateFormat("yyyy-MM-dd")
        val ThisMonthDateFormat = SimpleDateFormat("MMM-dd")
        val today_calender = Calendar.getInstance()
        val thisYear = today_calender.get(Calendar.YEAR).toString()
        val str = ServerDate.split("-".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        val ServerYear = Integer.parseInt(str[0]).toString()
        val da = ServerDateFormat.parse(ServerDate)
        val todayd = Date()
        val taday = ServerDateFormat.format(todayd)!!
        val motnd = ThisMonthDateFormat.format(da)
        if (ServerDate.equals(taday)) {
            ParsedDate = "Today"
        } else if (thisYear.equals(ServerYear)) {
            ParsedDate = motnd
        } else {
            ParsedDate = ServerDate
        }



        holder.txt_chitvalue.text =
            itemsList[position].getReceiptNo()
        holder.txt_groupname.text = itemsList[position].getGroupName() + " / " + itemsList[position].getTicketNo()
        holder.txt_paid.text =
            Constants.money_convertor(Constants.isEmtytoZero(itemsList[position].getTotalReceivedAmount()!!), false)
        val paytype = itemsList[position].getPaymentType()!!
        if(paytype.equals("cheque",ignoreCase = true))
        {
            holder.txt_stattus.visibility=View.VISIBLE
            holder.txt_stattus.text=itemsList[position].getStatus()
        }else
        {
            holder.txt_stattus.visibility=View.GONE
        }
        holder.txt_pending.text =paytype
        holder.txt_months.text = ParsedDate + " (" + parsedTime + ")"

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
        var txt_months: TextView
        var txt_stattus: TextView

        private val listenerRef: WeakReference<IAdapterClickListener>

        init {
            listenerRef = WeakReference(listener)
            txt_chitvalue = itemView.findViewById(com.mazenet.prabakaran.mazechit_customer.R.id.txt_chitvalue)
            txt_groupname =
                itemView.findViewById(com.mazenet.prabakaran.mazechit_customer.R.id.txt_groupname) as TextView
            txt_paid = itemView.findViewById(com.mazenet.prabakaran.mazechit_customer.R.id.txt_paid) as TextView
            txt_pending = itemView.findViewById(com.mazenet.prabakaran.mazechit_customer.R.id.txt_pending) as TextView
            txt_months = itemView.findViewById(com.mazenet.prabakaran.mazechit_customer.R.id.txt_months) as TextView
            txt_stattus = itemView.findViewById(com.mazenet.prabakaran.mazechit_customer.R.id.txt_stattus) as TextView
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