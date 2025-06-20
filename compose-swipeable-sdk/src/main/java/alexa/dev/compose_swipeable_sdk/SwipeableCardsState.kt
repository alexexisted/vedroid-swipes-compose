package alexa.dev.compose_swipeable_sdk

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class SwipeableCardsState(
    initialIndex: Int = 0,
    val itemCount: () -> Int,
    private val animSpec: AnimationSpec<Float> = spring(),
    private val swipeThreshold: Float = 200f,
    internal val scope: CoroutineScope,
    val onDeckEnd: () -> Unit = {},
    val looping: Boolean = false
) {
    var currentIndex by mutableStateOf(initialIndex)
        private set

    val offsetX = Animatable(0f)

    fun canSwipeBack() = currentIndex > 0

    suspend fun swipe(direction: SwipeDirection) {
        val target = if (direction == SwipeDirection.RIGHT) 1000f else -1000f
        offsetX.animateTo(target, tween(300))
        offsetX.snapTo(0f)

        advanceIndex()
    }

    suspend fun handleDragEnd(): SwipeDirection {
        val value = offsetX.value
        val direction = when {
            value > swipeThreshold -> SwipeDirection.RIGHT
            value < -swipeThreshold -> SwipeDirection.LEFT
            else -> SwipeDirection.NONE
        }

        when (direction) {
            SwipeDirection.NONE -> offsetX.animateTo(0f, animSpec)
            else -> {
                val target = if (direction == SwipeDirection.RIGHT) 1000f else -1000f
                offsetX.animateTo(target, tween(300))
                offsetX.snapTo(0f)
                advanceIndex()
            }
        }

        return direction
    }

    private fun advanceIndex() {
        currentIndex = when {
            currentIndex + 1 >= itemCount() && looping -> 0
            currentIndex + 1 >= itemCount() -> {
                onDeckEnd()
                itemCount() //jump out of bounds to avoid extra rendering
            }
            else -> currentIndex + 1
        }
    }

    fun goBack() {
        if (!canSwipeBack()) return
        scope.launch {
            currentIndex--
            offsetX.snapTo(0f)
        }
    }
}


