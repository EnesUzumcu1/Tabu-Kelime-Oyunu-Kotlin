package com.enesuzumcu.tabu.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.enesuzumcu.tabu.R
import com.enesuzumcu.tabu.databinding.FragmentHomeBinding
import com.enesuzumcu.tabu.ui.home.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = findNavController()
        binding.btnStart.setOnClickListener { navController.navigate(R.id.action_homeFragment_to_setTeamNameFragment) }
        binding.btnSettings.setOnClickListener { navController.navigate(R.id.action_homeFragment_to_settingsFragment) }
        val viewModel : HomeViewModel = ViewModelProvider(this)[HomeViewModel::class.java]
    }
}