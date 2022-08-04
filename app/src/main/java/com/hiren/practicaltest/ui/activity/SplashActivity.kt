package com.hiren.practicaltest.ui.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.*

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {

    private var _activityScope = CoroutineScope(Dispatchers.Main)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _activityScope.launch {
            delay(1000)
            handleNextAction()
        }
    }

    private fun handleNextAction() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}