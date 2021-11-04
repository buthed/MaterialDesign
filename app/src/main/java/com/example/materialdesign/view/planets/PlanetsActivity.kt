package com.example.materialdesign.view.planets

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.materialdesign.databinding.ActivityPlanetsBinding


class PlanetsActivity : AppCompatActivity() {
    lateinit var binding: ActivityPlanetsBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlanetsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val data:MutableList<Pair<Data,Boolean>> = ArrayList()
        repeat(10){
            if(it%2==0){
                //data.add(Data("Earth"))
            }else{
                data.add(Pair(Data("Mars",""),false))
            }
        }
        val lat = 50
        val lon = 30
        val coordinate = lat to lon
        val coordinate3d = Triple(1,2,3)
        coordinate.first
        coordinate.second
        coordinate3d.first
        coordinate3d.second
        coordinate3d.third

        data.add(0,Pair(Data("Header"),false))
        val adapter= PlanetsActivityAdapter(
            object : OnListItemClickListener {
                override fun onItemClick(data: Data) {
                    Toast.makeText(this@PlanetsActivity,data.someText,Toast.LENGTH_SHORT).show()
                }
            }, data)
        binding.recyclerView.adapter = adapter
        binding.recyclerActivityFAB.setOnClickListener { adapter.appendItem()}
    }
}