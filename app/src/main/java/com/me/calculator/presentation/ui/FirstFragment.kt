package com.me.calculator.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.me.calculator.databinding.FragmentFirstBinding
import com.me.calculator.presentation.viewmodel.CalculatorViewModel

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private lateinit var _binding: FragmentFirstBinding
    private val viewModel: CalculatorViewModel by viewModels()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding.viewModel = viewModel
        _binding.lifecycleOwner = viewLifecycleOwner
        lifecycleScope.launchWhenStarted {
            viewModel.uiState.collect {
                _binding.calculation.text = it.calculation
                _binding.result.text = it.result
            }
        }
     }
}