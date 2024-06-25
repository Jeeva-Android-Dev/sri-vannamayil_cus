package com.mazenet.prabakaran.mazechit_customer.Adapters

import android.view.View

interface IAdapterClickListener {

    fun onPositionClicked(view:View,position:Int)
    fun onLongClicked(position:Int)
//    fun onSwitchClicked(position:Int)
}