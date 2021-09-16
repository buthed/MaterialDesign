package com.example.materialdesign.view.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.materialdesign.R
import com.example.materialdesign.databinding.FragmentSettingsBinding

class SettingsFragment:Fragment() {
    var _bindong: FragmentSettingsBinding? = null
    val binding: FragmentSettingsBinding
        get() {
            return _bindong!!
        }

    //var bg: String = "default"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //return inflater.inflate(R.layout.fragment_chips, container, false)
        _bindong =  FragmentSettingsBinding.inflate(inflater)
        return  binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _bindong = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.chipGroup.setOnCheckedChangeListener{childGroup,position->
            Toast.makeText(context,"Click $position",Toast.LENGTH_SHORT).show()
        }
        binding.chipWithClose.setOnCloseIconClickListener {
            Toast.makeText(context,"Click on chipWithClose",Toast.LENGTH_SHORT).show()
        }
        binding.radioGroup.setOnCheckedChangeListener { _, checkedId ->
            when(checkedId){
                R.id.rb1 -> Toast.makeText(context,"Cosmos",Toast.LENGTH_SHORT).show()
                R.id.rb2 -> Toast.makeText(context,"Moon",Toast.LENGTH_SHORT).show()
                R.id.rb3 -> Toast.makeText(context,"Mars",Toast.LENGTH_SHORT).show()
            }
        }
    }

/*
bg = when(checkedId){
binding.rb1 -> "1"
binding.rb2 -> binding.settingsFr.background = "@drawable/bg_cosmos"
binding.rb3 -> binding.settingsFr.background = "@drawable/bg_cosmos"
else -> binding.settingsFr.background = "@drawable/bg_cosmos"
}
val a: String = binding.rb1.toString()
binding.settingTitle.text = "option "+i+" is selected"
*/




    companion object {
        fun newInstance() = SettingsFragment()
    }
}