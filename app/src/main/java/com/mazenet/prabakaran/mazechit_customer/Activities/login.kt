package com.mazenet.prabakaran.mazechit_customer.Activities

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.app.KeyguardManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.hardware.fingerprint.FingerprintManager
import android.os.*
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyPermanentlyInvalidatedException
import android.security.keystore.KeyProperties
import android.support.annotation.RequiresApi
import android.support.constraint.ConstraintLayout
import android.support.v4.app.ActivityCompat
import android.text.TextUtils
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import com.mazenet.prabakaran.mazechit_customer.Model.SigninModel
import com.mazenet.prabakaran.mazechit_customer.R
import com.mazenet.prabakaran.mazechit_customer.Retrofit.ICallService
import com.mazenet.prabakaran.mazechit_customer.Retrofit.RetrofitBuilder
import kotlinx.android.synthetic.main.activity_login.*
import com.mazenet.prabakaran.mazechit_customer.utilities.Constants
import com.mazenet.prabakaran.mazechit_customer.utilities.DrawableClickListener
import com.mazenet.prabakaran.mazechit_customer.utilities.FingerprintHandler
import kotlinx.android.synthetic.main.activity_login.fingerprint_layout
import retrofit2.Call
import retrofit2.Response
import java.io.IOException
import java.security.*
import java.security.cert.CertificateException
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.NoSuchPaddingException
import javax.crypto.SecretKey

class login : BaseActivity() {
    private val TAG = login::class.java.getSimpleName()
    lateinit var KEY_NAME: String
    private var cipher: Cipher? = null
    private var keyStore: KeyStore? = null
    private var keyGenerator: KeyGenerator? = null
    private var cryptoObject: FingerprintManager.CryptoObject? = null
    private var fingerprintManager: FingerprintManager? = null
    private var keyguardManager: KeyguardManager? = null
    internal lateinit var fingersensor_text: TextView
    internal var Fingerprint_broadcast: Boolean = false
    internal lateinit var v: Vibrator
    var showingPassword: Boolean = false
    internal var fingerprint_login_popup: PopupWindow? = null
    lateinit var pb_login: ProgressBar
    lateinit var edt_userid: EditText
    lateinit var edt_password: EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        toast("Enter Registerd User-ID or Mobile No. and Password to Login")
        v = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        KEY_NAME = Constants.str_keyvariable
        pb_login = findViewById(R.id.pb_login) as ProgressBar
        edt_userid = findViewById<EditText>(R.id.edt_userid)
        edt_password = findViewById<EditText>(R.id.edt_password)
        btn_signin.setOnClickListener {
            val email = edt_userid.text.toString()
            val password = edt_password.text.toString()
            if(email=="9876543210"){
                setPrefsString(Constants.DB,"vannamayil_chits")
            }
            else{
                setPrefsString(Constants.DB,"vannamayil_chits")
            }
            when {
                TextUtils.isEmpty(email) -> Constants.showToast("Enter Valid User Id or Mobile No.", this@login)
                TextUtils.isEmpty(password) -> Constants.showToast("Invalid Passsword", this@login)
                else -> checklogin(email, password, false)
            }
        }
        txt_forgot_pass.setOnClickListener {
            val i = Intent(this, SignUp::class.java)
            startActivity(i)
        }
//        initfingerprint()
//        fingerprint_layout.setOnClickListener {
//
//            initfingerprint()
//            if (getPrefsString(
//                    Constants.fingerprint_set,
//                    "no"
//                ).equals("yes")
//            ) {
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                    showfinger()
//                }
//            } else {
//                loginfirst(this@login)
//            }
//        }
        edt_password.setOnTouchListener(object : DrawableClickListener.RightDrawableClickListener(edt_password) {
            override fun onDrawableClick(): Boolean {
                if (!showingPassword) {
                    edt_password.setCompoundDrawablesRelativeWithIntrinsicBounds(
                        0, 0,
                        R.drawable.ic_eye_off, 0
                    )
                    showingPassword = true

                    edt_password.transformationMethod = PasswordTransformationMethod()
                } else {
                    edt_password.setCompoundDrawablesRelativeWithIntrinsicBounds(
                        0, 0,
                        R.drawable.ic_eye_on, 0
                    )
                    showingPassword = false
                    edt_password.setTransformationMethod(null);
                }
                return true
            }
        })
    }

    private fun checklogin(email: String, password: String, fingerprinted: Boolean) {
        pb_login.visibility = View.VISIBLE
        val checklogin: ICallService = RetrofitBuilder.buildservice(ICallService::class.java)
        val loginparameters = HashMap<String, String>()
        loginparameters.put("user_id", email)
        loginparameters.put("password", password)
        loginparameters.put("db",getPrefsString(Constants.DB,""))
        val RequestCall = checklogin.sign_in(loginparameters)
        RequestCall.enqueue(object : retrofit2.Callback<SigninModel> {
            override fun onFailure(call: Call<SigninModel>, t: Throwable) {
                pb_login.visibility = View.GONE
                t.printStackTrace()
            }

            override fun onResponse(call: Call<SigninModel>, response: Response<SigninModel>) {
                if (response.isSuccessful) {
                    when {
                        response.code().equals(200) -> {
                            pb_login.visibility = View.GONE
                            System.out.println("response token  ${response.body()?.getStatus()}")
                            if (response.body()?.getStatus().equals("Signup")) {
                                toast(response.body()!!.getMsg().toString())
                                goToSignup(email)
                            } else if (response.body()?.getStatus().equals("Error")) {
                                toast(response.body()!!.getMsg().toString())
                            } else if (response.body()?.getStatus().equals("Success")) {
                                if (fingerprinted) {
                                    setPrefsString(
                                        Constants.CUST_PID,
                                        //mounika
                                        response.body()!!.getCustomerPrimaryId().toString()
                                    )
                                    setPrefsString(Constants.CUST_NAME, response.body()!!.getCustomerName().toString())
                                    setPrefsString(Constants.TENANT_ID, response.body()!!.getTenantId().toString())
                                    setPrefsString(Constants.TENANT_NAME, response.body()!!.getTenantName().toString())
                                    setPrefsString(Constants.TENANT_ADD1, response.body()!!.getTenantAddress1().toString())
                                    setPrefsString(Constants.TENANT_ADD2, response.body()!!.getTenantAddress2().toString())
                                    setPrefsString(Constants.TENANT_DISTRICT, response.body()!!.getDistrictName().toString())
                                    setPrefsString(Constants.TENANT_MOBILENO, response.body()!!.getMobileNo().toString())
                                    setPrefsString(Constants.TENANT_PHONE, response.body()!!.getPhoneNo().toString())
                                    setPrefsString(Constants.TENANT_STATE, response.body()!!.getStateName().toString())
                                    setPrefsString(Constants.TENANT_PINCODE, response.body()!!.getPincode().toString())
                                    setPrefsString(Constants.BRANCH_ID, response.body()!!.getBranchId().toString())
                                    setPrefsString(Constants.BRANCH_NAME, response.body()!!.getBranchName().toString())
                                    setPrefsString(Constants.PROFILE_IMAGE, response.body()!!.getProfile_photo().toString())
                                    setPrefsString(Constants.CUST_CODE, response.body()!!.getCustomer_code().toString())
                                    showfinger()
                                } else {
                                    setPrefsString(Constants.fingerprint_set, "no")
                                    setPrefsString(
                                        Constants.CUST_PID,
                                        response.body()!!.getCustomerPrimaryId().toString()
                                    )
                                    setPrefsString(Constants.CUST_NAME, response.body()!!.getCustomerName().toString())
                                    setPrefsString(Constants.TENANT_ID, response.body()!!.getTenantId().toString())
                                    setPrefsString(Constants.TENANT_NAME, response.body()!!.getTenantName().toString())
                                    setPrefsString(Constants.TENANT_ADD1, response.body()!!.getTenantAddress1().toString())
                                    setPrefsString(Constants.TENANT_ADD2, response.body()!!.getTenantAddress2().toString())
                                    setPrefsString(Constants.TENANT_DISTRICT, response.body()!!.getDistrictName().toString())
                                    setPrefsString(Constants.TENANT_MOBILENO, response.body()!!.getMobileNo().toString())
                                    setPrefsString(Constants.TENANT_PHONE, response.body()!!.getPhoneNo().toString())
                                    setPrefsString(Constants.TENANT_STATE, response.body()!!.getStateName().toString())
                                    setPrefsString(Constants.TENANT_PINCODE, response.body()!!.getPincode().toString())
                                    setPrefsString(Constants.BRANCH_ID, response.body()!!.getBranchId().toString())
                                    setPrefsString(Constants.BRANCH_NAME, response.body()!!.getBranchName().toString())
                                    setPrefsString(Constants.PROFILE_IMAGE, response.body()!!.getProfile_photo().toString())
                                    setPrefsString(Constants.CUST_CODE, response.body()!!.getCustomer_code().toString())
                                    goToHome()
                                }

                            }
                        }
                        response.code().equals(401) -> toast(response.body()!!.getMsg().toString())
                        response.code().equals(500) -> toast("Internal server Error")

                    }
                } else {
                    pb_login.visibility = View.GONE
                    when {
                        response.code().equals(401) -> toast(response.message())
                        response.code().equals(500) -> toast("Internal server Error")
                    }
                    Log.e(TAG, "Response not Successfull")
                }

            }


        })
    }

    fun initfingerprint() {
        // If you’ve set your app’s minSdkVersion to anything lower than 23, then you’ll need to verify that the device is running Marshmallow
        // or higher before executing any fingerprint-related code
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //Get an instance of KeyguardManager and FingerprintManager//
            keyguardManager = getSystemService(Context.KEYGUARD_SERVICE) as KeyguardManager
            fingerprintManager = getSystemService(Context.FINGERPRINT_SERVICE) as FingerprintManager


            //Check whether the device has a fingerprint sensor//
            if (!fingerprintManager!!.isHardwareDetected()) {
                // If a fingerprint sensor isn’t available, then inform the user that they’ll be unable to use your app’s fingerprint functionality//
                Constants.showToast(Constants.str_doesnt_support_fingerprint.toString(), this@login)
                fingerprint_layout.visibility = View.GONE
            } else {
                //Check whether the user has granted your app the USE_FINGERPRINT permission//
                if (ActivityCompat.checkSelfPermission(
                        this@login,
                        Manifest.permission.USE_FINGERPRINT
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    // If your app doesn't have this permission, then display the following text//
                    Constants.showToast(Constants.str_enable_fingerprint_permission.toString(), this@login)
                    fingerprint_layout.visibility = View.GONE
                } else {
                    //Check that the user has registered at least one fingerprint//
                    if (!fingerprintManager!!.hasEnrolledFingerprints()) {
                        // If the user hasn’t configured any fingerprints, then display the following message//
                        Constants.showToast(Constants.str_fingerprint_not_configured.toString(), this@login)
                        fingerprint_layout.visibility = View.GONE
                    } else {
                        //Check that the lockscreen is secured//
                        if (!keyguardManager!!.isKeyguardSecure()) {
                            // If the user hasn’t secured their lockscreen with a PIN password or pattern, then display the following text//
                            Constants.showToast(Constants.str_enable_security.toString(), this@login)
                            fingerprint_layout.visibility = View.GONE
                            //                fingersensor_text.setText("Please enable lockscreen security in your device's Settings");
                        } else {
                            try {
                                generateKey()
                            } catch (e: FingerprintException) {
                                e.printStackTrace()
                            }

                            if (initCipher()) {
                                //If the cipher is initialized successfully, then create a CryptoObject instance//
                                cryptoObject = cipher?.let { FingerprintManager.CryptoObject(it) }
                                fingerprint_layout.visibility = View.VISIBLE
                                // Here, I’m referencing the FingerprintHandler class that we’ll create in the next section. This class will be responsible
                                // for starting the authentication process (via the startAuth method) and processing the authentication process events//

                            }
                        }
                    }


                }


            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun initCipher(): Boolean {
        try {
            //Obtain a cipher instance and configure it with the properties required for fingerprint authentication//
            cipher = Cipher.getInstance(
                KeyProperties.KEY_ALGORITHM_AES + "/"
                        + KeyProperties.BLOCK_MODE_CBC + "/"
                        + KeyProperties.ENCRYPTION_PADDING_PKCS7
            )
        } catch (e: NoSuchAlgorithmException) {
            throw RuntimeException("Failed to get Cipher", e)
        } catch (e: NoSuchPaddingException) {
            throw RuntimeException("Failed to get Cipher", e)
        }

        try {
            keyStore!!.load(
                null
            )
            val key = keyStore!!.getKey(KEY_NAME.toString(), null) as SecretKey
            cipher!!.init(Cipher.ENCRYPT_MODE, key)
            //Return true if the cipher has been initialized successfully//
            return true
        } catch (e: KeyPermanentlyInvalidatedException) {

            //Return false if cipher initialization failed//
            return false
        } catch (e: KeyStoreException) {
            throw RuntimeException("Failed to init Cipher", e)
        } catch (e: CertificateException) {
            throw RuntimeException("Failed to init Cipher", e)
        } catch (e: UnrecoverableKeyException) {
            throw RuntimeException("Failed to init Cipher", e)
        } catch (e: IOException) {
            throw RuntimeException("Failed to init Cipher", e)
        } catch (e: NoSuchAlgorithmException) {
            throw RuntimeException("Failed to init Cipher", e)
        } catch (e: InvalidKeyException) {
            throw RuntimeException("Failed to init Cipher", e)
        }

    }

    private inner class FingerprintException(e: Exception) : Exception(e)

    //Create the generateKey method that we’ll use to gain access to the Android keystore and generate the encryption key//

    @Throws(FingerprintException::class)
    private fun generateKey() {
        try {
            // Obtain a reference to the Keystore using the standard Android keystore container identifier (“AndroidKeystore”)//
            keyStore = KeyStore.getInstance("AndroidKeyStore")

            //Generate the key//
            keyGenerator = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, "AndroidKeyStore")

            //Initialize an empty KeyStore//
            keyStore!!.load(null)

            //Initialize the KeyGenerator//
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                keyGenerator!!.init(
                    //Specify the operation(s) this key can be used for//
                    KeyGenParameterSpec.Builder(
                        KEY_NAME.toString(),
                        KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
                    )
                        .setBlockModes(KeyProperties.BLOCK_MODE_CBC)

                        //Configure this key so that the user has to confirm their identity with a fingerprint each time they want to use it//
                        .setUserAuthenticationRequired(true)
                        .setEncryptionPaddings(
                            KeyProperties.ENCRYPTION_PADDING_PKCS7
                        )
                        .build()
                )
            }

            //Generate the key//
            keyGenerator!!.generateKey()

        } catch (exc: KeyStoreException) {
            exc.printStackTrace()
            throw FingerprintException(exc)
        } catch (exc: NoSuchAlgorithmException) {
            exc.printStackTrace()
            throw FingerprintException(exc)
        } catch (exc: NoSuchProviderException) {
            exc.printStackTrace()
            throw FingerprintException(exc)
        } catch (exc: InvalidAlgorithmParameterException) {
            exc.printStackTrace()
            throw FingerprintException(exc)
        } catch (exc: CertificateException) {
            exc.printStackTrace()
            throw FingerprintException(exc)
        } catch (exc: IOException) {
            exc.printStackTrace()
            throw FingerprintException(exc)
        }

    }


    @RequiresApi(Build.VERSION_CODES.M)
    fun showfinger() {
        val dialogBuilder = AlertDialog.Builder(this)
        // ...Irrelevant code for customizing the buttons and title
        val inflater = this.layoutInflater
        val dialogView = inflater.inflate(R.layout.fingerprintdilog, null)
        dialogBuilder.setView(dialogView)

        val textView = dialogView.findViewById(R.id.txt_cancel) as TextView
        fingersensor_text = dialogView.findViewById(R.id.txt_warm) as TextView
        val alertDialog = dialogBuilder.create()
        alertDialog.show()
        val helper = FingerprintHandler(this@login)
        fingerprintManager?.let { cryptoObject?.let { it1 -> helper.startAuth(it, it1) } }
        textView.setOnClickListener {
            FingerprintHandler.stopFingerAuth()
            alertDialog.dismiss()
        }
    }


    fun vibrate(count: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val mVibratePattern = longArrayOf(50, 100)
            if (count.equals("1", ignoreCase = true)) {
                v.vibrate(VibrationEffect.createOneShot(100, VibrationEffect.DEFAULT_AMPLITUDE))

            } else if (count.equals("2", ignoreCase = true)) {
                v.vibrate(VibrationEffect.createWaveform(mVibratePattern, 2))
            }

        } else {
            if (count.equals("1", ignoreCase = true)) {
                //deprecated in API 26
                v.vibrate(50)
            } else if (count.equals("2", ignoreCase = true)) {
                v.vibrate(50)
                v.vibrate(50)

            }
        }
    }

    override fun onResume() {
        if (!Fingerprint_broadcast) {
            try {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                    fingerprintManager = getSystemService(Context.FINGERPRINT_SERVICE) as FingerprintManager

                    if (fingerprintManager!!.isHardwareDetected()) {
                        registerReceiver(
                            broadcastBufferReceiver, IntentFilter(
                                FingerprintHandler.FINGERPRINT_INTENT
                            )
                        )

                    } else {
                        Constants.showToast(Constants.str_doesnt_support_fingerprint.toString(), this@login)
                        fingerprint_layout.visibility = View.GONE
                    }
                }

            } catch (e: Exception) {
                e.printStackTrace()
            }

            Fingerprint_broadcast = true
        }
        super.onResume()
    }

    private val broadcastBufferReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, bufferIntent: Intent) {
            try {
                showPD(bufferIntent)
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }
    }

    private fun showPD(bufferIntent: Intent) {
        val bufferValue = bufferIntent.getStringExtra("result")
        val bufferIntValue = Integer.parseInt(bufferValue)
        when (bufferIntValue) {
            0 -> if (getPrefsString(Constants.fingerprint_set, "no")!!.equals("no")) {
                setPrefsString(Constants.fingerprint_set, "yes")
                setPrefsString(Constants.application_logged_in, "yes")
                fingersensor_text.text = "Authenticated"
                vibrate("1")
                goToHome()

            } else {

                vibrate("1")
                fingersensor_text.text = "Authenticated"
                setPrefsString(Constants.fingerprint_set, "yes")
                setPrefsString(Constants.application_logged_in, "yes")
                goToHome()
            }


            1 -> {
            }

            2 -> {
                vibrate("2")
                fingersensor_text.text = "Try Again"
            }
        }
    }

    fun goToHome() {
        toast("Logged in successfully")
        setPrefsString(Constants.application_logged_in,"yes")
        setPrefsInt(Constants.First_time, 1)
        val intent: Intent
        intent = Intent(
            this@login,
            HomeActivity::class.java
        )
        startActivity(intent)
        finish()
    }

    fun goToSignup(email: String) {


        val intent: Intent
        intent = Intent(
            this@login,
            SignUp::class.java
        )
        intent.putExtra("email", email)
        startActivity(intent)
        finish()
    }

    fun loginfirst(context: Activity) {

        val layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val viewGroup = context.findViewById(R.id.loginfirstlayout) as? ConstraintLayout
        val layout = layoutInflater.inflate(R.layout.loginfirst, viewGroup)

        fingerprint_login_popup = PopupWindow(context)
        fingerprint_login_popup!!.setContentView(layout)
        fingerprint_login_popup!!.setWidth(RelativeLayout.LayoutParams.MATCH_PARENT)
        fingerprint_login_popup!!.setHeight(RelativeLayout.LayoutParams.MATCH_PARENT)
        fingerprint_login_popup!!.setAnimationStyle(R.style.AnimationPopup);
        fingerprint_login_popup!!.setFocusable(true)
        fingerprint_login_popup!!.setBackgroundDrawable(null)
        fingerprint_login_popup!!.showAtLocation(layout, Gravity.CENTER, 0, 0)


        val edt_password = layout.findViewById(R.id.edt_password) as? EditText
        val edt_email = layout.findViewById(R.id.edt_userid) as? EditText
        val yes = layout.findViewById(R.id.btn_login) as Button

        var showingPassword = false
        edt_password!!.setOnTouchListener(object : DrawableClickListener.RightDrawableClickListener(edt_password) {
            override fun onDrawableClick(): Boolean {
                if (!showingPassword) {
                    edt_password!!.setCompoundDrawablesRelativeWithIntrinsicBounds(
                        0, 0,
                        R.drawable.ic_eye_off, 0
                    )
                    showingPassword = true

                    edt_password!!.transformationMethod = PasswordTransformationMethod()
                } else {
                    edt_password!!.setCompoundDrawablesRelativeWithIntrinsicBounds(
                        0, 0,
                        R.drawable.ic_eye_on, 0
                    )
                    showingPassword = false
                    edt_password!!.setTransformationMethod(null);
                }
                return true
            }
        })
        edt_password!!.setOnEditorActionListener(TextView.OnEditorActionListener { v, actionId, event ->
            val email = edt_email!!.text.toString()
            val password = edt_password!!.text.toString()

            when {
                TextUtils.isEmpty(email) -> Constants.showToast("Enter Valid User ID or Mobile No.", this@login)
                TextUtils.isEmpty(password) -> Constants.showToast("Invalid Passsword", this@login)
                else -> checklogin(email, password, true)
            }
            true
        })
        yes.setOnClickListener {
            val email = edt_email!!.text.toString()
            val password = edt_password!!.text.toString()

            when {
                TextUtils.isEmpty(email) -> Constants.showToast("Enter Valid User ID or Mobile No.", this@login)
                TextUtils.isEmpty(password) -> Constants.showToast("Invalid Passsword", this@login)
                else -> checklogin(email, password, true)
            }
        }
        fingerprint_login_popup!!.setOnDismissListener {

        }

    }
}
