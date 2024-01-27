package com.jasonchen.workspace.study.composeanimationtestlab.ui.home.data

enum class AnimationType {
    CIRCLE_PROGRESS, COMPLETE
}

val animations = listOf(
    AnimationObject(AnimationType.CIRCLE_PROGRESS, "圓形進度條"),
    AnimationObject(AnimationType.COMPLETE, "達成")
)