package com.jasonchen.workspace.study.composeanimationtestlab.ui.animation.circleprogress

import androidx.annotation.FloatRange
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CirCleProgressView(
    @FloatRange(from = 0.0, to = 1.0) progress: Float,
    text: String,
    modifier: Modifier = Modifier,
    color: Color = ProgressIndicatorDefaults.circularColor,
    strokeWidth: Dp = ProgressIndicatorDefaults.CircularStrokeWidth,
    fontSize: Int = 14
) {
    var rememberProgress by remember {
        mutableStateOf(0f)
    }



    Box(modifier = modifier) {
        CircleAnimationProgress(progress = rememberProgress)
//        CircularProgressIndicator(progress = rememberProgress, color = color, strokeWidth = strokeWidth)

        Text(text = text, modifier = Modifier.align(Alignment.Center), fontSize = fontSize.sp)
    }

    LaunchedEffect(true) {
        rememberProgress = progress
//        while (rememberProgress < progress) {
//            delay(10)
//            rememberProgress += 0.02f
//        }
    }
}

@Composable
fun CircleAnimationProgress(progress: Float, modifier: Modifier = Modifier, ) {
//    val progressState = remember {
//        mutableStateOf(0f)
//    }

    val animAngle = animateFloatAsState(targetValue = progress * 360, animationSpec = tween(1000))

    Canvas(modifier = modifier.size(300.dp), onDraw = {
        val innerStrokeWidth = 10.dp.toPx()
        val radius = 120.dp.toPx()
        val outStrokeWidth = 17.dp.toPx()
        val canvasWidth = size.width
        val canvasHeight = size.height
        drawCircle(
            Color(222, 228, 246),
            radius = radius,
            center = Offset(canvasWidth / 2, canvasHeight / 2),
            style = Stroke(innerStrokeWidth)
        )

        drawArc(
            Color(46, 120, 249),
            startAngle = -90f,
            sweepAngle = animAngle.value,
            useCenter = false,
            size = Size(radius * 2, radius * 2),
            style = Stroke(outStrokeWidth, cap = StrokeCap.Round),
            topLeft = Offset(center.x - radius, center.y - radius)
        )
    })
}

@Preview(showBackground = true)
@Composable
fun PreviewCirCleProgressView() {
    CirCleProgressView(progress = 0.9f, text = "1/3", color = Color(117, 126, 236), strokeWidth = 3.dp)
}

@Preview
@Composable
fun PreviewCircleAnimationProgress() {
    CircleAnimationProgress(0.2f)
}