package com.most4dev.acquaintanceapp.activity

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.most4dev.acquaintanceapp.Config
import com.most4dev.acquaintanceapp.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navController = findNavController(R.id.nav_host)

        fabPrivacy.setOnClickListener {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(Config.termsURL))
            startActivity(browserIntent)
            menu_yellow.close(true)
        }

    }
}