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

![Demo](https://raw.githubusercontent.com/joshuadeguzman/TypedKt/master/app/src/main/res/drawable/demo.gif)

### Usage
```kotlin
override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    bt_animate.setOnClickListener {
        // Animate by characters
        tkv_character.setLooped(true)
        tkv_character.animateText()

        // Animate by word
        tkv_words.setAnimationByWord()
        tkv_words.setLooped(true)
        tkv_words.animateText()
        
        // Custom configurations
        tkv_character.setLooped(true) // default false
        tkv_character.setDelay(100) // default 150
        tkv_character.setAnimationByCharacter() // default animation
        tkv_character.setAnimationByWord() // default by character
        tkv_character.setAnimationByWord("regex pattern") // default delimiter is "\\s+" which denotes space
        tkv_character.setEndAnimationListener { // default null
            callSomeMethod()
        }
        
        // Setup configuration before calling animate method for it to take effect
        tkv_character.animateText()
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
