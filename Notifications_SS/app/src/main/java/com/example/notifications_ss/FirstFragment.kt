package com.example.notifications_ss

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.notifications_ss.databinding.FragmentFirstBinding
import com.example.notifications_ss.view_model.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FirstFragment : Fragment() {

    private lateinit var _binding: FragmentFirstBinding
    private val binding get() = _binding!!

    //    private val myUserViewModel: MainViewModel by viewModels()
    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentFirstBinding.inflate(inflater, container, false)

        binding.button.setOnClickListener {
            mainViewModel.showSimpleNotification()
        }
        binding.button2.setOnClickListener {
            mainViewModel.updateSimpleNotification()
        }
        binding.button3.setOnClickListener {
            mainViewModel.cancelSimpleNotification()
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding.root
    }
}