package com.makertech.collegeapp.ui.splashScreen

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.makertech.collegeapp.data.remote.BasicAuthInterceptor
import com.makertech.collegeapp.databinding.SplashScreenBinding
import com.makertech.collegeapp.other.AppConstants.KEY_LOGGED_IN_EMAIL
import com.makertech.collegeapp.other.AppConstants.KEY_PASSWORD
import com.makertech.collegeapp.other.AppConstants.NO_EMAIL
import com.makertech.collegeapp.other.AppConstants.NO_PASSWORD
import com.makertech.collegeapp.ui.home.HomeActivity
import com.makertech.collegeapp.ui.login.LoginFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class SplashActivity: AppCompatActivity() {
    lateinit var binding: SplashScreenBinding

    @Inject
    lateinit var sharedPreferences: SharedPreferences

    @Inject
    lateinit var basicAuthInterceptor: BasicAuthInterceptor

    private var curEmail:String?=null
    private var curPassword:String?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = SplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        lifecycleScope.launch (Dispatchers.IO){
            if (isLoggedIn())
            {
                authenticateApi(curEmail?:"",curPassword?:"")
                redirectToHomeActivity()
            }
            else{
                redirectToLoginActivity()
            }
        }


    }

   suspend fun isLoggedIn():Boolean
    {
        curEmail = sharedPreferences.getString(KEY_LOGGED_IN_EMAIL,NO_EMAIL)
        curPassword = sharedPreferences.getString(KEY_PASSWORD, NO_PASSWORD)
        delay(2000)
        return curEmail != NO_EMAIL && curPassword != NO_PASSWORD
    }

    suspend fun authenticateApi(email:String,pass:String){
        basicAuthInterceptor.email = email
        basicAuthInterceptor.password = pass

    }

    private fun redirectToHomeActivity()
    {
        val nextScreen = Intent(applicationContext, HomeActivity::class.java)
        nextScreen.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(nextScreen)
    }

    private fun redirectToLoginActivity()
    {
        val nextScreen = Intent(applicationContext, LoginFragment::class.java)
        nextScreen.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(nextScreen)
    }
}