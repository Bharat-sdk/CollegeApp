package com.makertech.collegeapp.ui.login

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.makertech.collegeapp.data.remote.BasicAuthInterceptor
import com.makertech.collegeapp.databinding.LoginScreenBinding
import com.makertech.collegeapp.other.AppConstants.KEY_LOGGED_IN_EMAIL
import com.makertech.collegeapp.other.AppConstants.KEY_PASSWORD
import com.makertech.collegeapp.other.Status
import com.makertech.collegeapp.ui.home.HomeActivity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LoginFragment : AppCompatActivity() {
    private val viewModel:LoginViewModel by viewModels()
    lateinit var binding:LoginScreenBinding

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
        val nextScreen = Intent(applicationContext, HomeActivity::class.java)
        nextScreen.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(nextScreen)
    }

   private fun observer(){
        viewModel.loginStatus.observe(this, Observer {result->
            result?.let {
                when(result.status)
                {
                    Status.SUCCESS ->{
                        binding.btnLogin.visibility = View.VISIBLE
                        binding.splashLoadingAnimation.cancelAnimation()
                        binding.splashLoadingAnimation.visibility = View.INVISIBLE
                        sharedPreferences.edit().putString(KEY_LOGGED_IN_EMAIL,curEmail).apply()
                        sharedPreferences.edit().putString(KEY_PASSWORD,curPassword).apply()
                        authenticateApi(curEmail?:"",curPassword?:"")
                        redirectToHomeActivity()

                    }
                    Status.ERROR ->{
                        binding.btnLogin.visibility = View.VISIBLE
                        binding.splashLoadingAnimation.cancelAnimation()
                        binding.splashLoadingAnimation.visibility = View.INVISIBLE
                        Toast.makeText(this,"Some Error Occurred" +result.data ,Toast.LENGTH_SHORT).show()

                    }
                    Status.LOADING ->{
                        binding.btnLogin.visibility = View.INVISIBLE
                        binding.splashLoadingAnimation.playAnimation()
                        binding.splashLoadingAnimation.visibility = View.VISIBLE
                    }
                }
            }
        })
   }
}