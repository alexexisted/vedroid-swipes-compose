package alexa.dev.android_swipes

import alexa.dev.compose_swipeable_sdk.SwipeDirection
import alexa.dev.compose_swipeable_sdk.SwipeableCardsState
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import kotlinx.coroutines.launch

@Composable
fun <T> TopSwipeCard(
    item: T,
    state: SwipeableCardsState,
    onSwiped: (SwipeDirection) -> Unit,
    content: @Composable (T) -> Unit
) {
    val scope = rememberCoroutineScope()

    Box(
        modifier = Modifier
            .pointerInput(item) {
                detectDragGestures(
                    onDragEnd = {
                        scope.launch {
                            val dir = state.handleDragEnd()
                            if (dir != SwipeDirection.NONE) {
                                onSwiped(dir)
                            }
                        }
                    },
                    onDrag = { _, dragAmount ->
                        scope.launch {
                            state.offsetX.snapTo(state.offsetX.value + dragAmount.x)
                        }
                    }
                )
            }
            .graphicsLayer(
                translationX = state.offsetX.value,
                rotationZ = state.offsetX.value * 0.05f
            )
    ) {
        content(item)
    }
}
