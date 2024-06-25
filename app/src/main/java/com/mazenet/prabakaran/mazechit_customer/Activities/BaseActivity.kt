package com.mazenet.prabakaran.mazechit_customer.Activities

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.net.ConnectivityManager
import android.net.Uri
import android.provider.MediaStore
import android.support.design.widget.CoordinatorLayout
import android.support.design.widget.Snackbar

import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.text.format.DateUtils
import android.util.Base64
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.FrameLayout
import android.widget.Toast
import com.mazenet.prabakaran.mazechit_customer.utilities.Constants
import com.mazenet.prabakaran.mazechit_customer.utilities.MvpView
import com.mazenet.prabakaran.mazechit_customer.R


import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream


import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

open class BaseActivity : AppCompatActivity(), MvpView {
    override fun showDialogWithError(errorCode: Int, error: String?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showNetWorkError() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    var progressView: View? = null
    val isNetworkAvailable: Boolean
        get() {
            val state: Boolean
            val cmg = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val activeNetworkInfo = cmg.activeNetworkInfo
            state = activeNetworkInfo != null && activeNetworkInfo.isConnected

            if (state) {
                return true
            } else {
                Log.i("isNetworkAvailable", " No Internet Connection Available! Please Check your Connection")
                return false
            }
        }

    // Self explanatory method
    fun checkForInternet(): Boolean {
        val cm = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = cm.activeNetworkInfo
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting
    }



    fun openActivity(activity: Class<*>) {
        startActivity(Intent(this, activity))
    }


    fun getColorDrawable(colorResId: Int): Int {
        return ContextCompat.getColor(this, colorResId)
    }

    fun getResDrawable(colorResId: Int): Drawable? {
        return ContextCompat.getDrawable(this, colorResId)
    }


    fun toast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    fun snackIt(layout: CoordinatorLayout, msg: String) {
        Snackbar.make(layout, msg, Snackbar.LENGTH_LONG)
            .setDuration(7000)
            .show()
    }


    @Throws(ParseException::class)
    fun isToday(date: String): Boolean {
        return DateUtils.isToday(SimpleDateFormat("EEEE, d MMMM yyyy", Locale.getDefault()).parse(date).time)
    }

    fun getColorRes(colorResId: Int): Int {
        return ContextCompat.getColor(this, colorResId)
    }

    fun getDrawableRes(drawResId: Int): Drawable? {
        return ContextCompat.getDrawable(this, drawResId)
    }

    protected fun setPrefsInt(key: String, `val`: Int) {
        BaseUtils.setSharedPrefs(baseContext, key, `val`)
    }

    protected fun setPrefsString(key: String, `val`: String) {
        BaseUtils.setSharedPrefs(baseContext, key, `val`)

    }

    protected fun setPrefsBoolean(key: String, `val`: Boolean) {
        BaseUtils.setSharedPrefs(baseContext, key, `val`)
    }

    fun getPrefsString(key: String, default: String): String {
        return BaseUtils.getSharedPrefs(baseContext, key, default)!!
    }

    protected fun getPrefsInt(key: String, default: Int): Int {
        return BaseUtils.getSharedPrefs(baseContext, key, default)
    }

    protected fun getPrefsBoolean(key: String, default: Boolean): Boolean {
        return BaseUtils.getSharedPrefs(baseContext, key, default)
    }

    fun ShowKeyBoard() {
        BaseUtils.showKeyboard(baseContext)
    }

    fun CloseKeyBoard() {
        BaseUtils.closeKeyboard(baseContext)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle home button pressed
        val id = item.itemId

        if (id == android.R.id.home) {
            onBackPressed()
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    companion object {
        fun hideSoftKeyboard(activity: Activity) {
            val inputMethodManager = activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(activity.currentFocus!!.windowToken, 0)
        }

        fun closeSoftKeyboard(activity: Activity, rootView: View) {
            if (isKeyboardShown(rootView)) {
                val imm = activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0) // hide
            }

        }

        private fun isKeyboardShown(rootView: View): Boolean {
            /* 128dp = 32dp * 4, minimum button height 32dp and generic 4 rows soft keyboard */
            val SOFT_KEYBOARD_HEIGHT_DP_THRESHOLD = 128

            val r = Rect()
            rootView.getWindowVisibleDisplayFrame(r)
            val dm = rootView.resources.displayMetrics
            /* heightDiff = rootView height - status bar height (r.top) - visible frame height (r.bottom - r.top) */
            val heightDiff = rootView.bottom - r.bottom
            /* Threshold size: dp to pixels, multiply with display density */

            //        Log.d(TAG, "isKeyboardShown ? " + isKeyboardShown + ", heightDiff:" + heightDiff + ", density:" + dm.density
            //                + "root view height:" + rootView.getHeight() + ", rect:" + r);

            return heightDiff > SOFT_KEYBOARD_HEIGHT_DP_THRESHOLD * dm.density
        }
    }

    override fun showProgressDialog() {
        if (progressView == null && checkForInternet()) {
            val rootLayout = findViewById<View>(android.R.id.content) as FrameLayout
            val inflater = LayoutInflater.from(this)
            progressView = inflater.inflate(R.layout.progress_layout, null, true)
            progressView!!.setEnabled(false)
            progressView!!.setOnClickListener({ v ->

            })
            rootLayout.addView(progressView)
//            val imageView = findViewById<ImageView>(R.id.progressImage)
//            val startRotateAnimation = AnimationUtils
//                .loadAnimation(applicationContext, R.anim.rotate_image)
//            imageView.startAnimation(startRotateAnimation)
        }

    }

    override fun hideProgressDialog() {
        if (progressView != null) {
            progressView!!.setVisibility(View.GONE)
            val vg = progressView!!.getParent() as ViewGroup
            vg.removeView(progressView)
            progressView = null
        }
    }
}

object BaseUtils {

    lateinit var yearMonthDate_Format: SimpleDateFormat
    lateinit var HourMinuteSeconds: SimpleDateFormat

    fun CurrentDate_ymd(): String {
        yearMonthDate_Format = SimpleDateFormat("yyyy-MM-dd")
        val c = Calendar.getInstance().getTime();
        return yearMonthDate_Format.format(c).toString()
    }

    fun Currenttime(): String {
        HourMinuteSeconds = SimpleDateFormat("HH:mm:ss")
        val c = Calendar.getInstance().getTime();
        return HourMinuteSeconds.format(c).toString()
    }

    fun isValidMail(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    fun isValidMobile(phone: String): Boolean {
        return phone.length >= 10 && android.util.Patterns.PHONE.matcher(phone).matches()
    }

    fun isValidPwd(password: String): Boolean {
        return password.length >= 8 && password.matches("^(?=.*?[A-Z-a-z])(?=.*?[0-9])(?=.*?[#?₹!@$%^&*-]).{8,}$".toRegex())
    }

    fun setSharedPrefs(c: Context, key: String, value: String) {

        val editor = c.getSharedPreferences(
            Constants.UserPref,
            Context.MODE_PRIVATE
        ).edit()
        editor.putString(key, value)
        editor.commit()

    }

    fun showKeyboard(c: Context) {
        val inputMethodManager = c!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
    }

    fun closeKeyboard(c: Context) {
        val inputMethodManager = c!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0)
    }

    fun setSharedPrefs(c: Context?, key: String, value: Int) {
        if (c != null) {
            val editor = c.getSharedPreferences(
                Constants.UserPref,
                Context.MODE_PRIVATE
            ).edit()
            editor.putInt(key, value)
            editor.apply()
        }
    }

    fun setSharedPrefs(c: Context?, key: String, value: Boolean) {
        if (c != null) {
            val editor = c.getSharedPreferences(
                Constants.UserPref,
                Context.MODE_PRIVATE
            ).edit()
            editor.putBoolean(key, value)
            editor.apply()
        }
    }

    fun getSharedPrefs(c: Context?, key: String, default_value: String): String? {
        if (c == null) {
            return default_value
        } else {
            val prefs = c.getSharedPreferences(
                Constants.UserPref,
                Context.MODE_PRIVATE
            )
            return prefs.getString(key, default_value)
        }

    }

    fun getSharedPrefs(c: Context?, key: String, default_value: Int): Int {
        if (c == null) {
            return default_value
        } else {
            val prefs = c.getSharedPreferences(
                Constants.UserPref,
                Context.MODE_PRIVATE
            )
            return prefs.getInt(key, default_value)
        }
    }

    fun getSharedPrefs(c: Context?, key: String, default: Boolean): Boolean {
        if (c == null) {
            return false
        } else {
            val prefs = c.getSharedPreferences(
                Constants.UserPref,
                Context.MODE_PRIVATE
            )
            return prefs.getBoolean(key, default)
        }
    }

    fun convertImageToString(bm: Bitmap): String {
        //MyFunctions.sop("original size is "+byteSizeOf(bm));
        val baos = ByteArrayOutputStream()
        bm.compress(Bitmap.CompressFormat.JPEG, 25, baos)
        val options = BitmapFactory.Options()
        options.inPreferredConfig = Bitmap.Config.RGB_565
        val decoded = BitmapFactory.decodeStream(
            ByteArrayInputStream(baos.toByteArray()),
            null, options
        )
        val photo = baos.toByteArray()

        return Base64.encodeToString(photo, Base64.DEFAULT)

    }

    fun getPath(uri: Uri, activity: Activity): String? {

        val projection = arrayOf(MediaStore.MediaColumns.DATA)
        val cursor = activity
            .managedQuery(uri, projection, null, null, null)
        if (cursor != null) {
            val column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA)
            cursor.moveToFirst()
            return cursor.getString(column_index)
        } else {
            return uri.path
        }

    }

    fun hideKeyboard(mContext: Context) {
        val imm = mContext
            .getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(
            (mContext as Activity).window
                .currentFocus!!.windowToken, 0
        )
    }


}
