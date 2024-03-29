package com.jasonchen.workspace.study.composeanimationtestlab.ui.complete

import androidx.annotation.DrawableRes
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jasonchen.workspace.study.composeanimationtestlab.R

@Composable
fun CompleteAnimation() {
    LazyColumn {
        item {
            AnimatedBeatCircle(
                backgroundRes = R.drawable.img_cube_achieve,
                contentDescription = "",
                modifier = Modifier.size(100.dp)
            )
        }

        items(1) {
            CubeBeatCircle(
                foregroundRes = R.drawable.img_cube_achieve_foreground,
                foregroundContentDesc = "",
                backgroundRes = R.drawable.img_cube_achieve_background,
                backgroundContentDesc = "",
                modifier = Modifier.size(100.dp)
            )
        }
    }
}

@Composable
fun CubeBeatCircle(
    @DrawableRes foregroundRes: Int,
    foregroundContentDesc: String?,
    @DrawableRes backgroundRes: Int,
    backgroundContentDesc: String?,
    modifier: Modifier
) {
    AnimatedBeatCircle(
        backgroundRes = backgroundRes,
        contentDescription = foregroundContentDesc,
        modifier = modifier,
        onAnimationFinished = null
    ) {
        var state by rememberSaveable {
            mutableStateOf(false)
        }

        val animatedSize by animateFloatAsState(
            targetValue = if (state) 1f else 0f,
            label = " cube beat circle text alpha",
            animationSpec = tween(durationMillis = 1000)
        )

        LaunchedEffect(key1 = state) {
            state = true
        }

        Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Icon(
                painter = painterResource(id = foregroundRes),
                contentDescription = backgroundContentDesc,
                modifier = Modifier
                    .fillMaxSize()
                    .alpha(animatedSize)
            )
        }
    }
}

@Composable
fun AnimatedBeatCircle(
    @DrawableRes backgroundRes: Int,
    contentDescription: String?,
    modifier: Modifier,
    onAnimationFinished: ((Boolean) -> Unit)? = null,
    contentView: @Composable () -> Unit = {}
) {
    var state by rememberSaveable {
        mutableStateOf(false)
    }
    val animatedSize by animateFloatAsState(targetValue = if (state) 0.75f else 0f,
        label = "beat circle state",
        animationSpec = spring(
            dampingRatio = 0.3f, stiffness = Spring.StiffnessLow
        ),
        finishedListener = {
            onAnimationFinished?.invoke(true)
        })

    LaunchedEffect(key1 = animatedSize) {
        state = true
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectTapGestures(onTap = { state = !state })
            }, contentAlignment = Alignment.Center
    ) {
        Box(modifier = Modifier.fillMaxSize(animatedSize)) {
            contentView()
        }
        Icon(
            painter = painterResource(id = backgroundRes),
            contentDescription = contentDescription,
            modifier = Modifier.fillMaxSize(animatedSize)
        )
    }
}

@Preview
@Composable
private fun PreviewCompleteAnimation() {
    CompleteAnimation()
}