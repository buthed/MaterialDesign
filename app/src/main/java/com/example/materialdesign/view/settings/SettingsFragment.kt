package com.example.materialdesign.view.settings

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.materialdesign.R
import com.example.materialdesign.databinding.FragmentSettingsBinding
import com.example.materialdesign.view.MainActivity
import com.example.materialdesign.view.ThemeCosmos
import com.example.materialdesign.view.ThemeMars
import com.example.materialdesign.view.ThemeMoon

class SettingsFragment:Fragment(), View.OnClickListener {

    private lateinit var parentActivity: MainActivity
    override fun onAttach(context: Context) {
        super.onAttach(context)
        parentActivity = activity as MainActivity
    }

    private var _binding: FragmentSettingsBinding? = null
    private val binding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSettingsBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rb1.setOnClickListener(this)
        binding.rb2.setOnClickListener(this)
        binding.rb3.setOnClickListener(this)

        when (parentActivity.getCurrentTheme()) {
            1 -> binding.radioGroup.check(R.id.rb1)
            2 -> binding.radioGroup.check(R.id.rb2)
            3 -> binding.radioGroup.check(R.id.rb3)
        }
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.rb1 -> {
                parentActivity.setCurrentTheme(ThemeCosmos)
                parentActivity.recreate()
            }
            R.id.rb2 -> {
                parentActivity.setCurrentTheme(ThemeMoon)
                parentActivity.recreate()
            }
            R.id.rb3 -> {
                parentActivity.setCurrentTheme(ThemeMars)
                parentActivity.recreate()
            }
        }
    }

    companion object {
        fun newInstance() = SettingsFragment()
    }
}