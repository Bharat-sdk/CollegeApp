package com.makertech.tnuteacherapp.ui.home

import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.makertech.tnuteacherapp.R
import com.makertech.tnuteacherapp.data.remote.request.AttendancePerStudent
import com.makertech.tnuteacherapp.databinding.ActivityHomeBinding
import com.makertech.tnuteacherapp.other.AppConstants.KEY_LOGGED_IN_EMAIL
import com.makertech.tnuteacherapp.other.AppConstants.KEY_NAME
import com.makertech.tnuteacherapp.other.AppConstants.KEY_PASSWORD
import com.makertech.tnuteacherapp.other.AppConstants.KEY_SUBJECT
import com.makertech.tnuteacherapp.other.AppConstants.KEY_UID
import com.makertech.tnuteacherapp.other.AppConstants.NO_EMAIL
import com.makertech.tnuteacherapp.other.AppConstants.NO_PASSWORD
import com.makertech.tnuteacherapp.other.Status
import com.makertech.tnuteacherapp.ui.attendance.TeacherAttendanceAdapter
import com.makertech.tnuteacherapp.ui.login.LoginActivity
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
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
        //Bottom Navigation
        val navController = findNavController(R.id.navHostFragment)
        val appBarConfiguration= AppBarConfiguration(setOf(R.id.postFragment,R.id.workFragment,R.id.mapFragment,R.id.aboutUsFragment))
        binding.bottomNavigation.setupWithNavController(navController)
        setupActionBarWithNavController(navController,appBarConfiguration)
        viewModel.getTeacherDetails()



    }

    private fun logOut(){
        sharedPreferences.edit().putString(KEY_LOGGED_IN_EMAIL, NO_EMAIL).apply()
        sharedPreferences.edit().putString(KEY_PASSWORD, NO_PASSWORD).apply()
        val i = Intent(applicationContext,LoginActivity::class.java)
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(i)
    }

    fun observe(){
        viewModel.teacherDetails.observe(this, Observer { result->
            result?.let {
                when(result.status)
                {
                    Status.SUCCESS ->{

                        sharedPreferences.edit().putString(KEY_NAME, result.data?.name).apply()
                        sharedPreferences.edit().putString(KEY_SUBJECT, result.data?.subject).apply()
                       sharedPreferences.edit().putString(KEY_UID, result.data?.id).apply()

                    }
                    Status.ERROR ->{


                    }
                    Status.LOADING ->{
                        Timber.log(1,"loading"+result.data)
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