package com.makertech.tnuteacherapp.ui.login

import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_CLEAR_TASK
import android.content.SharedPreferences
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.IntentCompat
import androidx.lifecycle.Observer
import com.makertech.tnuteacherapp.data.remote.BasicAuthInterceptor
import com.makertech.tnuteacherapp.databinding.LoginScreenBinding
import com.makertech.tnuteacherapp.other.AppConstants.KEY_LOGGED_IN_EMAIL
import com.makertech.tnuteacherapp.other.AppConstants.KEY_PASSWORD
import com.makertech.tnuteacherapp.other.Status
import com.makertech.tnuteacherapp.ui.home.HomeActivity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {
    private val viewModel: LoginViewModel by viewModels()
    lateinit var binding: LoginScreenBinding

    @Inject
    lateinit var sharedPreferences: SharedPreferences

    @Inject
    lateinit var basicAuthInterceptor: BasicAuthInterceptor

    private var curEmail:String?=null
    private var curPassword:String?=null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = LoginScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        observer()
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        binding.btnLogin.setOnClickListener {
            val email = binding.edtLoginEmail.text.toString()
            val password = binding.edtPassword.text.toString()
            curEmail = email
            curPassword = password
           viewModel.login(email , password )
        }

    }
private fun authenticateApi(email:String,pass:String){
    basicAuthInterceptor.email = email
    basicAuthInterceptor.password = pass
}

    private fun redirectToHomeActivity()
    {
        val nextScreen = Intent(applicationContext,HomeActivity::class.java)
        nextScreen.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(nextScreen)
    }
private fun observer(){
        viewModel.loginStatus.observe(this, Observer {result->
            result?.let {
                when(result.status)
                {
                    Status.SUCCESS ->{
                        sharedPreferences.edit().putString(KEY_LOGGED_IN_EMAIL,curEmail).apply()
                        sharedPreferences.edit().putString(KEY_PASSWORD,curPassword).apply()
                        authenticateApi(curEmail?:"",curPassword?:"")
                        redirectToHomeActivity()
                        Toast.makeText(this,"loading"+result.data,Toast.LENGTH_SHORT).show()

                    }
                    Status.ERROR ->{
                        Toast.makeText(this,"Error" +result.data ,Toast.LENGTH_SHORT).show()

                    }
                    Status.LOADING ->{
                        Toast.makeText(this,"loading",Toast.LENGTH_SHORT).show()
                    }
                }
            }
        })
   }
}