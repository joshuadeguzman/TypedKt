package io.jmdg.typedkt

import android.content.Context
import android.content.res.TypedArray
import android.os.Handler
import android.util.AttributeSet
import android.widget.TextView
import io.jmdg.typedkt.config.TypeWriterMode


/**
 * Created by Joshua de Guzman on 18/07/2018.
 */

class TypedKtView(context: Context, attributeSet: AttributeSet) : TextView(context, attributeSet) {
    private lateinit var mText: CharSequence
    private var mTextList: List<String> = emptyList()
    private var mTextListDelimiter: String = "\\s+"
    private var currentIndex: Int = 0
    private var delay: Long = 150
    private var isAnimating: Boolean = false
    private val textHandler = Handler()
    private var listener: (() -> Unit)? = null
    private var isLooped: Boolean = false
    private var typeWriterMode = TypeWriterMode.CHARACTERS
    private var typedArray: TypedArray = context.theme.obtainStyledAttributes(
            attributeSet,
            R.styleable.TypedKtView,
            0, 0)

    private var isTypedArrayRecycled: Boolean = false

    // Configuration validators if set programmatically
    private var isAnimatedByWordSet = false
    private var isDelaySet = false
    private var isLoopedSet = false

    private val characterAdder = object : Runnable {
        override fun run() {
            if (currentIndex <= mText.length) {
                text = mText.subSequence(0, currentIndex++)
                handler.postDelayed(this, delay)
            } else {
                validateLoop(::run)
            }
        }
    }

    private val wordAdder: Runnable = object : Runnable {
        override fun run() {
            if (currentIndex <= mTextList.size) {
                text = mTextList.subList(0, currentIndex).joinToString(" ")
                currentIndex++
                handler.postDelayed(this, delay)
            } else {
                validateLoop(::run)
            }
        }
    }

    private fun validateLoop(run: () -> Unit) {
        if (isLooped) {
            currentIndex = 0
            text = ""
            run()
        } else {
            isAnimating = false
            listener?.invoke()
        }
    }

    fun setAnimationByCharacter() {
        typeWriterMode = TypeWriterMode.CHARACTERS
    }

    fun setAnimationByWord(delimiter: String = mTextListDelimiter) {
        this.isAnimatedByWordSet = true
        mTextListDelimiter = delimiter
        typeWriterMode = TypeWriterMode.WORDS
    }

    fun setEndAnimationListener(endAnimationListener: (() -> Unit)? = null) {
        listener = endAnimationListener
    }

    fun setDelay(delay: Long) {
        this.isDelaySet = true
        this.delay = delay
    }

    fun setLooped(isLooped: Boolean) {
        this.isLoopedSet = true
        this.isLooped = isLooped
    }

    fun animateText(charSequence: CharSequence = "") {
        try {
            if (!isAnimating) {
                isAnimating = true

                if (!isTypedArrayRecycled) {
                    validateConfigurations()
                }

                val mString = validateString(charSequence)
                currentIndex = 0

                if (typeWriterMode == TypeWriterMode.CHARACTERS) {
                    mText = mString
                    textHandler.removeCallbacks(characterAdder)
                    textHandler.postDelayed(characterAdder, delay)
                } else {
                    mTextList = mString.split(Regex(mTextListDelimiter))
                    textHandler.removeCallbacks(wordAdder)
                    textHandler.postDelayed(wordAdder, delay)
                }
            }
        } finally {
            /**
             * This is required for caching purpose. When you call recycle it means that this object can be reused from this point.
             * Internally TypedArray contains few arrays so in order not to allocate memory each time when
             * TypedArray is used it is cached in Resources class as static field.
             * https://stackoverflow.com/questions/13805502/why-should-a-typedarray-be-recycled
             */
            if (!isTypedArrayRecycled) {
                isTypedArrayRecycled = true
                typedArray.recycle()
            }
        }
    }

    private fun validateConfigurations() {
        // Check type mode
        val isAnimatedByWord = typedArray.getBoolean(R.styleable.TypedKtView_isAnimatedByWord, false)
        if (!isAnimatedByWordSet && isAnimatedByWord) {
            typeWriterMode = TypeWriterMode.WORDS
        }

        // Check loop
        val isLooped = typedArray.getBoolean(R.styleable.TypedKtView_isLooped, false)
        if (!isLoopedSet) {
            this.isLooped = isLooped
        }

        // Check delay
        val delay = typedArray.getInt(R.styleable.TypedKtView_delay, 150)
        if (!isDelaySet) {
            this.delay = delay.toLong()
        }
    }

    private fun validateString(charSequence: CharSequence): CharSequence {
        if (!charSequence.trim().isEmpty()) {
            return charSequence
        }
        return text
    }

    fun removeAnimation() {
        isAnimating = false
        if (typeWriterMode == TypeWriterMode.CHARACTERS) {
            textHandler.removeCallbacks(characterAdder)
        } else {
            textHandler.removeCallbacks(wordAdder)
        }
    }

    fun isAnimating(): Boolean {
        return isAnimating
    }
}