package com.soneralci.catchthecoffeekotlin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.ImageView
import com.soneralci.catchthecoffeekotlin.databinding.ActivityMenuBinding
import java.util.Random

class MenuActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMenuBinding
    var imageArray = ArrayList<ImageView>()
    var runnable = Runnable {}
    var handler = Handler(Looper.getMainLooper())
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMenuBinding.inflate(layoutInflater)
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


    }

    fun play(view: View) {
        val intent = Intent(this@MenuActivity, MainActivity::class.java)
        startActivity(intent)
    }

    fun quit() {
        onBackPressed()
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


}