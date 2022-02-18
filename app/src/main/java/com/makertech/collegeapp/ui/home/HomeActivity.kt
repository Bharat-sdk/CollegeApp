package com.makertech.collegeapp.ui.home

import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.makertech.collegeapp.R
import com.makertech.collegeapp.databinding.ActivityHomeBinding
import com.makertech.collegeapp.other.AppConstants.KEY_DEPARTMENT
import com.makertech.collegeapp.other.AppConstants.KEY_LOGGED_IN_EMAIL
import com.makertech.collegeapp.other.AppConstants.KEY_NAME
import com.makertech.collegeapp.other.AppConstants.KEY_PASSWORD
import com.makertech.collegeapp.other.AppConstants.KEY_SEMESTER
import com.makertech.collegeapp.other.AppConstants.KEY_UID
import com.makertech.collegeapp.other.AppConstants.NO_EMAIL
import com.makertech.collegeapp.other.AppConstants.NO_PASSWORD
import com.makertech.collegeapp.other.Status
import com.makertech.collegeapp.ui.login.LoginFragment
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeActivity: AppCompatActivity() {
    lateinit var binding: ActivityHomeBinding

    private val viewModel:HomeViewModel by viewModels()

    @Inject
    lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        observe()

        val navController = findNavController(R.id.navHostFragment)
        val appBarConfiguration= AppBarConfiguration(setOf(R.id.postFragment,R.id.workFragment,R.id.mapFragment,R.id.aboutUsFragment))
        binding.bottomNavigation.setupWithNavController(navController)
        setupActionBarWithNavController(navController,appBarConfiguration)
        viewModel.getStudentDetails()

    }

    private fun logOut(){
        sharedPreferences.edit().putString(KEY_LOGGED_IN_EMAIL, NO_EMAIL).apply()
        sharedPreferences.edit().putString(KEY_PASSWORD, NO_PASSWORD).apply()
        val i = Intent(applicationContext,LoginFragment::class.java)
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(i)
    }

    fun observe(){
        viewModel.studentDetails.observe(this, Observer { result->
            result?.let {
                when(result.status)
                {
                    Status.SUCCESS ->{

                        sharedPreferences.edit().putString(KEY_NAME, result.data?.name).apply()
                        sharedPreferences.edit().putString(KEY_DEPARTMENT, result.data?.department).apply()
                        sharedPreferences.edit().putString(KEY_SEMESTER, result.data?.semester).apply()
                        sharedPreferences.edit().putString(KEY_UID, result.data?.id).apply()

                    }
                    Status.ERROR ->{
                    }
                    Status.LOADING ->{
                    }
                }
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_logout,menu)
        return super.onCreateOptionsMenu(menu)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId)
        {
            R.id.menu_logout ->logOut()
        }
        return super.onOptionsItemSelected(item)
    }
}