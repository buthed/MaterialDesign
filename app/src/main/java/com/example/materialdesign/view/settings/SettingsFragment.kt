package com.example.materialdesign.view.settings

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.view.ContextThemeWrapper
import androidx.fragment.app.Fragment
import com.example.materialdesign.R
import com.example.materialdesign.databinding.FragmentSettingsBinding
import com.example.materialdesign.view.MainActivity
import com.example.materialdesign.view.ThemeCosmos
import com.example.materialdesign.view.ThemeMars
import com.example.materialdesign.view.ThemeMoon

class SettingsFragment:Fragment(), View.OnClickListener {

    private val KEY_SP_LOCAL = "sp_local"
    private val KEY_CURRENT_THEME_LOCAL = "current_theme_local"

    private lateinit var parentActivity: MainActivity // 1 способ получить родительскую активити
    override fun onAttach(context: Context) {
        super.onAttach(context)
        parentActivity = (context as MainActivity) // 1 способ получить родительскую активити

        parentActivity = activity as MainActivity // воторой способ
        parentActivity =
            requireActivity() as MainActivity // третий способ( на самом деле то же самое все, просто со встроенной проверкой актвити на null)
    }

    var _bindong: FragmentSettingsBinding? = null
    val binding: FragmentSettingsBinding
        get() {
            return _bindong!!
        }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val context: Context = ContextThemeWrapper(activity, getRealStyleLocal(getCurrentThemeLocal()))
        val localInflater = inflater.cloneInContext(context)
        _bindong =  FragmentSettingsBinding.inflate(localInflater)
        return  binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _bindong = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rb1.setOnClickListener(this)
        binding.rb2.setOnClickListener(this)
        binding.rb3.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.rb1 -> {
                setCurrentThemeLocal(ThemeCosmos)
                requireActivity().supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.container, SettingsFragment.newInstance())
                    .commit();// применяем для всей активити и для всех дочерних фрагментов
                binding.settingImageView.setBackgroundResource(R.drawable.bg_cosmos)
            }
            R.id.rb2 -> {
                setCurrentThemeLocal(ThemeMoon)
                requireActivity().supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.container, SettingsFragment.newInstance())
                    .commit();// применяем для всей активити и для всех дочерних фрагментов
                binding.settingImageView.setBackgroundResource(R.drawable.bg_moon)
            }
            R.id.rb3 -> {
                setCurrentThemeLocal(ThemeMars)
                requireActivity().supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.container, SettingsFragment.newInstance())
                    .commit();// применяем для всей активити и для всех дочерних фрагментов
                binding.settingImageView.setBackgroundResource(R.drawable.bg_mars)
            }

        }

    }

    companion object {
        fun newInstance() = SettingsFragment()
    }

    private fun setCurrentThemeLocal(currentTheme: Int) {
        val sharedPreferences =
            requireActivity().getSharedPreferences(KEY_SP_LOCAL, AppCompatActivity.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putInt(KEY_CURRENT_THEME_LOCAL, currentTheme)
        editor.apply()
    }

    private fun getCurrentThemeLocal(): Int {
        val sharedPreferences =
            requireActivity().getSharedPreferences(KEY_SP_LOCAL, AppCompatActivity.MODE_PRIVATE)
        return sharedPreferences.getInt(KEY_CURRENT_THEME_LOCAL, -1)
    }

    private fun getRealStyleLocal(currentTheme: Int): Int {
        return when (currentTheme) {
            ThemeCosmos -> R.style.ThemeCosmos
            ThemeMoon -> R.style.ThemeMoon
            ThemeMars -> R.style.ThemeMars
            else -> 0
        }
    }
}