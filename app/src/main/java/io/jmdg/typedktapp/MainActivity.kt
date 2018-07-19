package io.jmdg.typedktapp

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bt_animate.setOnClickListener {
            // Animate view with XML configuration
            tkv_animate_xml.animateText()

            // Animate by characters
            tkv_character.setLooped(true)

            // Setup options before calling this method for it to take effect
            tkv_character.animateText()

            // Animate by word
            tkv_words.setAnimationByWord()
            tkv_words.setEndAnimationListener {
                Log.e("JDG", "END WORD")
            }
            tkv_words.setLooped(true)

            // Setup options before calling this method for it to take effect
            tkv_words.animateText()
        }

        bt_skip.setOnClickListener {
            tkv_character.skipAnimation()
            tkv_words.skipAnimation()
        }

        bt_stop.setOnClickListener {
            tkv_character.stopAnimation()
            tkv_words.stopAnimation()
        }
    }

    override fun onResume() {
        super.onResume()
        // Resume animation
        if(tkv_animate_xml.isAnimating()){
            tkv_animate_xml.animateText()
        }

        if(tkv_character.isAnimating()){
            tkv_character.animateText()
        }

        if(tkv_words.isAnimating()){
            tkv_words.animateText()
        }
    }

    override fun onPause() {
        super.onPause()
        // Pause animation
        tkv_animate_xml.skipAnimation()
        tkv_character.skipAnimation()
        tkv_words.skipAnimation()
    }

    override fun onDestroy() {
        super.onDestroy()
        // Remove callbacks
        tkv_animate_xml.removeAnimation()
        tkv_character.removeAnimation()
        tkv_words.removeAnimation()
    }
}
