package com.enesuzumcu.tabu.ui.setteamname

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.enesuzumcu.tabu.R
import com.enesuzumcu.tabu.data.model.Settings
import com.enesuzumcu.tabu.databinding.FragmentSetTeamNameBinding
import com.enesuzumcu.tabu.ui.setteamname.viewmodel.SetTeamNameViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SetTeamNameFragment : Fragment() {
    private lateinit var binding: FragmentSetTeamNameBinding
    private lateinit var navController: NavController
    private val viewModel by viewModels<SetTeamNameViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSetTeamNameBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tanimlamalar()
        binding.btnSave.setOnClickListener {
            val check1 = binding.etTeam1.text.toString() != ""
            val check2 = binding.etTeam2.text.toString() != ""
            val check3 = binding.etTeam3.text.toString() != ""
            val check4 = binding.etTeam4.text.toString() != ""
            if (check1 && check2) {
                viewModel.setTeam1andTeam2Names(
                    binding.etTeam1.text.toString(),
                    binding.etTeam2.text.toString()
                )
            }
            if (Settings.settings!!.teamCount >= 3) {
                if (check3) {
                    viewModel.setTeam3Name(binding.etTeam3.text.toString())
                }
            }
            if (Settings.settings!!.teamCount == 4) {
                if (check4) {
                    viewModel.setTeam4Name(binding.etTeam4.text.toString())
                }
            }
            navController.navigate(R.id.action_setTeamNameFragment_to_gameFragment)
        }

        binding.toolbar.title.text = getString(R.string.setTeamNameFixedText)

        binding.toolbar.ivBackArrow.setOnClickListener {
            backPressNavigate()
        }

        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    backPressNavigate()
                }
            })
    }

    private fun backPressNavigate(){
        // Handle the back button event
        val options: NavOptions =
            NavOptions.Builder()
                .setEnterAnim(R.anim.slide_from_left)
                .setExitAnim(R.anim.slide_to_right)
                .setPopEnterAnim(R.anim.slide_from_left)
                .setPopExitAnim(R.anim.slide_to_right)
                .setPopUpTo(R.id.nav_graph, true)
                .build()
        navController.navigate(R.id.homeFragment, null, options)
    }

    private fun tanimlamalar() {
        navController = findNavController()
        if (Settings.settings!!.teamCount == 2) {
            binding.llTeam3.visibility = View.GONE
            binding.llTeam4.visibility = View.GONE
        } else if (Settings.settings!!.teamCount == 3) {
            binding.llTeam4.visibility = View.GONE
        }
    }
}