package com.kod.habittracker.ui.introscreen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.viewpager2.widget.ViewPager2
import com.kod.habittracker.R
import com.kod.habittracker.data.model.IntroView
import com.kod.habittracker.databinding.ActivityIntroScreenBinding
import com.kod.habittracker.ui.mainactivity.MainActivity

class IntroScreenActivity : AppCompatActivity() {
    private lateinit var introViewList: List<IntroView>
    private lateinit var binding: ActivityIntroScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addIntroView()
        binding = ActivityIntroScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        supportActionBar?.setIcon(
            R.drawable.ic_spa
        )

        val numbOfPage = introViewList.size-1
        binding.viewPager2.adapter = ViewPagerAdapter(introViewList)
        binding.viewPager2.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        binding.circleIndicator.setViewPager(binding.viewPager2)

        binding.viewPager2.registerOnPageChangeCallback(object: ViewPager2.OnPageChangeCallback(){
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                if(position == numbOfPage){
                    animationButton()
                }else{
                    binding.btnStartApp.visibility = View.GONE
                }
                super.onPageScrolled(position, positionOffset, positionOffsetPixels)
            }
        })
    }

    private fun animationButton(){
        binding.btnStartApp.visibility = View.VISIBLE
        binding.btnStartApp.animate().apply {
            duration = 1800
            alpha(1f)
            binding.btnStartApp.setOnClickListener {
                startActivity(Intent(applicationContext, MainActivity::class.java))
                finish()
            }
        }.start()
    }

    private fun addIntroView(){
        introViewList = listOf(
            IntroView("Welcome to bad habit monitoring app." +
                    "\n\nThis app helps you track down your bad habits and hence " +
                    "helps you in managing and avoidong them", R.drawable.ic_spa
            ),
            IntroView("Smoking is not good for the heath. Set a reminder to " +
                    "help you reduce your addiction\n Just select me",R.drawable.ic_smoking_stop),
            IntroView("Junk food is not healthy for your health. " +
                    "Consume more fresh beverages than gaseous beverages" +
                    "\n\nWhy don't you let us remind  you to start eating healthy as from today",R.drawable.ic_fast_food),
            IntroView("Not doing enough sport?\nI am here to help",R.drawable.ic_running),
            IntroView("Sleeping early will enable your body to be in " +
                    "perfect fitness for the next morning\nWe can set a sleeping and a " +
                    "get up reminder ",R.drawable.ic_sleep),
            IntroView("Pourquoi ne pas utiliser son temps libre pour regarder des videos intuitives et utiles?\n\n" +
                "Laissez moi vous aider", R.drawable.ic_video)
        )
    }
}