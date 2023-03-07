package com.enesuzumcu.tabu.ui.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.enesuzumcu.tabu.R
import com.enesuzumcu.tabu.data.model.Game
import com.enesuzumcu.tabu.data.model.Settings
import com.enesuzumcu.tabu.databinding.FragmentSettingsBinding
import com.enesuzumcu.tabu.ui.settings.viewmodel.SettingsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingsFragment : Fragment() {
    private lateinit var binding: FragmentSettingsBinding
    private lateinit var navController: NavController
    private val viewModel by viewModels<SettingsViewModel>()

    private lateinit var takimSayisiAdapter: ArrayAdapter<Int>
    private lateinit var sureAdapter: ArrayAdapter<Int>
    private lateinit var turSayisiAdapter: ArrayAdapter<Int>
    private lateinit var pasHakkiAdapter: ArrayAdapter<Int>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSettingsBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tanimlamalar()

        binding.btnSave.setOnClickListener {
            viewModel.save()
            Game.gameStatus.pass = Settings.settings!!.pass
            Toast.makeText(requireContext(), "Kaydedildi.", Toast.LENGTH_SHORT).show()
            navController.navigate(R.id.action_settingsFragment_to_setTeamNameFragment)
        }
    }

    private fun tanimlamalar(){
        navController = findNavController()
        setTeamNumberSpinnerAdapter()
        setTimeSpinnerAdapter()
        setRoundNumberSpinnerAdapter()
        setPassNumberSpinnerAdapter()
    }

    private fun setTeamNumberSpinnerAdapter() {
        val takimDegerleri = arrayOf(2, 3, 4)
        takimSayisiAdapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, takimDegerleri)
        takimSayisiAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.sTeamNumber.adapter = takimSayisiAdapter
        binding.sTeamNumber.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                viewModel.setTeamCount(parent.getItemAtPosition(position).toString().toInt())
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
        //seçilen değerin sonraki açılışta seçili gelmesi için.
        binding.sTeamNumber.setSelection(takimSayisiAdapter.getPosition(Settings.settings!!.teamCount))
        binding.sTeamNumber.dropDownVerticalOffset = 100
    }

    private fun setTimeSpinnerAdapter() {
        val sureDegerleri = arrayOf(5, 60, 120, 150, 180, 210, 240)
        sureAdapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, sureDegerleri)
        sureAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.sTime.adapter = sureAdapter
        binding.sTime.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                viewModel.setTime(parent.getItemAtPosition(position).toString().toInt())
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
        //seçilen değerin sonraki açılışta seçili gelmesi için.
        binding.sTime.setSelection(sureAdapter.getPosition(Settings.settings!!.time))
        binding.sTime.dropDownVerticalOffset = 100
    }

    private fun setRoundNumberSpinnerAdapter() {
        val turDegerleri = arrayOf(1, 3, 5, 7, 9, 15)
        turSayisiAdapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, turDegerleri)
        turSayisiAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.sRoundNumber.adapter = turSayisiAdapter
        binding.sRoundNumber.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                viewModel.setRoundCount(parent.getItemAtPosition(position).toString().toInt())
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
        //seçilen değerin sonraki açılışta seçili gelmesi için.
        binding.sRoundNumber.setSelection(turSayisiAdapter.getPosition(Settings.settings!!.round))
        binding.sRoundNumber.dropDownVerticalOffset = 100
    }

    private fun setPassNumberSpinnerAdapter() {
        val pasDegerleri = arrayOf(5, 10, 15, 20, 25, 100)
        pasHakkiAdapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, pasDegerleri)
        pasHakkiAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.sPassNumber.adapter = pasHakkiAdapter
        binding.sPassNumber.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                viewModel.setPass(parent.getItemAtPosition(position).toString().toInt())
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
        //seçilen değerin sonraki açılışta seçili gelmesi için.
        binding.sPassNumber.setSelection(pasHakkiAdapter.getPosition(Settings.settings!!.pass))
        binding.sPassNumber.dropDownVerticalOffset = 100
    }
}