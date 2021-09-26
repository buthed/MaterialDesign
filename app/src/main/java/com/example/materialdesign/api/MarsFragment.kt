package com.example.materialdesign.api

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import coil.api.load
import com.example.materialdesign.R
import com.example.materialdesign.databinding.FragmentMarsBinding
import com.example.materialdesign.view.picture.BottomNavigationDrawerFragment
import com.example.materialdesign.view.settings.SettingsFragment
import com.example.materialdesign.viewmodel.NasaViewModel
import com.example.materialdesign.viewmodel.PODData

class MarsFragment: Fragment() {

    private var _binding: FragmentMarsBinding? = null
    val binding: FragmentMarsBinding
        get() {
            return _binding!!
        }

    private val viewModel: NasaViewModel by lazy {
        ViewModelProvider(this).get(NasaViewModel::class.java)
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMarsBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getLiveData().observe(viewLifecycleOwner, Observer { renderData(it) })

        viewModel.getMarsPicture()
    }

    private fun renderData(data: PODData) {
        when (data) {
            is PODData.SuccessMars -> {
                val url = data.serverResponseData.photos.first().imgSrc
                binding.imageView.load(url)
            }
            is PODData.Error -> {//TODO HW
                Toast.makeText(context, "PODData.Error", Toast.LENGTH_LONG).show()
            }
            is PODData.Loading -> {
                binding.imageView.load(R.drawable.progress_animation) {
                    error(R.drawable.ic_load_error_vector)
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object{
        fun newInstance(): MarsFragment {
            return MarsFragment()
        }
        private const val TODAY = 0   //TODO: доделать дни
        private const val YESTERDAY = 1
        private const val BEFORE_YESTERDAY = 2
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_bottom_bar, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.app_bar_other -> {
                Toast.makeText(context, "Favorite", Toast.LENGTH_SHORT).show()
                startActivity(Intent(context, ApiBottomActivity::class.java))
            }
            R.id.app_bar_fav -> {
                startActivity(Intent(context, ApiActivity::class.java))
            }
            R.id.app_bar_settings -> {
                requireActivity().supportFragmentManager.beginTransaction().replace(R.id.container,
                    SettingsFragment.newInstance()).addToBackStack("").commit()
            }
            // у нашего бургера такой вот id внутри android
            android.R.id.home -> {
                BottomNavigationDrawerFragment.newInstance()
                    .show(requireActivity().supportFragmentManager, "")
            }
        }
        return super.onOptionsItemSelected(item)
    }
}