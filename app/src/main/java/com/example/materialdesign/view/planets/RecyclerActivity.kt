package com.example.materialdesign.view.planets

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.materialdesign.databinding.ActivityPlanetsBinding


class RecyclerActivity : AppCompatActivity() {
    lateinit var binding: ActivityPlanetsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlanetsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val data:MutableList<Data> = ArrayList()
        repeat(10){
            if(it%2==0){
                data.add(Data("Earth"))
            }else{
                data.add(Data("Mars",""))
            }
        }
        data.add(0,Data("Header"))
        binding.recyclerView.adapter = PlanetsActivityAdapter(
            object : OnListItemClickListener {
                override fun onItemClick(data: Data) {
                    Toast.makeText(this@RecyclerActivity,data.someText,Toast.LENGTH_SHORT).show()
                }
            }, data)
    }
}