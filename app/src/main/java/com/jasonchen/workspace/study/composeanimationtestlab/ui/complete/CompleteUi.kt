package com.jasonchen.workspace.study.composeanimationtestlab.ui.complete

import android.util.Log
import androidx.annotation.DrawableRes
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jasonchen.workspace.study.composeanimationtestlab.R

@Composable
fun CompleteAnimation() {
    CubeBeatCircle(
        foregroundRes = R.drawable.img_cube_achieve_foreground,
        backgroundRes = R.drawable.img_cube_achieve_background,
        modifier = Modifier.size(100.dp)
    )
}

@Composable
fun CubeBeatCircle(@DrawableRes foregroundRes: Int, @DrawableRes backgroundRes: Int, modifier: Modifier) {
    var state by remember {
        mutableStateOf(false)
    }
    AnimatedBeatCircle(backgroundRes = backgroundRes, modifier = modifier, onAnimationFinished = { state = true }) {
        val animatedSize by animateFloatAsState(
            targetValue = if (state) 1f else 0f, label = "", animationSpec = tween(durationMillis = 1000)
        )

        Icon(
            painter = painterResource(id = foregroundRes), contentDescription = "", modifier = Modifier.alpha(animatedSize)
        )
    }
}

@Composable
fun AnimatedBeatCircle(
    @DrawableRes backgroundRes: Int, modifier: Modifier, onAnimationFinished: ((Boolean) -> Unit)? = null, contentView: @Composable () -> Unit = {}
) {
    var state by remember {
        mutableStateOf(false)
    }
    val animatedSize by animateFloatAsState(targetValue = if (state) 0.75f else 0f, label = "", animationSpec = spring(
        dampingRatio = 0.3f, stiffness = Spring.StiffnessLow
    ), finishedListener = {
        onAnimationFinished?.invoke(true)
        Log.e("TAG", "AnimatedBeatCircle: ")
    })

    LaunchedEffect(key1 = animatedSize) {
        state = true
    }

    Box(
        modifier = modifier.pointerInput(Unit) {
            detectTapGestures(onTap = { state = !state })
        }, contentAlignment = Alignment.Center
    ) {
        Surface(modifier = Modifier.fillMaxSize(animatedSize * 0.5f), color = Color.Transparent) {
            contentView()
        }
        Icon(
            painter = painterResource(id = backgroundRes), contentDescription = "", modifier = Modifier.fillMaxSize(animatedSize)
        )
    }
}

@Preview
@Composable
private fun PreviewCompleteAnimation() {
    CompleteAnimation()
}