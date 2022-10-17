package com.most4dev.acquaintanceapp.activity

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.most4dev.acquaintanceapp.R
import com.most4dev.acquaintanceapp.managers.SharedPreferenceManager
import android.os.CountDownTimer
import androidx.lifecycle.ViewModelProvider
import com.most4dev.acquaintanceapp.viewModels.AcquaintanceViewModel


@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {

    private lateinit var acquaintanceViewModel: AcquaintanceViewModel
    private lateinit var sharedPreferenceManager: SharedPreferenceManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        acquaintanceViewModel = ViewModelProvider(this)[AcquaintanceViewModel::class.java]
        setTimer()
    }

    private fun setTimer(){
        object : CountDownTimer(5000, 1000) {
            override fun onTick(millisUntilFinished: Long) {}

            override fun onFinish() {
                checkRegister()
            }
        }.start()
    }

    //TODO
    fun checkRegister(){
        sharedPreferenceManager = SharedPreferenceManager(this)
        if (sharedPreferenceManager.getCompleteRegister()){
            acquaintanceViewModel.getWebViewLoad().observe(this) {
                if (!it) {
                    startActivity(
                        Intent(
                            this,
                            WebViewActivity::class.java
                        )
                    )
                } else {
                    startActivity(
                        Intent(
                            this,
                            MainActivity::class.java
                        )
                    )
                }
            }
        }
        else{
            startActivity(
                Intent(
                    this,
                    RegistrationActivity::class.java
                )
            )
        }
        finish()
    }

}