package com.example.material_app.view.chips

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.material_app.databinding.FragmentSettingsBinding
import com.google.android.material.chip.Chip

private const val duration = 1000L

class SettingsFragment : Fragment() {
    private var _binding: FragmentSettingsBinding? = null
    private val binding: FragmentSettingsBinding
        get() {
            return _binding!!
        }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var isExpanded = false
        binding.transparentBackground.alpha = 0f
        binding.optionOneContainer.alpha = 0f
        binding.optionOneContainer.isClickable = false
        binding.optionTwoContainer.alpha = 0f
        binding.optionTwoContainer.isClickable = false

        binding.fab.setOnClickListener {
            if (isExpanded) {
                isExpanded = false
                ObjectAnimator.ofFloat(binding.plusImageview, "rotation", 315f, 0f)
                    .setDuration(duration).start()
                ObjectAnimator.ofFloat(binding.optionOneContainer, "translationY", 0f)
                    .setDuration(duration).start()
                ObjectAnimator.ofFloat(binding.optionTwoContainer, "translationY", 0f)
                    .setDuration(duration).start()
                binding.optionOneContainer.animate()
                    .alpha(0f)
                    .setDuration(duration)
                    .setListener(object : AnimatorListenerAdapter() {
                        override fun onAnimationEnd(animation: Animator?) {
                            super.onAnimationEnd(animation)
                            binding.optionOneContainer.isClickable = false
                        }
                    })
                binding.optionTwoContainer.animate()
                    .alpha(0f)
                    .setDuration(duration)
                    .setListener(object : AnimatorListenerAdapter() {
                        override fun onAnimationEnd(animation: Animator?) {
                            super.onAnimationEnd(animation)
                            binding.optionTwoContainer.isClickable = false
                        }
                    })
                binding.transparentBackground.animate()
                    .alpha(0f)
                    .setDuration(duration)
            } else {
                isExpanded = true
                ObjectAnimator.ofFloat(binding.plusImageview, "rotation", 0f, 315f)
                    .setDuration(duration).start()
                ObjectAnimator.ofFloat(binding.optionOneContainer, "translationY", -300f)
                    .setDuration(duration).start()
                ObjectAnimator.ofFloat(binding.optionTwoContainer, "translationY", -150f)
                    .setDuration(duration).start()
                binding.optionOneContainer.animate()
                    .alpha(1f)
                    .setDuration(duration)
                    .setListener(object : AnimatorListenerAdapter() {
                        override fun onAnimationEnd(animation: Animator?) {
                            super.onAnimationEnd(animation)
                            binding.optionOneContainer.isClickable = true
                            binding.optionTwoContainer.animate()
                                .alpha(1f)
                                .setDuration(duration)
                                .setListener(object : AnimatorListenerAdapter() {
                                    override fun onAnimationEnd(animation: Animator?) {
                                        super.onAnimationEnd(animation)
                                        binding.optionTwoContainer.isClickable = true
                                    }
                                })

                            binding.transparentBackground.animate()
                                .alpha(0.4f)
                                .setDuration(0)
                        }

                    })
            }
        }

    }


    companion object {

        @JvmStatic
        fun newInstance() =
            SettingsFragment()
    }
}