package alexa.dev.example

import alexa.dev.compose_swipeable_sdk.SwipeCardDeck
import alexa.dev.compose_swipeable_sdk.rememberSwipeableCardsState
import alexa.dev.example.ui.theme.AndroidswipesTheme
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.dp
import kotlin.math.min


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
                "Alexa",
                "Global Elite",
                "Android dev & CS2 strategist",
                R.drawable.card_test_preview3
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
        initialIndex = 0,
        itemCount = { items.size },
        onDeckEnd = {},
        looping = false
    )

    SwipeCardDeck(
        state = state,
        items = items,
        visibleCount = 5,
        modifier = Modifier,
        onSwiped = { dir, item -> println("Swiped $dir on ${item.name}") },
        cardContent = { item, offsetX ->
            Box(modifier = Modifier) {
                TestCard(item)

                if (offsetX > 100f) {
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
    )
}



