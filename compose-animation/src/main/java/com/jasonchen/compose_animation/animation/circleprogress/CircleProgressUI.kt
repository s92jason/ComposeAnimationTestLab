package com.jasonchen.workspace.study.composeanimationtestlab.ui.animation.circleprogress

import androidx.annotation.DrawableRes
import androidx.annotation.FloatRange
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationEndReason
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.graphics.ExperimentalAnimationGraphicsApi
import androidx.compose.animation.graphics.res.animatedVectorResource
import androidx.compose.animation.graphics.res.rememberAnimatedVectorPainter
import androidx.compose.animation.graphics.vector.AnimatedImageVector
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.progressSemantics
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
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jasonchen.compose_animation.animation.tick.AnimationTick

@OptIn(ExperimentalAnimationGraphicsApi::class)
@Composable
fun CircularProgress(
    progress: Float,
    text: String,
    @DrawableRes id: Int,
    modifier: Modifier = Modifier,
    progressBackgroundColor: Color = Color(231, 233, 235),
    progressIndicatorColor: Color = Color(117, 126, 236),
    fontSize: TextUnit = 14.sp,
    fontWeight: FontWeight = FontWeight(600),
    fontColor: Color = Color.Unspecified
) {
    AnimatedCircularProgress(
        progress = progress,
        modifier = modifier,
        progressBackgroundColor = progressBackgroundColor,
        progressIndicatorColor = progressIndicatorColor,
        contentView = {
            Text(text = text, fontSize = fontSize, color = fontColor, fontWeight = FontWeight(600))
        },
        completedView = {
            val image = AnimatedImageVector.animatedVectorResource(id)
            var atEnd by remember {
                mutableStateOf(false)
            }

            LaunchedEffect(key1 = atEnd, block = {
                atEnd = true
            })

            Image(
                painter = rememberAnimatedVectorPainter(animatedImageVector = image, atEnd = atEnd),
                contentDescription = "打勾",
                modifier = Modifier,
                contentScale = ContentScale.Crop
            )
        })
}

@Composable
fun CircularProgress(
    progress: Float,
    text: String,
    modifier: Modifier = Modifier,
    innerPadding: Dp = 10.dp,
    progressBackgroundColor: Color = Color(231, 233, 235),
    progressIndicatorColor: Color = Color(117, 126, 236),
    fontSize: TextUnit = 14.sp,
    fontWeight: FontWeight = FontWeight(600),
    fontColor: Color = Color.Unspecified,
    strokeWidth: Dp = 3.dp
) {
    AnimatedCircularProgress(
        progress = progress,
        modifier = modifier,
        innerPadding = innerPadding,
        progressBackgroundColor = progressBackgroundColor,
        progressIndicatorColor = progressIndicatorColor,
        innerStrokeWidth = strokeWidth,
        outStrokeWidth = strokeWidth,
        contentView = {
            Text(text = text, fontSize = fontSize, color = fontColor, fontWeight = fontWeight)
        },
        completedView = {
            AnimationTick(
                modifier = Modifier
                    .fillMaxSize(0.5f),
                color = progressIndicatorColor,
                strokeWidth = strokeWidth
            )
        })
}

@Composable
fun AnimatedCircularProgress(
    @FloatRange(from = 0.0, to = 1.0) progress: Float,
    modifier: Modifier = Modifier,
    innerPadding: Dp = 10.dp,
    progressBackgroundColor: Color = Color(231, 233, 235),
    progressIndicatorColor: Color = Color(117, 126, 236),
    innerStrokeWidth: Dp = 3.dp,
    outStrokeWidth: Dp = 3.dp,
    contentView: @Composable (innerPadding: Dp) -> Unit = {},
    completedView: @Composable (innerPadding: Dp) -> Unit = {}
) {
    var isCompleted by remember {
        mutableStateOf(false)
    }

    val animateFloat = remember { Animatable(0f) }
    LaunchedEffect(animateFloat) {
        animateFloat.animateTo(
            targetValue = progress,
            animationSpec = tween(durationMillis = 1200, easing = LinearOutSlowInEasing)
        ).takeIf { result -> result.endReason == AnimationEndReason.Finished }?.run {
            if (progress == 1.0f) {
                isCompleted = true
            }
        }
    }

    Box(modifier = modifier.padding(innerPadding), contentAlignment = Alignment.Center) {
        AnimatedVisibility(visible = !isCompleted) {
            contentView(innerPadding)
        }

        AnimatedVisibility(visible = isCompleted) {
            completedView(innerPadding)
        }
    }

    Canvas(
        modifier = modifier.progressSemantics(progress)
    ) {
        val startAngle = 270f
        val sweep: Float = animateFloat.value * 360f

        drawCircle(
            color = progressBackgroundColor,
            style = Stroke(width = innerStrokeWidth.toPx(), cap = StrokeCap.Round, join = StrokeJoin.Round),
            radius = size.minDimension / 2.0f - innerStrokeWidth.toPx() / 2
        )

        drawCircularProgressIndicator(
            startAngle,
            sweep,
            progressIndicatorColor,
            Stroke(width = outStrokeWidth.toPx(), cap = StrokeCap.Round, join = StrokeJoin.Round)
        )
    }
}

private fun DrawScope.drawCircularProgressIndicator(
    startAngle: Float,
    sweep: Float,
    color: Color,
    stroke: Stroke
) {
    val diameterOffset = stroke.width / 2
    val arcDimen = size.width - 2 * diameterOffset

    val topLeft = Offset(
        diameterOffset + (if (size.width < size.height) 0f else (size.width - size.height) / 2),
        diameterOffset + (if (size.height < size.width) 0f else (size.height - size.width) / 2)
    )
    drawArc(
        color = color,
        startAngle = startAngle,
        sweepAngle = sweep,
        useCenter = false,
        topLeft = topLeft,
        size = Size(arcDimen, arcDimen),
        style = stroke
    )
}

@Preview
@Composable
fun PreviewCircularProgress() {
    var targetSize = 3f
    var completedTargetSize = 3f

    var text = "${completedTargetSize.toInt()} / ${targetSize.toInt()}"

    Box(modifier = Modifier.padding(20.dp)) {
        CircularProgress(
            progress = completedTargetSize / targetSize,
            text = text,
            modifier = Modifier.size(100.dp),
            innerPadding = 10.dp,
            strokeWidth = 10.dp
        )
    }
}
