package com.example.notification_direct_reply

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.notification_direct_reply.databinding.FragmentFirstBinding
import com.example.notification_direct_reply.view_model.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FirstFragment : Fragment() {

        private lateinit var _binding: FragmentFirstBinding
        private val binding get() = _binding!!

        private val myViewModel: MainViewModel by viewModels()

        override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {
            // Inflate the layout for this fragment
            _binding = FragmentFirstBinding.inflate(inflater, container, false)

            binding.button.setOnClickListener {
                myViewModel.showSimpleNotification()
            }

            binding.button2.setOnClickListener {
                myViewModel.updateSimpleNotification()
            }

            binding.button3.setOnClickListener {
                myViewModel.cancelSimpleNotification()
            }

            return binding.root
        }

        override fun onDestroyView() {
            super.onDestroyView()
            _binding.root
        }
    }