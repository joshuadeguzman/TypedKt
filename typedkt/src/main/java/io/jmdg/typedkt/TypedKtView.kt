package io.jmdg.typedkt

import android.content.Context
import android.os.Handler
import android.util.AttributeSet
import android.widget.TextView


/**
 * Created by Joshua de Guzman on 18/07/2018.
 */

class TypedKtView(context: Context?, attrs: AttributeSet?) : TextView(context, attrs) {
    private lateinit var mText: CharSequence
    private var mTextList: List<String> = emptyList()
    private var currentIndex: Int = 0
    private var delay: Long = 150
    private val textHandler = Handler()
    private var listener: (() -> Unit)? = null

    private val characterAdder = object : Runnable {
        override fun run() {
            text = mText.subSequence(0, currentIndex++)
            if (currentIndex <= mText.length) {
                handler.postDelayed(this, delay)
            }
        }
    }

    private val wordAdder: Runnable = object : Runnable {
        override fun run() {
            text = mTextList.subList(0, currentIndex).joinToString(" ")
            if (currentIndex < mTextList.size) {
                currentIndex++
                handler.postDelayed(this, delay)
            } else {
                listener?.invoke()
            }
        }
    }

    fun setCharacterDelay(m: Long) {
        delay = m
    }

    fun animateText() {
        renderAnimation(true, text, null)
    }

    fun animateText(endAnimationListener: (() -> Unit)? = null) {
        renderAnimation(true, text, endAnimationListener)
    }

    fun animateText(charSequence: CharSequence, endAnimationListener: (() -> Unit)? = null) {
        renderAnimation(true, charSequence, endAnimationListener)
    }

    fun animateWords() {
        renderAnimation(false, text, null)
    }

    fun animateWords(endAnimationListener: (() -> Unit)? = null) {
        renderAnimation(false, text, endAnimationListener)
    }

    fun animateWords(charSequence: CharSequence, endAnimationListener: (() -> Unit)? = null) {
        renderAnimation(false, charSequence, endAnimationListener)
    }

    private fun renderAnimation(isAnimatedByCharacter: Boolean, charSequence: CharSequence, endAnimationListener: (() -> Unit)? = null) {
        currentIndex = 0
        listener = endAnimationListener

        if (isAnimatedByCharacter) {
            mText = text
            textHandler.removeCallbacks(characterAdder)
            textHandler.postDelayed(characterAdder, delay)
        } else {
            mTextList = charSequence.split(Regex("\\s+"))
            textHandler.removeCallbacks(wordAdder)
            textHandler.postDelayed(wordAdder, delay)
        }
    }
}