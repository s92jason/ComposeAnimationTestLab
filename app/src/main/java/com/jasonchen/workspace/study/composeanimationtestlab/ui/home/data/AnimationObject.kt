package com.jasonchen.workspace.study.composeanimationtestlab.ui.home.data

import androidx.annotation.DrawableRes

data class AnimationObject(val type: AnimationType, val name: String, @DrawableRes val iconRes: Int = 0)