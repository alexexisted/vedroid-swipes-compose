package alexa.dev.android_swipes

import alexa.dev.android_swipes.ui.theme.AndroidswipesTheme
import alexa.dev.compose_swipeable_sdk.SwipeCardDeck
import alexa.dev.compose_swipeable_sdk.rememberSwipeableCardsState
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AndroidswipesTheme {
                SwipeDemo()
            }
        }
    }
}


@Composable
fun SwipeDemo() {
    val items = remember {
        listOf(
            ProfileCard(
                "Alexa Diamant",
                "Global Elite",
                "Android dev & CS2 strategist",
                R.drawable.card_test_preview
            ),
            ProfileCard(
                "Mira Nova",
                "Legendary Eagle",
                "Design lover & FPS beast",
                R.drawable.card_test_preview2
            ),
            ProfileCard(
                "R2 D2",
                "Silver 5",
                "im noob wanna play better",
                R.drawable.card_test_preview3
            )
        )
    }
    val state = rememberSwipeableCardsState(
        itemCount = { items.size },
        onDeckEnd = {},
        looping = false
    )

    SwipeCardDeck(
        state = state,
        items = items,
        visibleCount = 1,
        modifier = Modifier,
        onSwiped = { dir, item -> println("Swiped $dir on ${item.name}") },
        cardContent = { item, offsetX ->
            Box(modifier = Modifier) {
                TestCard(item)
            }
        }
    )
}
//@Composable
//fun SwipeCardStackDemo() {

//
//    val context = LocalContext.current
//
//    val state = rememberSwipeableCardsState(
//        itemCount = { items.size },
//        looping = true,
//        onDeckEnd = {
//            Toast.makeText(context, "Deck finished", Toast.LENGTH_SHORT).show()
//        }
//    )
//
//    Column(
//        horizontalAlignment = Alignment.CenterHorizontally,
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(16.dp)
//    ) {
//        SwipeCardDeck(
//            state = state,
//            items = items,
//            onSwiped = { dir, item -> println("Swiped $dir on ${item.name}") },
//            cardContent = { item, offsetX ->
//                Box {
//                    TestCard(item)
//
////                    if (offsetX > 100f) {
////                        Text(
////                            "LIKE",
////                            color = Color.Green,
////                            fontSize = 24.sp,
////                            fontWeight = FontWeight.Bold,
////                            modifier = Modifier
////                                .align(Alignment.TopStart)
////                                .padding(16.dp)
////                                .alpha(min(offsetX / 300f, 1f))
////                        )
////                    } else if (offsetX < -100f) {
////                        Text(
////                            "NOPE",
////                            color = Color.Red,
////                            fontSize = 24.sp,
////                            fontWeight = FontWeight.Bold,
////                            modifier = Modifier
////                                .align(Alignment.TopEnd)
////                                .padding(16.dp)
////                                .alpha(min(-offsetX / 300f, 1f))
////                        )
////                    }
//                }
//            }
//        )
//    }
//}



