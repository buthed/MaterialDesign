package com.example.materialdesign.view.planets

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.materialdesign.R
import com.example.materialdesign.databinding.FragmentRecyclerItemEarthBinding
import com.example.materialdesign.databinding.FragmentRecyclerItemHeaderBinding
import com.example.materialdesign.databinding.FragmentRecyclerItemMarsBinding


class PlanetsFragmentAdapter (
    private var onListItemClickListener: OnListItemClickListener,
    private var data: List<Data>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType){
            TYPE_EARTH->{
                val binding: FragmentRecyclerItemEarthBinding =
                    FragmentRecyclerItemEarthBinding.inflate(LayoutInflater.from(parent.context),parent,false)
                EarthViewHolder(binding.root)
            }
            TYPE_MARS->{
                val binding: FragmentRecyclerItemMarsBinding =
                    FragmentRecyclerItemMarsBinding.inflate(LayoutInflater.from(parent.context),parent,false)
                MarsViewHolder(binding.root)
            }
            else -> {
                val binding: FragmentRecyclerItemHeaderBinding =
                    FragmentRecyclerItemHeaderBinding.inflate(LayoutInflater.from(parent.context),parent,false)
                HeaderViewHolder(binding.root)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) == TYPE_EARTH) {
            holder as EarthViewHolder
            holder.bind(data[position])
        } else {
            holder as MarsViewHolder
            holder.bind(data[position])
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun getItemViewType(position: Int): Int {
        return if (data[position].someDescription.isNullOrBlank()) TYPE_MARS else TYPE_EARTH
    }

    inner class EarthViewHolder(view: View):RecyclerView.ViewHolder(view){
        fun bind(data: Data){
            FragmentRecyclerItemEarthBinding.bind(itemView).apply {
                descriptionTextView.text = data.someDescription
                wikiImageView.setOnClickListener {
                    onListItemClickListener.onItemClick(data)
                }
            }
        }
    }

    inner class MarsViewHolder(view: View):RecyclerView.ViewHolder(view){
        fun bind(data: Data){
            // было itemView.findViewById<ImageView>(R.id.marsImageView).setOnClickListener {  }
            FragmentRecyclerItemMarsBinding.bind(itemView).apply {
                marsImageView.setOnClickListener {
                    onListItemClickListener.onItemClick(data)
                }
            }
        }
    }

    inner class HeaderViewHolder(view: View):RecyclerView.ViewHolder(view){
        fun bind(data: Data){
            // было itemView.findViewById<ImageView>(R.id.marsImageView).setOnClickListener {  }
            FragmentRecyclerItemHeaderBinding.bind(itemView).apply {
                root.setOnClickListener {
                    onListItemClickListener.onItemClick(data)
                }
            }
        }
    }

    companion object {
        private const val TYPE_EARTH=0
        private const val TYPE_MARS=1
        private const val TYPE_HEADER=2
    }

}