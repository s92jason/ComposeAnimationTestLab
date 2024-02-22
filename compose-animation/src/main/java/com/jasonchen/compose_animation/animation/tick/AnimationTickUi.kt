package com.jasonchen.compose_animation.animation.tick

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.TweenSpec
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp

@Composable
fun AnimationTick(
    modifier: Modifier = Modifier,
    color: Color = Color(117, 126, 236),
    strokeWidth: Dp = 3.dp,
    firstLineTween: TweenSpec<Float> = tween(durationMillis = 130, easing = FastOutSlowInEasing),
    secondLineTween: TweenSpec<Float> = tween(durationMillis = 120, easing = LinearOutSlowInEasing)
) {
    var viewSize by remember {
        mutableStateOf(IntSize.Zero)
    }

    Box(modifier = modifier.onGloballyPositioned { coordinates ->
        viewSize = coordinates.size
    }, contentAlignment = Alignment.Center) {

        if (viewSize.width != 0 && viewSize.height != 0) {
            val aspect = 13.5f / 20f
            val width: Float
            val height: Float
            if (viewSize.width * aspect < viewSize.height) {
                width = viewSize.width.toFloat()
                height = viewSize.width * aspect
            } else {
                width = viewSize.height / aspect
                height = viewSize.height.toFloat()
            }

            val offsetX = width / 2f
            val centerX = -offsetX + width * (8.5f / 20f)
            val bottomY = height * (7f / 13.5f)
            val coordinates = arrayOf(
                Offset(-offsetX, 0f),
                Offset(centerX, height * (7f / 13.5f)),
                Offset(centerX + width * (11.5f / 20f), bottomY - height)
            )
            val offsets = arrayOf(coordinates[1] - coordinates[0], coordinates[2] - coordinates[1])

            val animationTween1 = remember {
                Animatable(0f)
            }

            val animationTween2 = remember {
                Animatable(0f)
            }

            LaunchedEffect(key1 = animationTween1, key2 = animationTween2, block = {
                animationTween1.animateTo(
                    targetValue = 1f, animationSpec = firstLineTween
                )

                animationTween2.animateTo(
                    targetValue = 1f, animationSpec = secondLineTween
                )
            })

            Canvas(modifier = Modifier, onDraw = {
                val strokeWidthPx = strokeWidth.toPx()
                val path = Path().apply {

                    var offset = offsets[0] * animationTween1.value
                    var x = coordinates[0].x
                    var y = coordinates[0].y

                    moveTo(coordinates[0].x, coordinates[0].y)

                    x += offset.x
                    y += offset.y
                    lineTo(x, y)

                    if (animationTween2.value > 0) {
                        offset = offsets[1] * animationTween2.value

                        x += offset.x
                        y += offset.y
                        lineTo(x, y)
                    }

                    moveTo(coordinates[0].x, coordinates[0].y)
                    close()
                }

                drawPath(
                    path, color, style = Stroke(width = strokeWidthPx, join = StrokeJoin.Round)
                )
            })
        }
    }
}

@Preview
@Composable
private fun PreviewAnimationTick() {
    AnimationTick(
        modifier = Modifier.size(50.dp)
    )
}