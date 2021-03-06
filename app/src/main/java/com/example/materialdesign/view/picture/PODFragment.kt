package com.example.materialdesign.view.picture

import android.content.Context
import android.content.Intent
import android.graphics.Typeface
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.text.style.TypefaceSpan
import android.view.*
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.provider.FontRequest
import androidx.core.provider.FontsContractCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import coil.api.load
import com.example.materialdesign.R
import com.example.materialdesign.R.*
import com.example.materialdesign.api.ApiActivity
import com.example.materialdesign.api.ApiBottomActivity
import com.example.materialdesign.databinding.FragmentMainBinding
import com.example.materialdesign.view.MainActivity
import com.example.materialdesign.view.planets.PlanetsActivity
import com.example.materialdesign.view.settings.SettingsFragment
import com.example.materialdesign.viewmodel.PODData
import com.example.materialdesign.viewmodel.NasaViewModel
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.bottomsheet.BottomSheetBehavior

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
                binding.bottomAppBar.navigationIcon = null // ?????????? ?????????????????? ???????????? ??????????????
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
                data.serverResponseData.explanation?.let{
                    val spannableStart = SpannableStringBuilder(it)

                    binding.descOfImageview.setText(spannableStart, TextView.BufferType.EDITABLE)
                    val spannable = binding.descOfImageview.text as SpannableStringBuilder


                    val start = 1
                    val end  = 3
                    spannable.setSpan(
                        ForegroundColorSpan(resources.getColor(color.colorAccent)),start,
                        end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                    spannable.insert(end,"a")
                    spannable.insert(start,"a")
                    spannable.setSpan(
                        ForegroundColorSpan(resources.getColor(color.colorPrimary)),5,
                        25, Spannable.SPAN_INCLUSIVE_INCLUSIVE)

                    spannable.setSpan(
                        ForegroundColorSpan(resources.getColor(color.colorAccent)),20,
                        55, Spannable.SPAN_INCLUSIVE_INCLUSIVE)

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                        spannable.setSpan(
                            TypefaceSpan(resources.getFont(font.astro_armada_twotone)),56,
                            70, Spannable.SPAN_INCLUSIVE_INCLUSIVE)
                    }

                    val request = FontRequest("com.google.android.gms.fonts",
                        "com.google.android.gms","Homenaje", array.com_google_android_gms_fonts_certs)
                    val fontCallback = object : FontsContractCompat.FontRequestCallback(){
                        override fun onTypefaceRetrieved(typeface: Typeface?) {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                                typeface?.let {
                                    spannable.setSpan(TypefaceSpan(it),0,
                                        spannable.length,Spannable.SPAN_INCLUSIVE_INCLUSIVE)
                                }
                            }
                        }
                    }
                    FontsContractCompat.requestFont(requireContext(),request,fontCallback,
                        Handler(Looper.getMainLooper())
                    )
                }
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
            R.id.app_bar_planets -> {
                activity?.let {
                    startActivity(Intent(it,PlanetsActivity::class.java))
                }
            }
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
            binding.imageView.load(data.serverResponseData.url ) { // ???????????????????? ???????????????????? ??????????????????????????
                placeholder(drawable.progress_animation) // ????????
                error(drawable.ic_load_error_vector)
            }
            binding.titleOfImageview.setText(data.serverResponseData.title)
            binding.descOfImageview.setText(data.serverResponseData.explanation)
        }
    }

    private fun showAVideoUrl(videoUrl: String) = with(binding) {
        imageView.visibility = View.GONE
        titleOfImageview.visibility = View.VISIBLE
        titleOfImageview.text = "?????????????? ?? ?????? ?????? ???????????????? ??????, ???? ????????  ?????????? ??????! " +
                "${videoUrl.toString()} \n ???????????? >??????????< ?????????? ?????????????? ?? ?????????? ????????"
        titleOfImageview.setOnClickListener {
            val i = Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse(videoUrl)
            }
            startActivity(i)
        }
    }
}