package com.example.material_app.view.picture

import android.content.ContentValues.TAG
import android.content.Intent
import android.graphics.Typeface
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.text.Html
import android.text.Spannable
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.style.BulletSpan
import android.text.style.ImageSpan
import android.util.Log
import android.view.*
import android.widget.FrameLayout
import androidx.activity.OnBackPressedCallback
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.transition.ArcMotion
import androidx.transition.ChangeBounds
import androidx.transition.TransitionManager
import coil.load
import com.example.material_app.R
import com.example.material_app.databinding.FragmentMainBinding
import com.example.material_app.view.MainActivity
import com.example.material_app.view.api.ApiActivity
import com.example.material_app.view.api.ApiBottomActivity
import com.example.material_app.view.chips.SettingsFragment
import com.example.material_app.view.constraint.ConstraintFragment
import com.example.material_app.viewmodel.PictureOfTheDayState
import com.example.material_app.viewmodel.PictureOfTheDayViewModel
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.bottomsheet.BottomSheetBehavior
import smartdevelop.ir.eram.showcaseviewlib.GuideView
import smartdevelop.ir.eram.showcaseviewlib.config.DismissType
import smartdevelop.ir.eram.showcaseviewlib.config.Gravity
import smartdevelop.ir.eram.showcaseviewlib.listener.GuideListener
import java.text.SimpleDateFormat
import java.util.*

class PictureOfTheDayFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    val binding: FragmentMainBinding
        get() {
            return _binding!!
        }

    private val viewModel: PictureOfTheDayViewModel by lazy {
        ViewModelProvider(this).get(PictureOfTheDayViewModel::class.java)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getData().observe(viewLifecycleOwner, Observer {
            renderData(it)
        })
        viewModel.sendServerRequest()

        binding.inputLayout.setEndIconOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW).apply {
                data =
                    Uri.parse("https://en.wikipedia.org/wiki/${binding.inputEditText.text.toString()}")
            })
        }

        var isDirectionRight = false
        binding.chipGroup.setOnCheckedChangeListener { group, checkedId ->
            isDirectionRight = !isDirectionRight
            when (checkedId) {
                R.id.yestrday -> {
                    viewModel.sendServerRequest(takeDate(-1))
                }
                R.id.today -> {
                    viewModel.sendServerRequest()
                }

            }
            val params = binding.chipGroup.layoutParams as ConstraintLayout.LayoutParams
            if (isDirectionRight) {
                params.height = ConstraintLayout.LayoutParams.MATCH_PARENT
            } else {
                params.height = ConstraintLayout.LayoutParams.WRAP_CONTENT
            }

            val transition = ChangeBounds()
            val path = ArcMotion()
            transition.setPathMotion(path)
            transition.duration = 2000
            TransitionManager.beginDelayedTransition(binding.chipGroup, transition)

            binding.chipGroup.layoutParams = params


        }

        setBottomAppBar()


        val builder = GuideView.Builder(requireContext())
            .setTitle("Новая фича")
            .setContentText("Мы добавили")
            .setGravity(Gravity.center)
            .setTargetView(binding.inputEditText)
            .setDismissType(DismissType.anywhere)
            .setGuideListener(object : GuideListener {
                override fun onDismiss(view: View?) {

                }
            })
        builder.build().show()


    }

    private fun takeDate(count: Int): String {
        val currentDate = Calendar.getInstance()
        currentDate.add(Calendar.DAY_OF_MONTH, count)
        val format1 = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        format1.timeZone = TimeZone.getTimeZone("EST")
        return format1.format(currentDate.time)
    }

    private fun renderData(state: PictureOfTheDayState) {
        when (state) {
            is PictureOfTheDayState.Error -> {
                binding.imageView.load(R.drawable.ic_no_photo_vector)
            }
            is PictureOfTheDayState.Loading -> {
                binding.imageView.load(R.drawable.ic_no_photo_vector)
            }
            is PictureOfTheDayState.Success -> {
                val pictureOfTheDayResponseData = state.pictureOfTheDayResponseData
                val url = pictureOfTheDayResponseData.url
                binding.imageView.load(url) {
                    lifecycle(this@PictureOfTheDayFragment)
                    error(R.drawable.ic_load_error_vector)
                    placeholder(R.drawable.ic_no_photo_vector)
                    binding.includeBottomSheet.bottomSheetDescriptionHeader.text =
                        pictureOfTheDayResponseData.explanation
//                    binding.includeBottomSheet.bottomSheetDescriptionHeader.typeface =
//                        Typeface.createFromAsset(requireContext().assets, "a.ttf")

                    val spannableMutable =
                        SpannableStringBuilder("A galaxy is a gravitationally bound system of " +
                                "stars, stellar remnants, interstellar gas, dust, and dark matter. The word is derived from the Greek galaxias," +
                                " literally, a reference to the Milky Way. Galaxies range in size from dwarfs with just a few hundred million" +
                                "stars to giants with one hundred trillion stars, each orbiting its galaxy's center of mass.")


                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                        spannableMutable.setSpan(
                            BulletSpan(
                                20,
                                ContextCompat.getColor(requireContext(), R.color.color_green_dark),
                                10
                            ),
                            0, 30, Spannable.SPAN_INCLUSIVE_INCLUSIVE
                        )



                        binding.textView.text = spannableMutable
                    }

                }
            }
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    companion object {
        fun newInstance() = PictureOfTheDayFragment()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_bottom_bar, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.api_activity ->
                startActivity(Intent(requireContext(), ApiActivity::class.java))

            R.id.api_bottom_activity -> {
                startActivity(Intent(requireContext(), ApiBottomActivity::class.java))
            }
            R.id.app_bar_settings -> requireActivity().supportFragmentManager.beginTransaction()
                .addToBackStack("")
                .replace(
                    R.id.container,
                    SettingsFragment.newInstance()
                ).commit()
            android.R.id.home -> BottomNavigationDrawerFragment().show(
                requireActivity().supportFragmentManager,
                ""
            )
        }
        return super.onOptionsItemSelected(item)
    }

    private var isMain = true
    private fun setBottomAppBar() {
        val context = activity as MainActivity
        context.setSupportActionBar(binding.bottomAppBar)
        setHasOptionsMenu(true)


        binding.fab.setOnClickListener {
            val behavior = BottomSheetBehavior.from(binding.includeBottomSheet.bottomSheetContainer)
            if (isMain) {
                isMain = false
                behavior.state = BottomSheetBehavior.STATE_EXPANDED
                binding.bottomAppBar.navigationIcon = null
                binding.bottomAppBar.fabAlignmentMode = BottomAppBar.FAB_ALIGNMENT_MODE_END
                binding.fab.setImageDrawable(
                    ContextCompat.getDrawable(
                        context,
                        R.drawable.ic_back_fab
                    )
                )
                binding.bottomAppBar.replaceMenu(R.menu.menu_bottom_bar_other_screen)
            } else {
                isMain = true
                behavior.state = BottomSheetBehavior.STATE_COLLAPSED
                binding.bottomAppBar.navigationIcon =
                    ContextCompat.getDrawable(context, R.drawable.ic_hamburger_menu_bottom_bar)
                binding.bottomAppBar.fabAlignmentMode = BottomAppBar.FAB_ALIGNMENT_MODE_CENTER
                binding.fab.setImageDrawable(
                    ContextCompat.getDrawable(
                        context,
                        R.drawable.ic_plus_fab
                    )
                )
                binding.bottomAppBar.replaceMenu(R.menu.menu_bottom_bar)
            }
        }

        requireActivity()
            .onBackPressedDispatcher
            .addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    Log.d(TAG, "Fragment back pressed invoked")
                    if (isMain) {
                        System.exit(0)
                    } else {
                        isMain = true
                        binding.bottomAppBar.navigationIcon = ContextCompat.getDrawable(
                            context,
                            R.drawable.ic_hamburger_menu_bottom_bar
                        )
                        binding.bottomAppBar.fabAlignmentMode =
                            BottomAppBar.FAB_ALIGNMENT_MODE_CENTER
                        binding.fab.setImageDrawable(
                            ContextCompat.getDrawable(
                                context,
                                R.drawable.ic_plus_fab
                            )
                        )
                        binding.bottomAppBar.replaceMenu(R.menu.menu_bottom_bar)
                    }
                }
            }
            )
    }
}