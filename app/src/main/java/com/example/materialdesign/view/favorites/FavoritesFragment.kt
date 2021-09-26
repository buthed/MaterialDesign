package com.example.materialdesign.view.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.materialdesign.databinding.FragmentFavoritesBinding


class FavoritesFragment:Fragment() { //TODO: доделать фрагмент
    var _bindong: FragmentFavoritesBinding? = null
    val binding: FragmentFavoritesBinding
        get() {
            return _bindong!!
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //return inflater.inflate(R.layout.fragment_chips, container, false)
        _bindong =  FragmentFavoritesBinding.inflate(inflater)
        return  binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _bindong = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
    companion object {
        fun newInstance() = FavoritesFragment()
    }
}