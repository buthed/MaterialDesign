package com.example.materialdesign.view.behaviors

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.google.android.material.textfield.TextInputLayout

class CustomCoordinatorBehavior (context: Context? = null, attrs: AttributeSet? = null): CoordinatorLayout.Behavior<View>(context, attrs) {

    override fun layoutDependsOn(
        parent: CoordinatorLayout,
        child: View,
        dependency: View
    )= dependency is TextInputLayout

    @SuppressLint("Range")
    override fun onDependentViewChanged(
        parent: CoordinatorLayout,
        child: View,
        dependency: View
    ): Boolean {
        val appBar = dependency as TextInputLayout
        // т.к. y - отрицательное число
        val currentAppBarHeight = appBar.height + appBar.y
        val parentHeight = parent.height ?: 0
        val placeHolderHeight = (parentHeight - currentAppBarHeight).toInt()
        if (placeHolderHeight == 0)             Log.d("Beh", "GOOD!")
        if (appBar.height  == 0)             Log.d("Beh", "GOOD!1")
        if (parent.height  == 0)             Log.d("Beh", "GOOD!2")
        return false
    }
}