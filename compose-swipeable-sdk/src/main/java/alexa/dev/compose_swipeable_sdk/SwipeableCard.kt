package alexa.dev.compose_swipeable_sdk

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import kotlinx.coroutines.launch

@Composable
fun <T> SwipeableCard(
    card: T,
    modifier: Modifier = Modifier,
    onSwiped: (SwipeDirection, T) -> Unit,
    cardContent: @Composable (T) -> Unit
) {
    val offsetX = remember { Animatable(0f) }
    val swipeThreshold = 200f
    val scope = rememberCoroutineScope()

    val dragState = remember { mutableStateOf(Offset.Zero) }

    Box(
        modifier = modifier
            .pointerInput(card) {
                detectDragGestures(
                    onDragEnd = {
                        val finalOffset = offsetX.value
                        val direction = when {
                            finalOffset > swipeThreshold -> SwipeDirection.RIGHT
                            finalOffset < -swipeThreshold -> SwipeDirection.LEFT
                            else -> SwipeDirection.NONE
                        }

                        scope.launch {
                            if (direction == SwipeDirection.NONE) {
                                offsetX.animateTo(0f, spring())
                            } else {
                                val target = if (direction == SwipeDirection.RIGHT) 1000f else -1000f
                                offsetX.animateTo(target, tween(300))
                                onSwiped(direction, card)
                            }
                        }
                    },
                    onDrag = { _, dragAmount ->
                        scope.launch {
                            offsetX.snapTo(offsetX.value + dragAmount.x)
                        }
                    }
                )
            }
            .graphicsLayer(
                translationX = offsetX.value,
                rotationZ = offsetX.value * 0.05f
            )
    ) {
        cardContent(card)
    }
}
