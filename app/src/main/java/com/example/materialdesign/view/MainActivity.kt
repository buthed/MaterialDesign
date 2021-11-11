package com.example.materialdesign.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.example.materialdesign.R
import com.example.materialdesign.databinding.ActivityMainBinding
import com.example.materialdesign.view.picture.PODFragment
import com.example.materialdesign.viewmodel.NasaViewModel


const val ThemeCosmos = 1
const val ThemeMoon = 2
const val ThemeMars = 3

class MainActivity : AppCompatActivity(), View.OnClickListener {

    val nasaViewModel by lazy {
        ViewModelProvider(this).get(NasaViewModel::class.java)
    }

    private val KEY_SP = "sp"
    private val KEY_CURRENT_THEME = "current_theme"
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_MaterialDesign)
        binding =  ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onResume() {
        super.onResume()

        supportFragmentManager.beginTransaction().replace(R.id.container,PODFragment.newInstance()).commit()
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.rb1 -> setCurrentTheme(R.style.ThemeCosmos)
            R.id.rb2 -> setCurrentTheme(R.style.ThemeMoon)
            R.id.rb3 -> setCurrentTheme(R.style.ThemeMars)
        }
        recreate()
    }

    fun setCurrentTheme(currentTheme: Int) {
        val sharedPreferences = getSharedPreferences(KEY_SP, MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putInt(KEY_CURRENT_THEME, currentTheme)
        editor.apply()
    }

    fun getCurrentTheme(): Int {
        val sharedPreferences = getSharedPreferences(KEY_SP, MODE_PRIVATE)
        return sharedPreferences.getInt(KEY_CURRENT_THEME, -1)
    }

    private fun getRealStyle(currentTheme: Int): Int {
        return when (currentTheme) {
            ThemeCosmos -> R.style.ThemeCosmos
            ThemeMoon -> R.style.ThemeMoon
            ThemeMars -> R.style.ThemeMars
            else -> 0
        }
    }
}