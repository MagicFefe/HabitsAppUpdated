package com.swaptech.habitstwo.navigation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.IntentFilter
import android.graphics.drawable.ColorDrawable
import android.net.ConnectivityManager
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.navigation.ui.*
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import com.swaptech.habitstwo.CommonNetworkReceiver
import com.swaptech.habitstwo.R
import com.swaptech.habitstwo.actionwithhabit.AddFragment
import com.swaptech.habitstwo.implofelements.ViewPagerAdapter
import com.swaptech.habitstwo.listhabits.HabitsFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_about.*
import kotlinx.android.synthetic.main.fragment_habits.*


class MainActivity : AppCompatActivity()/*, NavigationView.OnNavigationItemSelectedListener */ {

    //private lateinit var toggle: ActionBarDrawerToggle
    private lateinit var appBarConfiguration: AppBarConfiguration
    /*
    private val mOnNavigationViewClickListener = BottomNavigationView.OnNavigationItemSelectedListener { menuItem ->
        when(menuItem.itemId) {
            R.id.navigation_habits -> {
                //openFragment(HabitsFragment.newInstance())
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_add -> {
                openFragment(AddFragment.newInstance())
                return@OnNavigationItemSelectedListener true
            }
            else -> return@OnNavigationItemSelectedListener false
        }
    }

     */
    private fun openFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.nav_host_fragment, fragment)
        transaction.commit()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        setSupportActionBar(tool_bar)

        //toggle = ActionBarDrawerToggle(this, main_layout, tool_bar, R.string.about_item_nav_drawer, R.string.home_item_nav_drawer)
        //main_layout.addDrawerListener(toggle)


        //supportActionBar?.setDisplayHomeAsUpEnabled(true)
        //supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_menu_24)
        //supportActionBar?.setBackgroundDrawable(ColorDrawable(ContextCompat.getColor(this, R.color.white)))

        //bottom_navigation_drawer.setOnNavigationItemSelectedListener(mOnNavigationViewClickListener)

        //nav_drawer.setNavigationItemSelectedListener(this)


        val navController = findNavController(R.id.nav_host_fragment)

        appBarConfiguration = AppBarConfiguration(setOf(R.id.habitsFragment, R.id.addFragment, R.id.aboutFragment), main_layout)



        setupActionBarWithNavController(navController, appBarConfiguration)
        nav_drawer.setupWithNavController(navController)



        registerReceiver(CommonNetworkReceiver(), IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))



        //openFragment(HabitsFragment.newInstance())
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onBackPressed() {
        if(supportFragmentManager.fragments.last() == AddFragment.newInstance())  {
            findNavController(R.id.nav_host_fragment).navigate(R.id.action_addFragment_to_habitsFragment)
        } else {
            super.onBackPressed()
        }

    }
}