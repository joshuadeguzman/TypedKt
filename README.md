# TypedKt
Animated TextView effects for Android

### Initial Release 
* Supports character and word animation
* Option for custom delay
* Option for custom string to best animated for a specific view
* Option for callback after the animation

### Additional configurations added
* Supports loop configuration
* Passing of custom delimiter using Regex pattern
* Added removal of callbacks
* Optimized method invocations

### Installation
```gradle
repositories {
    maven { url "https://jitpack.io" }
}

dependencies {
    implementation 'com.github.joshuadeguzman:typedkt:x.x.x'
}
```

![Demo](https://raw.githubusercontent.com/joshuadeguzman/TypedKt/b8d9f5fda3da91bff770b875e9bd98904315247b/app/src/main/res/drawable/demo.gif)

### Usage
```kotlin
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
```

### Custom configurations via code
```kotlin
tkv_character.setLooped(true) // default false
tkv_character.setDelay(100) // default 150
tkv_character.setAnimationByCharacter() // default animation
tkv_character.setAnimationByWord() // default by character
tkv_character.setAnimationByWord("regex pattern") // default delimiter is "\\s+" which denotes space
tkv_character.setEndAnimationListener { // default null
    callSomeMethod()
}
```

```xml
<io.jmdg.typedkt.TypedKtView
    ...
    app:animateOnLoad="true"
    app:delay="150"
    app:isAnimatedByWord="false"
    app:isLooped="true" />
```

###### Important: Remove callback
```kotlin
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
```
