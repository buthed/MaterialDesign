package com.example.materialdesign.api

import android.os.Bundle
import android.transition.Slide
import android.transition.TransitionManager
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.materialdesign.R
import com.example.materialdesign.databinding.FragmentEarthBinding
import com.example.materialdesign.databinding.FragmentMarsBinding

class EarthFragment: Fragment() {

    private var _binding: FragmentEarthBinding? = null
    val binding: FragmentEarthBinding
        get() {
            return _binding!!
        }

    private var textIsVisible = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEarthBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        super.onCreate(savedInstanceState)

        binding.imageView.setOnClickListener {
            if (textIsVisible) {
                TransitionManager.beginDelayedTransition(binding.transitionsContainer, Slide(Gravity.END))
                textIsVisible = !textIsVisible
                binding.titlePlanet.visibility = if (textIsVisible) View.VISIBLE else View.GONE
                binding.titlePlanet2.visibility = if (textIsVisible) View.VISIBLE else View.GONE
            }
            else {
                TransitionManager.beginDelayedTransition(binding.transitionsContainer, Slide(Gravity.START))
                textIsVisible = !textIsVisible
                binding.titlePlanet.visibility = if (textIsVisible) View.VISIBLE else View.GONE
                binding.titlePlanet2.visibility = if (textIsVisible) View.VISIBLE else View.GONE
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}