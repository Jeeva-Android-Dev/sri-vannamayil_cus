package com.mazenet.prabakaran.mazechit_customer.Fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.daimajia.slider.library.SliderLayout

import com.daimajia.slider.library.SliderTypes.BaseSliderView
import com.daimajia.slider.library.SliderTypes.TextSliderView
import com.mazenet.prabakaran.mazechit_customer.Activities.HomeActivity

import com.mazenet.prabakaran.mazechit_customer.R
import com.mazenet.prabakaran.mazechit_customer.utilities.Constants
import kotlinx.android.synthetic.main.fragment_dashboard.view.*
import java.util.HashMap


class Fragment_Dashboard : BaseFragment(), BaseSliderView.OnSliderClickListener {
    override fun onSliderClick(slider: BaseSliderView?) {

    }

    lateinit var slider: SliderLayout
    lateinit var txt_welcome: TextView
    override fun onResume() {
        (activity as HomeActivity).setActionBarColor(R.color.colorPrimary)
        super.onResume()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_dashboard, container, false)
        slider = view.findViewById(R.id.slider) as SliderLayout
        txt_welcome = view.findViewById(R.id.txt_welcome) as TextView
        (activity as HomeActivity)
            .setActionBarTitle("My Dashboard")
        txt_welcome.setText("Hi "+getPrefsString(Constants.CUST_NAME,"" ))
        view.layout_mychits.setOnClickListener {
            doFragmentTransaction(Mychits(), "mychits", true, "", "")
        }
        view.layout_myacntcopy.setOnClickListener {
            doFragmentTransaction(my_acnt_copy(), "myacntcopy", true, "", "")
        }
        view.layout_newchits.setOnClickListener {
            doFragmentTransaction(NewChits(), "newchits", true, "", "")
        }
        view.logout_layout.setOnClickListener {
            setPrefsString(Constants.application_logged_in, "no")
            (activity as HomeActivity).logoutdb()
        }
        val file_maps = HashMap<String, Int>()
        file_maps["1"] = R.drawable.sliderimg
        file_maps["2"] = R.drawable.sliderimg
        file_maps["3"] = R.drawable.sliderimg


        for (name in file_maps.keys) {
            val textSliderView = TextSliderView(context)
            // initialize a SliderLayout
            textSliderView
                //  .description(name)
                .image(file_maps[name]!!)
                .setScaleType(com.daimajia.slider.library.SliderTypes.BaseSliderView.ScaleType.CenterInside)
                .setOnSliderClickListener(this)


            textSliderView.bundle(Bundle())
            textSliderView.bundle.putString("extra", name)

            slider.addSlider(textSliderView)

        }

        slider.setPresetTransformer(SliderLayout.Transformer.Default)
        slider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom)
       // slider.setCustomAnimation(ChildAnimationExample())
        slider.setDuration(4000)
        //  mDemoSlider.addOnPageChangeListener(this);

        return view
    }


}
