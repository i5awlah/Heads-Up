package com.example.headsup

import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import com.example.headsup.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONArray
import java.net.URL
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    private val url = "https://dojo-recipes.herokuapp.com/celebrities/?format=json"
    var celebrities = arrayListOf<Celebrity>()
    var timer = 60_000L
    private lateinit var countDownTimer: CountDownTimer
    private var isStart = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        requestAPI()

        // Use a start button to initiate the game
        binding.btnStart.setOnClickListener {
            isStart = true
            startGame()
        }
    }

    private fun requestAPI() {
        CoroutineScope(IO).launch {
            val data = async { fetchData() }.await()
            if (data.isNotEmpty()) {
                populate(data)
            } else {
                Log.d("Main","empty!")
            }
        }
    }

    private fun fetchData(): String {
        var response = ""
        try {
            response = URL("$url").readText()
        } catch (e: Exception) {
            Log.d("Main","Error: $e")
        }
        return response
    }

    private suspend fun populate(data: String) {
        val celebritiesJsonArray = JSONArray(data)
        for (i in 0 until celebritiesJsonArray.length()) {
            val pk = celebritiesJsonArray.getJSONObject(i).getInt("pk")!!
            val name = celebritiesJsonArray.getJSONObject(i).getString("name")!!
            val taboo1 = celebritiesJsonArray.getJSONObject(i).getString("taboo1")!!
            val taboo2 = celebritiesJsonArray.getJSONObject(i).getString("taboo2")!!
            val taboo3 = celebritiesJsonArray.getJSONObject(i).getString("taboo3")!!
            celebrities.add(Celebrity(name, pk, taboo1, taboo2, taboo3))
        }


        withContext(Main) {
            Log.d("Main","Data: $celebrities")
        }
    }

    private fun startGame() {
        //  Once the game starts, a 60 second timer should be displayed and count down
        startTimer()
        countDownTimer.cancel()

        binding.btnStart.visibility = View.GONE
        binding.tvMain.text = "Please Rotate Device"
    }


    private fun startTimer() {
        countDownTimer = object : CountDownTimer(timer,1000){

            override fun onFinish() {
                Log.d("Main", "end!")
                isStart = false
            }

            override fun onTick(millisUntilFinished: Long) {
                timer = millisUntilFinished
                Log.d("Main","Timer: $timer")
                title = "Time: ${millisUntilFinished/1000}"
            }

        }.start()
    }

    private fun generateNewCelebrity() {
        // The phone should display a Celebrity name and the three taboo words
        val randomNumber = Random.nextInt(0, celebrities.size)
        binding.tvCelebrity.text = celebrities[randomNumber].name
        binding.tvTaboo1.text = celebrities[randomNumber].taboo1
        binding.tvTaboo2.text = celebrities[randomNumber].taboo2
        binding.tvTaboo3.text = celebrities[randomNumber].taboo3
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        if (isStart) {
            if (newConfig.orientation === Configuration.ORIENTATION_LANDSCAPE) {
                startTimer()
                Log.d("Main", "ORIENTATION_LANDSCAPE")
                // Rotating the device should move to the next celebrity
                generateNewCelebrity()
                binding.tvMain.visibility = View.GONE
                binding.llCelebrity.visibility = View.VISIBLE
            } else {
                countDownTimer.cancel()
                Log.d("Main", "ORIENTATION_PORTRAIT")
                binding.tvMain.visibility = View.VISIBLE
                binding.llCelebrity.visibility = View.GONE

            }
        }
    }
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putLong("timer", timer)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        timer = savedInstanceState.getLong("timer", 70_000L)
    }
}