package com.soneralci.catchthecoffeekotlin

import android.content.Context
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.soneralci.catchthecoffeekotlin.databinding.ActivityMainBinding
import java.util.Random

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    var highScore = 0;
    var score = 0
    var imageArray = ArrayList<ImageView>()
    var runnable = Runnable {}
    var handler = Handler(Looper.getMainLooper())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)


        imageArray.add(binding.imageView)
        imageArray.add(binding.imageView2)
        imageArray.add(binding.imageView3)
        imageArray.add(binding.imageView4)
        imageArray.add(binding.imageView5)
        imageArray.add(binding.imageView6)
        imageArray.add(binding.imageView7)
        imageArray.add(binding.imageView8)
        imageArray.add(binding.imageView9)

        hideImages()

        val sharedPreferences = getSharedPreferences("MyAppPreferences", Context.MODE_PRIVATE)
        highScore = sharedPreferences.getInt("HighScore", 0)
        binding.highScoreText.text = "High Score: ${highScore}"

        //CountDownTimer

        object : CountDownTimer(20500, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                binding.timeText.text = "Time ${millisUntilFinished / 1000}"
            }

            override fun onFinish() {
                binding.timeText.text = "Time : 0"
                handler.removeCallbacks(runnable)
                for (image in imageArray) {
                    image.visibility = View.INVISIBLE
                }
                //alert dialog
                val alert = AlertDialog.Builder(this@MainActivity)
                alert.setTitle("Game Over")
                alert.setMessage("Restart The Game")
                alert.setPositiveButton("Yes", DialogInterface.OnClickListener
                { dialog, which ->
                    if (score > highScore) {
                        highScore = score
                        binding.highScoreText.text = "High Score: ${highScore}"
                        savedHighScore(highScore)
                        Toast.makeText(
                            this@MainActivity,
                            "New High Score:${highScore}",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                    resetGame()
                    val intent = intent
                    finish()
                    startActivity(intent)
                })

                alert.setNegativeButton("No", DialogInterface.OnClickListener { dialog, which ->
                    Toast.makeText(this@MainActivity, "Game Over!", Toast.LENGTH_LONG).show()
                })

                alert.show()
            }

        }.start()

    }

    fun hideImages() {
        //binding.imageView.visibility = View.GONE
        //View.GONE, View.INVISIBLE vs View.VISIBILE
        runnable = object : Runnable {
            override fun run() {
                for (image in imageArray) {
                    image.visibility = View.INVISIBLE
                }
                val random = Random()
                val randomIndex = random.nextInt(9)
                imageArray[randomIndex].visibility = View.VISIBLE

                handler.postDelayed(runnable, 500)


            }
        }
        handler.post(runnable)
    }

    fun increaseScore(view: View) {
        score = score + 1
        binding.scoreText.text = "Score : ${score}"

    }

    fun savedHighScore(highScore: Int) {
        val sharedPreferences = getSharedPreferences("MyAppPreferences", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putInt("HighScore", highScore)
        editor.apply()
    }

    fun resetGame() {
        score = 0
        binding.scoreText.text = "Score : $score"
    }
}
