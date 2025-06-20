package alexa.dev.compose_swipeable_sdk

import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.spring
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope

@Composable
fun rememberSwipeableCardsState(
    initialIndex: Int = 0,
    itemCount: () -> Int,
    animSpec: AnimationSpec<Float> = spring(),
    onDeckEnd: () -> Unit = {},
    looping: Boolean = false
): SwipeableCardsState {
    val scope = rememberCoroutineScope()
    return remember(initialIndex, looping) {
        SwipeableCardsState(
            initialIndex = initialIndex,
            itemCount = itemCount,
            animSpec = animSpec,
            scope = scope,
            onDeckEnd = onDeckEnd,
            looping = looping
        )
    }
}

