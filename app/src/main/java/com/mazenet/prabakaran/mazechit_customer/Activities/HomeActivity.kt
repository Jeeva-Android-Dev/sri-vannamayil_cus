package com.mazenet.prabakaran.mazechit_customer.Activities

import android.app.AlertDialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.Snackbar
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.view.MenuItem
import android.support.v4.widget.DrawerLayout
import android.support.design.widget.NavigationView
import android.support.v7.widget.Toolbar
import android.view.Menu
import com.mazenet.prabakaran.mazechit_customer.Fragments.*
import com.mazenet.prabakaran.mazechit_customer.R
import com.mazenet.prabakaran.mazechit_customer.utilities.Constants
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_home2.*
import kotlinx.android.synthetic.main.app_bar_home.*
import kotlinx.android.synthetic.main.nav_header_home.view.*


class HomeActivity : BaseActivity(), NavigationView.OnNavigationItemSelectedListener {

    val manager = supportFragmentManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.mazenet.prabakaran.mazechit_customer.R.layout.activity_home2)
        val toolbar: Toolbar = findViewById(com.mazenet.prabakaran.mazechit_customer.R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar!!.setTitle(com.mazenet.prabakaran.mazechit_customer.R.string.app_name)
        val fab: FloatingActionButton = findViewById(com.mazenet.prabakaran.mazechit_customer.R.id.fab)
        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
        val drawerLayout: DrawerLayout = findViewById(com.mazenet.prabakaran.mazechit_customer.R.id.drawer_layout)
        val navView: NavigationView = findViewById(com.mazenet.prabakaran.mazechit_customer.R.id.nav_view)
       //navView.menu.getItem()
        val toggle = ActionBarDrawerToggle(
            this,
            drawerLayout,
            toolbar,
            com.mazenet.prabakaran.mazechit_customer.R.string.navigation_drawer_open,
            com.mazenet.prabakaran.mazechit_customer.R.string.navigation_drawer_close
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        navView.setNavigationItemSelectedListener(this)
        replacefragment(Fragment_Dashboard())
        val navigationView = findViewById(R.id.nav_view) as NavigationView

        val navigation_header_texts = navigationView.getHeaderView(0)
        val profile = getPrefsString(Constants.PROFILE_IMAGE, "")
        if (profile.isNullOrEmpty()) {

        } else {
            Picasso.with(this@HomeActivity).load(profile).resize(50, 50).onlyScaleDown()
                .placeholder(
                    R.drawable.ic_person
                ).error(R.drawable.ic_person)
                .centerInside().into(navigation_header_texts.imageView)
        }

        navigation_header_texts.txt_username.setText(getPrefsString(Constants.CUST_NAME, ""))
        navigation_header_texts.txt_usercode.setText(getPrefsString(Constants.CUST_CODE, ""))

    }

    fun setActionBarTitle(title: String) {
        supportActionBar!!.title = title
    }

    fun setActionBarColor(color: Int) {
        supportActionBar!!.setBackgroundDrawable(ColorDrawable(resources.getColor(color)));
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(com.mazenet.prabakaran.mazechit_customer.R.menu.home, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            com.mazenet.prabakaran.mazechit_customer.R.id.menu_notification -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            com.mazenet.prabakaran.mazechit_customer.R.id.nav_home -> {
                replacefragment(Fragment_Dashboard())
            }
            com.mazenet.prabakaran.mazechit_customer.R.id.nav_mychits -> {
                replacefragment(Mychits())
            }
            com.mazenet.prabakaran.mazechit_customer.R.id.nav_acntcopy -> {
                replacefragment(my_acnt_copy())
            }
            com.mazenet.prabakaran.mazechit_customer.R.id.nav_newchits -> {
                replacefragment(NewChits())
            }
            com.mazenet.prabakaran.mazechit_customer.R.id.nav_settings -> {

            }
            R.id.nav_change_password -> {
                replacefragment(ChangePassword())
            }
            com.mazenet.prabakaran.mazechit_customer.R.id.nav_logout -> {
                logoutdb()
            }
        }
        val drawerLayout: DrawerLayout = findViewById(com.mazenet.prabakaran.mazechit_customer.R.id.drawer_layout)
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    fun replacefragment(fragment: BaseFragment) {
        val transaction = manager.beginTransaction()
        transaction.replace(com.mazenet.prabakaran.mazechit_customer.R.id.frame_container, fragment)
        transaction.addToBackStack("0")
        transaction.commit()
    }

    fun logoutdb() {
        setPrefsString(Constants.application_logged_in, "no")
        val intent = Intent(this@HomeActivity, splash::class.java)
        startActivity(intent)
        finish()
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            val fragmentList = manager.fragments
            if (fragmentList.size > 0) {
                val fragments = manager
                if (fragments.getBackStackEntryCount() > 1) {
                    // We have fragments on the backstack that are poppable
                    val f = supportFragmentManager.findFragmentById(R.id.frame_container)
                    fragments.popBackStackImmediate()
                } else {
                    val builder = AlertDialog.Builder(this@HomeActivity, R.style.MyErrorDialogTheme)
                    builder.setCancelable(false)
                    builder.setTitle("Caution")
                        .setMessage("Do you want exit the application?")
                        .setNegativeButton("No") { dialog, which ->
                            dialog.dismiss()
                        }
                        .setPositiveButton("Yes") { dialog, which ->
                            dialog.dismiss()
                            supportFinishAfterTransition()
                        }
                    builder.create().show()

                }
            }
        }
    }
}
