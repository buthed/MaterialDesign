package com.example.materialdesign.view.planets

import android.view.View
import androidx.recyclerview.widget.RecyclerView

abstract class BaseViewHolder(view: View): RecyclerView.ViewHolder(view) {
    abstract fun bind(data: Data)
}