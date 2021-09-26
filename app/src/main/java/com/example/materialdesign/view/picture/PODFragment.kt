package com.example.materialdesign.view.picture

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import coil.api.load
import com.example.materialdesign.R
import com.example.materialdesign.R.*
import com.example.materialdesign.api.ApiActivity
import com.example.materialdesign.api.ApiBottomActivity
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.example.materialdesign.databinding.FragmentMainBinding
import com.example.materialdesign.view.MainActivity
import com.example.materialdesign.view.settings.SettingsFragment
import com.example.materialdesign.viewmodel.PODData
import com.example.materialdesign.viewmodel.NasaViewModel
import com.google.android.material.bottomappbar.BottomAppBar


class PODFragment : Fragment() {

    private lateinit var bottomSheetBehavior: BottomSheetBehavior<ConstraintLayout>

    private var _binding: FragmentMainBinding? = null
    val binding: FragmentMainBinding
        get() {
            return _binding!!
        }

    private val viewModel: NasaViewModel by lazy {
        ViewModelProvider(this).get(NasaViewModel::class.java)
    }

    lateinit var nasaViewModel: NasaViewModel

    override fun onAttach(context: Context) {
        super.onAttach(context)
        nasaViewModel = (context as MainActivity).nasaViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMainBinding.inflate(inflater)
        setActionBar()
        return binding.root
    }

    private var isMain = true
    private fun setActionBar() {
        (context as MainActivity).setSupportActionBar(binding.bottomAppBar)
        setHasOptionsMenu(true)
        binding.fab.setOnClickListener {
            if (isMain) {
                isMain = false
                binding.bottomAppBar.navigationIcon = null // лучше придумать замену бургеру
                binding.bottomAppBar.fabAlignmentMode = BottomAppBar.FAB_ALIGNMENT_MODE_END
                binding.fab.setImageDrawable(
                    ContextCompat.getDrawable(
                        requireContext(),
                        drawable.ic_back_fab
                    )
                )
                binding.bottomAppBar.replaceMenu(menu.menu_bottom_bar_other_screen)
            } else {
                isMain = true
                binding.bottomAppBar.navigationIcon =
                    ContextCompat.getDrawable(
                        requireContext(),
                        drawable.ic_hamburger_menu_bottom_bar
                    )
                binding.bottomAppBar.fabAlignmentMode = BottomAppBar.FAB_ALIGNMENT_MODE_CENTER
                binding.fab.setImageDrawable(
                    ContextCompat.getDrawable(
                        requireContext(),
                        drawable.ic_plus_fab
                    )
                )
                binding.bottomAppBar.replaceMenu(menu.menu_bottom_bar)
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        nasaViewModel.getLiveData().observe(viewLifecycleOwner, Observer { renderData(it) })

        binding.inputLayout.setEndIconOnClickListener {
            val i = Intent(Intent.ACTION_VIEW).apply {
                data =
                    Uri.parse("https://en.wikipedia.org/wiki/${binding.inputEditText.text.toString()}")
            }
            startActivity(i)
        }
        nasaViewModel.getPODFromServer((TODAY))
        bottomSheetBehavior = BottomSheetBehavior.from(binding.includeLayout.bottomSheetContainer)
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
    }

    private fun renderData(data: PODData) {
        when (data) {
            is PODData.SuccessPOD -> {
                setData(data)
            }
            is PODData.Error -> {//TODO HW
                Toast.makeText(context, "PODData.Error", Toast.LENGTH_LONG).show()
            }
            is PODData.Loading -> {
                binding.imageView.load(drawable.progress_animation) {
                    error(drawable.ic_load_error_vector)
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object{
        fun newInstance(): PODFragment {
            return PODFragment()
        }
        private const val TODAY = 0
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
                requireActivity().supportFragmentManager.beginTransaction().replace(R.id.container,SettingsFragment.newInstance()).addToBackStack("").commit()
            }
            // у нашего бургера такой вот id внутри android
            android.R.id.home -> {
                BottomNavigationDrawerFragment.newInstance()
                    .show(requireActivity().supportFragmentManager, "")
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setData(data: PODData.SuccessPOD)  {
        val url = data.serverResponseData.hdurl
        if (url.isNullOrEmpty()) {
            val videoUrl = data.serverResponseData.url
            videoUrl?.let { showAVideoUrl(it) }
        } else {
            binding.imageView.load(data.serverResponseData.url) { // квадратное становится прямоугольным
                placeholder(drawable.progress_animation) // этот
                error(drawable.ic_load_error_vector)
            }
            binding.titleOfImageview.setText(data.serverResponseData.title)
        }
    }

    private fun showAVideoUrl(videoUrl: String) = with(binding) {
        imageView.visibility = View.GONE
        titleOfImageview.visibility = View.VISIBLE
        titleOfImageview.text = "Сегодня у нас без картинки дня, но есть  видео дня! " +
                "${videoUrl.toString()} \n кликни >ЗДЕСЬ< чтобы открыть в новом окне"
        titleOfImageview.setOnClickListener {
            val i = Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse(videoUrl)
            }
            startActivity(i)
        }
    }


}