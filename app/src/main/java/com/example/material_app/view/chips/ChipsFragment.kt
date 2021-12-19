package com.example.material_app.view.chips

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.material_app.R
import com.example.material_app.databinding.FragmentChipsBinding
import com.example.material_app.databinding.FragmentMainBinding
import com.example.material_app.viewmodel.PictureOfTheDayViewModel
import com.google.android.material.chip.Chip


class ChipsFragment : Fragment() {
    private var _binding: FragmentChipsBinding? = null
    val binding: FragmentChipsBinding
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
        _binding = FragmentChipsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.chipGroup.setOnCheckedChangeListener { group, checkedId ->
            group.findViewById<Chip>(checkedId)?.let {
                Toast.makeText(context, "choose ${it.text}", Toast.LENGTH_SHORT).show()
            }
        }

        binding.chipForDelete.setOnCloseIconClickListener {
            binding.chipForDelete.visibility = View.GONE
        }
    }

    companion object {

        @JvmStatic
        fun newInstance() =
            ChipsFragment()
    }
}