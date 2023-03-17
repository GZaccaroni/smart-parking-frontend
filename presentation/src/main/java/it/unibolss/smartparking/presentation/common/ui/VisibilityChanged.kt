package it.unibolss.smartparking.presentation.common.ui

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.util.SparseBooleanArray
import android.view.View
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.currentCompositeKeyHash
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.layout.LayoutCoordinates
import androidx.compose.ui.layout.boundsInWindow
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.platform.LocalView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver

/**
 * We need to remember which composable have been seen to not re-fire on a configuration change.
 */
private val seenVisibleMap = SparseBooleanArray()
private val seenHiddenMap = SparseBooleanArray()

/**
 * A callback called when the composable visibility changes. It will fire only when completely visible
 * and when the app is resumed, and it will not fire again on rotation.
 *
 * @param onVisibilityChanged The callback to call when the visibility changes.
 */
internal fun Modifier.onVisibilityChanged(
    onVisibilityChanged: (visible: Boolean) -> Unit
): Modifier = onVisibilityChanged(0, onVisibilityChanged)

internal fun Modifier.onVisibilityChanged(
    extraKeys: Int,
    onVisibilityChanged: (visible: Boolean) -> Unit,
): Modifier {
    return composed {
        val view = LocalView.current
        val lifecycleState = LocalLifecycleOwner.current.lifecycle.collectState()
        val isResumed = lifecycleState.isAtLeast(Lifecycle.State.RESUMED)
        var isVisible: Boolean? by remember { mutableStateOf(null) }

        val hash = currentCompositeKeyHash + extraKeys

        var seenVisible by remember(hash) {
            // Check to see if the seen visible value was saved for a config change.
            mutableStateOf(seenVisibleMap[hash])
        }
        var seenHidden by remember(hash) {
            // Check to see if the seen hidden value was saved for a config change.
            mutableStateOf(seenHiddenMap[hash])
        }
        // Fire the event if we are resumed, visible, and it hasn't already been seen.
        DisposableEffect(isResumed, isVisible, seenVisible, seenHidden, onVisibilityChanged) {
            if ((isResumed && isVisible == true) && !seenVisible) {
                seenVisible = true
                // Remember the visible value to prevent re-sending on configuration changes.
                seenVisibleMap.put(hash, true)

                seenHidden = false
                seenHiddenMap.delete(hash)

                onVisibilityChanged(true)
            } else if ((!isResumed || isVisible == false) && !seenHidden) {
                seenHidden = true
                // Remember the hidden value to prevent re-sending on configuration changes.
                seenHiddenMap.put(hash, true)

                seenVisible = false
                seenVisibleMap.delete(hash)

                onVisibilityChanged(false)
            }
            onDispose { }
        }

        // Clean up the map values unless we are going through a configuration change.
        DisposableEffect(hash) {
            onDispose {
                val activity = view.context.findActivity()
                if (!activity.isChangingConfigurations) {
                    seenVisibleMap.delete(hash)
                    seenHiddenMap.delete(hash)
                }
            }
        }

        onGloballyPositioned { coordinates ->
            isVisible = coordinates.isCompletelyVisible(view)
        }
    }
}

private fun Context.findActivity(): Activity {
    return when (this) {
        is Activity -> this
        is ContextWrapper -> baseContext.findActivity()
        else -> error("unable to find Activity")
    }
}

@Composable
private fun Lifecycle.collectState(): Lifecycle.State {
    var state by remember { mutableStateOf(currentState) }
    DisposableEffect(this) {
        val listener = LifecycleEventObserver { _, _ ->
            state = currentState
        }
        addObserver(listener)
        onDispose {
            removeObserver(listener)
        }
    }
    return state
}

private fun LayoutCoordinates.isCompletelyVisible(view: View): Boolean {
    if (!isAttached) return false
    // Window relative bounds of our compose root view that are visible on the screen
    val globalRootRect = android.graphics.Rect()
    if (!view.getGlobalVisibleRect(globalRootRect)) {
        // we aren't visible at all.
        return false
    }
    val bounds = boundsInWindow()
    // Make sure we are completely in bounds.
    return bounds.top >= globalRootRect.top &&
        bounds.left >= globalRootRect.left &&
        bounds.right <= globalRootRect.right &&
        bounds.bottom <= globalRootRect.bottom
}
