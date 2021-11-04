package com.example.materialdesign.view.picture

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.materialdesign.R
import com.example.materialdesign.databinding.BottomNavigationLayoutBinding
import com.example.materialdesign.view.bonus.BonusFragment
import com.example.materialdesign.view.favorites.FavoritesFragment
import com.example.materialdesign.view.planets.RecyclerActivity
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class BottomNavigationDrawerFragment : BottomSheetDialogFragment() {

    private var _binding: BottomNavigationLayoutBinding? = null
    val binding: BottomNavigationLayoutBinding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = BottomNavigationLayoutBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.navigationView.setNavigationItemSelectedListener { it->
            when(it.itemId){
                R.id.app_bar_fav ->{
                    requireActivity().supportFragmentManager.beginTransaction().replace(R.id.container,
                        FavoritesFragment.newInstance()).addToBackStack("").commit()
                }
                R.id.app_bar_settings ->{
                    requireActivity().supportFragmentManager.beginTransaction().replace(R.id.container,
                        BonusFragment.newInstance()).addToBackStack("").commit()
                }
                R.id.app_bar_planets  ->{
                    activity?.let {
                        startActivity(Intent(it,RecyclerActivity::class.java))
                    }
                }
            }
            true
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding=null
    }
    companion object {
        fun newInstance() = BottomNavigationDrawerFragment()
    }
}