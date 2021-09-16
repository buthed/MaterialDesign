package com.example.materialdesign.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.materialdesign.R
import com.example.materialdesign.view.picture.PODFragment


const val ThemeDefault = 3
const val ThemeCosmos = 1
const val ThemeMoon = 2
const val ThemeMars = 3

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if(savedInstanceState==null){
            supportFragmentManager.beginTransaction().replace(R.id.container,PODFragment.newInstance()).commit()
        }
    }
}