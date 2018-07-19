package io.jmdg.typedkt

import android.content.Context
import android.os.Handler
import android.util.AttributeSet
import android.widget.TextView
import io.jmdg.typedkt.config.TypeWriterMode


/**
 * Created by Joshua de Guzman on 18/07/2018.
 */

class TypedKtView(context: Context?, attrs: AttributeSet?) : TextView(context, attrs) {
    private lateinit var mText: CharSequence
    private var mTextList: List<String> = emptyList()
    private var mTextListDelimiter: Regex = Regex("\\s+")
    private var currentIndex: Int = 0
    private var delay: Long = 150
    private val textHandler = Handler()
    private var listener: (() -> Unit)? = null
    private var isLooped: Boolean = false
    private var typeWriterMode = TypeWriterMode.CHARACTERS

    private val characterAdder = object : Runnable {
        override fun run() {
            if (currentIndex <= mText.length) {
                text = mText.subSequence(0, currentIndex++)
                handler.postDelayed(this, delay)
            } else {
                if (isLooped) {
                    currentIndex = 0
                    text = ""
                    run()
                } else {
                    listener?.invoke()
                }
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
                if (isLooped) {
                    currentIndex = 0
                    text = ""
                    run()
                } else {
                    listener?.invoke()
                }
            }
        }
    }

    fun setAnimationByCharacter() {
        typeWriterMode = TypeWriterMode.CHARACTERS
    }

    fun setAnimationByWord(regex: Regex = mTextListDelimiter) {
        mTextListDelimiter = regex
        typeWriterMode = TypeWriterMode.WORDS
    }

    fun setEndAnimationListener(endAnimationListener: (() -> Unit)? = null) {
        listener = endAnimationListener
    }

    fun setCharacterDelay(delay: Long) {
        this.delay = delay
    }

    fun setLooped(isLooped: Boolean) {
        this.isLooped = isLooped
    }

    fun animateText(charSequence: CharSequence = "") {
        val mString = validateString(charSequence)
        currentIndex = 0

        if (typeWriterMode == TypeWriterMode.CHARACTERS) {
            mText = mString
            textHandler.removeCallbacks(characterAdder)
            textHandler.postDelayed(characterAdder, delay)
        } else {
            mTextList = mString.split(mTextListDelimiter)
            textHandler.removeCallbacks(wordAdder)
            textHandler.postDelayed(wordAdder, delay)
        }
    }

    private fun validateString(charSequence: CharSequence): CharSequence {
        if (!charSequence.trim().isEmpty()) {
            return charSequence
        }
        return text
    }

    fun removeAnimation() {
        if (typeWriterMode == TypeWriterMode.CHARACTERS) {
            textHandler.removeCallbacks(characterAdder)
        } else {
            textHandler.removeCallbacks(wordAdder)
        }
    }
}