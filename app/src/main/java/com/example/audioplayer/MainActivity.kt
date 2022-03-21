package com.example.audioplayer

import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.SeekBar
import com.example.audioplayer.databinding.ActivityMainBinding
import java.lang.Exception

class MainActivity : AppCompatActivity() {

    private var mp : MediaPlayer? = null
    private var currentSong = mutableListOf(R.raw.sample)

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        controlSound(currentSong[0])
    }

    private fun controlSound(id:Int){
        binding.fabPlay.setOnClickListener {
            if(mp == null){
                mp = MediaPlayer.create(this,id)

                intializeSeekBar()
            }
            mp?.start()
        }

        binding.fabPause.setOnClickListener {
            if (mp != null){
                mp?.pause()
            }
        }

        binding.fabStop.setOnClickListener {
            if (mp != null) {
                mp?.stop()
                mp?.reset()
                mp?.release()
                mp = null
            }
        }

        binding.seekBar.setOnSeekBarChangeListener(object :
            SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) {
                    mp?.seekTo(progress)
                }
            }
            override fun onStartTrackingTouch(p0: SeekBar?) {
            }
            override fun onStopTrackingTouch(p0: SeekBar?) {
            }


        })
    }

    private fun intializeSeekBar(){

        binding.seekBar.max = mp!!.duration

        val handler = Handler()
        handler.postDelayed(object :Runnable{
            override fun run() {
                try {
                    binding.seekBar.progress = mp!!.currentPosition
                    handler.postDelayed(this, 1000)
                }catch (e:Exception){
                    binding.seekBar.progress = 0
                }

            }

        },0)

    }

}