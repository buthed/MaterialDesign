package com.example.materialdesign.view.planets

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.materialdesign.databinding.FragmentPlanetsBinding

class PlanetsFragment : Fragment() {

    private var _binding: FragmentPlanetsBinding? = null
    val binding: FragmentPlanetsBinding
        get() {
            return _binding!!
        }



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPlanetsBinding.inflate(inflater)
        return binding.root

//        val data = listOf(
//            Data("Earth"),
//            Data("Mars", ""),
//            Data("Earth"),
//            Data("Earth"),
//            Data("Mars", ""),
//            Data("Mars", ""),
//            Data("Earth"),
//            Data("Mars", "")
//        )
//
//        binding.recyclerView.adapter = PlanetsFragmentAdapter(
//            object : PlanetsFragmentAdapter.OnListItemClickListener {
//                override fun onItemClick(data: Data) {
//                }
//            },
//            data
//        )

    }

    companion object {
        fun newInstance() = PlanetsFragment()
    }
}