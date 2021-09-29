package com.example.materialdesign.view.favorites

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.materialdesign.databinding.FragmentFavoritesBinding


class FavoritesFragment:Fragment() { //TODO: доделать фрагмент

    private var isExpanded = false

    var _bindong: FragmentFavoritesBinding? = null
    val binding: FragmentFavoritesBinding
        get() {
            return _bindong!!
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _bindong =  FragmentFavoritesBinding.inflate(inflater)
        return  binding.root
    }




    private fun setFAB() {
        setInitialState()

        binding.fab.setOnClickListener {
            if (isExpanded) {
                collapseFab()
            } else {
                expandFAB()
            }
        }
    }

    private fun setInitialState() = with(binding) {
        transparentBackground.apply {
            alpha = 0f
        }
        optionTwoContainer.apply {
            alpha = 0f
            isClickable = false
        }
        optionOneContainer.apply {
            alpha = 0f
            isClickable = false
        }
    }

    private fun expandFAB() = with(binding) {
        isExpanded = true
        ObjectAnimator.ofFloat(plusImageview, "rotation", 0f, 225f).start()
        ObjectAnimator.ofFloat(optionTwoContainer, "translationY", -130f).start()
        ObjectAnimator.ofFloat(optionOneContainer, "translationY", -250f).start()

        optionTwoContainer.animate()
            .alpha(1f)
            .setDuration(300)
            .setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    optionTwoContainer.isClickable = true
                    optionTwoContainer.setOnClickListener {

                    }
                }
            })
        optionOneContainer.animate()
            .alpha(1f)
            .setDuration(300)
            .setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    optionOneContainer.isClickable = true
                    optionOneContainer.setOnClickListener {

                    }
                }
            })
        transparentBackground.animate()
            .alpha(0.9f)
            .setDuration(300)
            .setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    transparentBackground.isClickable = true
                }
            })
    }

    private fun collapseFab() = with(binding) {
        isExpanded = false
        ObjectAnimator.ofFloat(plusImageview, "rotation", 0f, -180f).start()
        ObjectAnimator.ofFloat(optionTwoContainer, "translationY", 0f).start()
        ObjectAnimator.ofFloat(optionOneContainer, "translationY", 0f).start()

        optionTwoContainer.animate()
            .alpha(0f)
            .setDuration(300)
            .setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    optionTwoContainer.isClickable = false
                    optionOneContainer.setOnClickListener(null)
                }
            })
        optionOneContainer.animate()
            .alpha(0f)
            .setDuration(300)
            .setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    optionOneContainer.isClickable = false
                }
            })
        transparentBackground.animate()
            .alpha(0f)
            .setDuration(300)
            .setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    transparentBackground.isClickable = false
                }
            })
    }

    override fun onDestroy() {
        super.onDestroy()
        _bindong = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setFAB()
        binding.scrollView.setOnScrollChangeListener { _, _, _, _, _ ->
            binding.header.isSelected = binding.scrollView.canScrollVertically(-1)
        }

    }
    companion object {
        fun newInstance() = FavoritesFragment()
    }
}