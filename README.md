# TypedKt
Animated TextView effects for Android

### Initial Release 
* Supports character and word animation
* Option for custom delay
* Option for custom string to best animated for a specific view
* Option for callback after the animation


### Installation
```gradle
repositories {
    maven { url "https://jitpack.io" }
}

dependencies {
    implementation 'com.github.joshuadeguzman:typedkt:x.x.x'
}
```

![Demo](https://raw.githubusercontent.com/joshuadeguzman/TypedKt/master/app/src/main/res/drawable/demo.gif)

### Usage
```kotlin
 override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    bt_animate.setOnClickListener {
        // Animate by characters
        tkv_character.setLooped(true)

        // Custom configurations
        tkv_character.setLooped(true) // default false
        tkv_character.setDelay(100) // default 150
        tkv_character.setAnimationByCharacter() // default animation
        tkv_character.setAnimationByWord() // default by character
        tkv_character.setAnimationByWord(Regex("regex pattern")) // default delimiter is Regex("\\s+")
        tkv_character.setEndAnimationListener { // default null
            callSomeMethod()
        }
        
        // Setup options before calling this method for it to take effect
        tkv_character.animateText()

        // Animate by word
        tkv_words.setAnimationByWord()
        tkv_words.setLooped(true)

        // Setup options before calling this method for it to take effect
        tkv_words.animateText()
    }
}
```

###### Important: Remove callback
```kotlin
override fun onDestroy() {
    super.onDestroy()
    // Remove callbacks
    tkv_character.removeAnimation()
    tkv_words.removeAnimation()
}
```
