# Compose Swipeable SDK

A lightweight, customizable, swipeable card stack for Jetpack Compose.

---

## Installation

1. In your **`settings.gradle.kts`** (project-level):

```kotlin
dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
        maven { url = uri("https://jitpack.io") } // Add this
    }
}
```
2. In your **`build.gradle.kts`** (module-level):
   
```kotlin
dependencies {
	        implementation("com.github.alexexisted:vedroid-swipes-compose:v1.1.0")
	}
```

# Usage

```kotlin
@Composable
fun SwipeDemo() {
    val items = remember {
        listOf(ProfileCard) // <----- your custom list of items to be shown
}
    val state = rememberSwipeableCardsState(  // <----- create state for cards
        initialIndex = 0,                    // <----- optional, use when you need set start index of a card
        itemCount = { items.size },       // <----- size of your items
        onDeckEnd = { },                  // <----- callback, called when your stack is empty
        looping = false      // <----- if true, then your stack will be repeated
    )

    SwipeCardDeck(          // <----- create card stack
        state = state,
        items = items,
        visibleCount = 5,      // <----- you can set amount of cards that will be visible
        modifier = Modifier,    // <----- add modifier
        onSwiped = { dir, item -> println("Swiped $dir on ${item.name}") },      // <----- you can do your custom logic with every swipe
        cardContent = { item, offsetX ->              // <----- content of each card
            Box(modifier = Modifier) {
                TestCard(item)
            }
        }
    )
}
```
### To create overlayed items:

```kotlin
cardContent = { item, offsetX ->            // <----- working with current item and it's offset
            Box(modifier = Modifier) {
                TestCard(item)              // <----- Custom card

                if (offsetX > 100f) {       // <----- According to offset, we create text on a corners of a card
                    Text(  
                        "RIGHT SWIPE",
                        color = Color.Green,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .align(Alignment.TopStart)
                            .padding(16.dp)
                            .alpha(min(offsetX / 300f, 1f))
                    )
                } else if (offsetX < -100f) {
                    Text(
                        "LEFT SWIPE",
                        color = Color.Red,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .padding(16.dp)
                            .alpha(min(-offsetX / 300f, 1f))
                    )
                }
            }
        }
```


