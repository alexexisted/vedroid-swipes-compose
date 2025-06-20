package alexa.dev.compose_swipeable_sdk

import alexa.dev.android_swipes.TopSwipeCard
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex

@Composable
fun <T> SwipeCardDeck(
    state: SwipeableCardsState,
    items: List<T>,
    visibleCount: Int = 3,
    modifier: Modifier = Modifier,
    onSwiped: (SwipeDirection, T) -> Unit,
    cardContent: @Composable (T, Float) -> Unit
) {
    val activeCards = items.drop(state.currentIndex).take(visibleCount)

    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        activeCards.reversed().forEachIndexed { index, item ->
            val isTop = index == activeCards.lastIndex
            val scale = 1f - (0.05f * index)
            val verticalOffset = 8.dp * index

            Box(
                modifier = Modifier
                    .graphicsLayer(
                        scaleX = scale,
                        scaleY = scale,
                        translationY = verticalOffset.toPx()
                    )
                    .zIndex(index.toFloat())
            ) {
                if (isTop) {
                    TopSwipeCard(
                        item = item,
                        state = state,
                        onSwiped = { direction -> onSwiped(direction, item) },
                        content = { dataItem -> cardContent(dataItem, state.offsetX.value) }
                    )
                } else {
                    cardContent(item, 0f)
                }
            }
        }
    }
}


@Composable
fun Dp.toPx(): Float {
    return with(LocalDensity.current) { this@toPx.toPx() }
}

