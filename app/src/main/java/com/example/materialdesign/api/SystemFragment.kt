package com.example.materialdesign.api

import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.*

import com.example.materialdesign.R
import com.example.materialdesign.databinding.FragmentSystemBinding

class SystemFragment: Fragment() {

    private var _binding: FragmentSystemBinding? = null
    val binding: FragmentSystemBinding
        get() {
            return _binding!!
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSystemBinding.inflate(inflater)
        binding.recyclerView.adapter = Adapter()
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    fun explode(clickedView:View){
        val viewRect = Rect()
        clickedView.getGlobalVisibleRect(viewRect)
        val explode = Explode()
        explode.epicenterCallback = object : Transition.EpicenterCallback(){
            override fun onGetEpicenter(transition: Transition): Rect {
                return viewRect
            }
        }
        explode.excludeTarget(clickedView,true)
        explode.duration = 3000

        val setTransition = TransitionSet()
            .addTransition(explode)

        TransitionManager.beginDelayedTransition(binding.recyclerView,setTransition)
        binding.recyclerView.adapter = null
    }

    inner class Adapter : RecyclerView.Adapter<Adapter.MyViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
            return MyViewHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.activity_animations_explode_item,
                    parent,
                    false
                ) as View
            )
        }
        override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
            holder.itemView.setOnClickListener {
                explode(it)
            }
        }
        override fun getItemCount(): Int {
            return 32
        }
        inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) // FIXME
    }

}