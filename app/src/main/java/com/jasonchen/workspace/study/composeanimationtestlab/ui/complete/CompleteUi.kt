package com.jasonchen.workspace.study.composeanimationtestlab.ui.complete

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jasonchen.workspace.study.composeanimationtestlab.R

@Composable
fun CompleteAnimation() {
    AnimatedBeatCircle(
        backgroundRes = R.drawable.img_cube_achieve, modifier = Modifier.size(100.dp)
    )
}

@Composable
fun AnimatedBeatCircle(
    backgroundRes: Int, modifier: Modifier, contentView: @Composable () -> Unit = {}
) {
    var state by remember {
        mutableStateOf(false)
    }
    val animatedSize by animateFloatAsState(
        targetValue = if (state) 0.75f else 0f, label = "", animationSpec = spring(
            dampingRatio = 0.3f, stiffness = Spring.StiffnessLow
        )
    )

    LaunchedEffect(key1 = animatedSize) {
        state = true
    }

    Box(
        modifier = modifier.pointerInput(Unit) {
            detectTapGestures(onTap = { state = !state })
        }, contentAlignment = Alignment.Center
    ) {
        Icon(
            painter = painterResource(id = backgroundRes),
            contentDescription = "",
            modifier = Modifier.fillMaxSize(animatedSize)
        )


        contentView()
    }
}

@Preview
@Composable
private fun PreviewCompleteAnimation() {
    CompleteAnimation()
}