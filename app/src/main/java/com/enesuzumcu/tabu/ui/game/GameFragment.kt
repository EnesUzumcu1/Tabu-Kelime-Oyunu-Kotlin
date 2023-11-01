package com.enesuzumcu.tabu.ui.game

import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.enesuzumcu.tabu.R
import com.enesuzumcu.tabu.data.model.Settings
import com.enesuzumcu.tabu.data.model.Teams
import com.enesuzumcu.tabu.databinding.FragmentGameBinding
import com.enesuzumcu.tabu.ui.game.viewmodel.GameViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlin.Long
import kotlin.getValue

@AndroidEntryPoint
class GameFragment : Fragment() {
    private lateinit var binding: FragmentGameBinding
    private lateinit var navController: NavController
    private val viewModel by viewModels<GameViewModel>()

    private var backPressTime: Long = 0
    private var mCountDownTimer: CountDownTimer? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentGameBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tanimlamalar()
        if (viewModel.turn > viewModel.maxTurn) {
            binding.btnStart.setOnClickListener { navigateHomeFragment() }
            binding.btnStart.setText(R.string.gameOver)
            binding.cvInformation.visibility = View.GONE
            binding.tvWinner.text = viewModel.winner()
        } else {
            binding.btnStart.setOnClickListener {
                kelimeleriYazdir()
                binding.llResult.visibility = View.GONE
                binding.cvInformation.visibility = View.VISIBLE
                binding.pasHakkiTextView.text = viewModel.gameStatus!!.pass.toString() + "/" + Settings.settings!!.pass
                binding.tvScore.text = "Puan: " + viewModel.gameStatus!!.score.toString()
                binding.tvTeamName.text = viewModel.getTeamName()
                binding.cvWords.visibility = View.VISIBLE
                binding.clButtons.visibility = View.VISIBLE
                binding.btnSuccess.isEnabled = true
                binding.btnTaboo.isEnabled = true
                binding.btnPass.isEnabled = true
                binding.pbSecond.max = viewModel.second
                timer()
            }
        }
        binding.btnPass.setOnClickListener {
            if (viewModel.gameStatus!!.pass > 0) {
                viewModel.decreasePass()
                binding.pasHakkiTextView.text = viewModel.gameStatus!!.pass.toString()  + "/" + Settings.settings!!.pass
                viewModel.getWords()
                kelimeleriYazdir()
            }
        }

        binding.btnTaboo.setOnClickListener {
            viewModel.decreaseScore()
            binding.tvScore.text = "Puan: " + viewModel.gameStatus!!.score.toString()
            viewModel.getWords()
            kelimeleriYazdir()
        }

        binding.btnSuccess.setOnClickListener {
            viewModel.increaseScore()
            binding.tvScore.text = "Puan: " + viewModel.gameStatus!!.score.toString()
            viewModel.getWords()
            kelimeleriYazdir()
        }

        //backpress basildiginda gerekli durum icin callback cagirildi
        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    // Handle the back button event
                    onBackPressed()
                }
            })
    }
    fun onBackPressed() {
        val toast = Toast.makeText(
            requireContext(),
            "Giriş sayfasına gitmek için bir daha tıklayın.",
            Toast.LENGTH_SHORT
        )
        if (backPressTime + 2000 > System.currentTimeMillis()) {
            toast.cancel()
            navigateHomeFragment()
        } else {
            toast.show()
        }
        backPressTime = System.currentTimeMillis()
    }

    private fun timer() {
        //miliSaniyeye +100 eklenmesinin sebebi Android 10'dan önceki sürümlerde zamanlayıcıda oluşan bugu önlemek.
        //eğer eklenmezse Android 10 öncesinde saniye 1 olunca duruyor ve 0 değerini almıyor.
        //Android 10'daki çalışan sistemi bozmamak için artık saniye kontrolü yaparak azaltma işlemi yapılıyor.
        mCountDownTimer = object : CountDownTimer(viewModel.second * 1000L + 100, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                if (viewModel.second > 0) {
                    binding.tvSecond.text = viewModel.second.toString()
                    binding.pbSecond.progress = viewModel.second
                    viewModel.decreaseSecond()
                }
            }

            override fun onFinish() {
                binding.tvSecond.text = viewModel.second.toString()
                binding.llResult.visibility = View.VISIBLE
                binding.cvWords.visibility = View.GONE
                binding.clButtons.visibility = View.GONE
                binding.pbSecond.progress = 0
                getTeamScoreTV()?.text = viewModel.updateScore().toString()
                if (viewModel.turn == viewModel.maxTurn) {
                    binding.btnStart.setOnClickListener { navigateHomeFragment() }
                    binding.btnStart.setText(R.string.gameOver)
                    binding.cvInformation.visibility = View.GONE
                    binding.tvWinner.text = viewModel.winner()
                } else {
                    binding.btnSuccess.isEnabled = false
                    binding.btnTaboo.isEnabled = false
                    binding.btnPass.isEnabled = false
                    binding.btnStart.setText(R.string.nextTeam)
                }
                viewModel.increaseTurn()
                viewModel.resetState()
            }
        }.start()
    }

    private fun tanimlamalar() {
        navController = findNavController()
        binding.pasHakkiTextView.text = viewModel.gameStatus!!.pass.toString()  + "/" + Settings.settings!!.pass
        binding.tvSecond.text = viewModel.second.toString()
        binding.tvScore.text = "Puan: " + viewModel.gameStatus!!.score.toString()
        if (viewModel.inTheGame) {
            binding.cvWords.visibility = View.VISIBLE
            binding.clButtons.visibility = View.VISIBLE
            binding.llResult.visibility = View.GONE
        } else {
            binding.clButtons.visibility = View.GONE
        }
        //takım sayısına göre oyun arasında skor tablosunu hazırlıyor
        binding.tvTeamName1.text = Teams.team1?.teamName
        binding.tvTeamName2.text = Teams.team2?.teamName
        binding.tvTeamScore1.text = "${Teams.team1?.teamScore}"
        binding.tvTeamScore2.text = "${Teams.team2?.teamScore}"
        binding.llTeam3Info.visibility = View.GONE
        binding.llTeam4Info.visibility = View.GONE
        if (Settings.settings!!.teamCount >= 3) {
            binding.tvTeamName3.text = Teams.team3?.teamName
            binding.tvTeamScore3.text = "${Teams.team3?.teamScore}"
            binding.llTeam3Info.visibility = View.VISIBLE
        }
        if (Settings.settings!!.teamCount == 4) {
            binding.tvTeamName4.text = Teams.team4?.teamName
            binding.tvTeamScore4.text = "${Teams.team4?.teamScore}"
            binding.llTeam4Info.visibility = View.VISIBLE
        }
        //Skor tablosu hazırlıgı sonu
        binding.tvTeamName.text = viewModel.getTeamName()
    }

    private fun navigateHomeFragment() {
        val options: NavOptions =
            NavOptions.Builder()
                .setEnterAnim(R.anim.slide_from_left)
                .setExitAnim(R.anim.slide_to_right)
                .setPopEnterAnim(R.anim.slide_from_left)
                .setPopExitAnim(R.anim.slide_to_right)
                .setPopUpTo(R.id.nav_graph, true)
                .build()
        navController.navigate(R.id.action_gameFragment_to_homeFragment, null, options)
        viewModel.resetState()
    }

    private fun kelimeleriYazdir() {
        val words = viewModel.words
        binding.tvTargetWord.text = words?.AnlatilanKelime
        binding.tvTabooWord1.text = words?.Tabu1
        binding.tvTabooWord2.text = words?.Tabu2
        binding.tvTabooWord3.text = words?.Tabu3
        binding.tvTabooWord4.text = words?.Tabu4
        binding.tvTabooWord5.text = words?.Tabu5
    }

    fun getTeamScoreTV(): TextView? {
        return when (viewModel.turn % Settings.settings!!.teamCount) {
            0 -> binding.tvTeamScore1
            1 -> binding.tvTeamScore2
            2 -> binding.tvTeamScore3
            3 -> binding.tvTeamScore4
            else -> null
        }
    }

    override fun onPause() {
        super.onPause()
        mCountDownTimer?.cancel()
    }

    override fun onResume() {
        super.onResume()
        if (viewModel.inTheGame) {
            kelimeleriYazdir()
            timer()
        }
    }
}