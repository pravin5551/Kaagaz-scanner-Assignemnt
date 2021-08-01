package com.example.kaagazscanner.activities

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.airbnb.lottie.LottieAnimationView
import com.example.kaagazscanner.R
import com.example.kaagazscanner.fragments.HomeFragment
import com.example.kaagazscanner.fragments.PdfToolsFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView
import com.google.mlkit.vision.common.InputImage

class HomeActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    //  All variable and Objects declared here for future scope
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var toggle: ActionBarDrawerToggle

    private lateinit var toolbar: androidx.appcompat.widget.Toolbar

    var navigationFragment: Fragment? = null
    private lateinit var button: FloatingActionButton
    lateinit var lottie :LottieAnimationView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        lottie = findViewById(R.id.lottie)
        button = findViewById(R.id.fab)

        //launching MainActivity from HomeActivity
        button.setOnClickListener {
            // Launch the scanner activity
            intent = Intent(applicationContext, MainActivity::class.java)
            startActivity(intent)
        }
        bottomNavigationWorking()

        toolbar = findViewById(R.id.toolbar0)
        setSupportActionBar(toolbar)

        drawerLayout = findViewById(R.id.drawer)

        toggle = ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

    }

    /**
     * Here I have implimented bottomNavigationWorking so onclick on any bottom button new fragment will open
     */
    private fun bottomNavigationWorking() {
        val bottomNavigationView =
            findViewById<View>(R.id.bottomNavigationView) as BottomNavigationView
        bottomNavigationView.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.home_navigation -> {
                    navigationFragment = HomeFragment()
                }
                R.id.pdfTools_navigation -> {
                    navigationFragment = PdfToolsFragment()
                }
            }

            //stopping lottie animation after launching fragments
            lottie.setVisibility(View.INVISIBLE);
            navigationFragment?.let { loadFragment(it) }
            return@setOnNavigationItemSelectedListener true
        }
    }


    //fragment launcher
    private fun loadFragment(navigationFragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(
            R.id.flContainer,
            navigationFragment
        ).commit()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {

        }
        drawerLayout.closeDrawer(GravityCompat.START)
        navigationFragment?.let { loadFragment(it) }
        return true
    }


    // Here i'm trying to pause the lottie if this activity gets stop or pause
    override fun onPause() {
        super.onPause()
        lottie.cancelAnimation()
    }

    override fun onDestroy() {
        super.onDestroy()
        lottie.cancelAnimation()
    }
}