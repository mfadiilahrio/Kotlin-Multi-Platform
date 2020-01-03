package com.rio.kotlinmultiplatform.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.rio.kotlinmultiplatform.R
import com.rio.kotlinmultiplatform.avenger.view.AvengerActivity
import com.rio.kotlinmultiplatform.xmen.view.XMenActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        avenger.setOnClickListener {
            val intent = Intent(this, AvengerActivity::class.java)
            startActivity(intent)
        }

        xmen.setOnClickListener {
            val intent = Intent(this, XMenActivity::class.java)
            startActivity(intent)
        }
    }
}
