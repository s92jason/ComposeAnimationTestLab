package com.jasonchen.workspace.study.composeanimationtestlab.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jasonchen.workspace.study.composeanimationtestlab.R
import com.jasonchen.workspace.study.composeanimationtestlab.ui.home.data.AnimationObject
import com.jasonchen.workspace.study.composeanimationtestlab.ui.home.data.AnimationType
import com.jasonchen.workspace.study.composeanimationtestlab.ui.home.data.animations
import com.jasonchen.workspace.study.composeanimationtestlab.ui.theme.ComposeAnimattionTestLabTheme

@Composable
fun HomeUi(items: List<AnimationObject>, onItemClock: (AnimationType) -> Unit = {}, modifier: Modifier = Modifier) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        contentPadding = PaddingValues(10.dp)
    ) {
        items(items) { type ->
            HomeAnimationItem(type, modifier = modifier) {type ->
                onItemClock.invoke(type)
            }
        }
    }
}

@Composable
fun HomeAnimationItem(type: AnimationObject, modifier: Modifier, onItemClock: ((AnimationType) -> Unit)? = null) {
    Column(
        modifier = Modifier
            .background(color = MaterialTheme.colorScheme.secondary)
            .clickable { onItemClock?.invoke(type.type) },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = modifier.height(20.dp))
        Image(
            painter = painterResource(id = R.drawable.baseline_view_quilt_24),
            contentDescription = "",
            alignment = Alignment.TopCenter
        )
        Spacer(modifier = modifier.height(20.dp))
        Text(text = type.name, modifier = Modifier, textAlign = TextAlign.Center)
        Spacer(modifier = modifier.height(20.dp))
    }
}

@Preview
@Composable
fun PreviewHomeUi() {
    ComposeAnimattionTestLabTheme {
        HomeUi(animations, modifier = Modifier.fillMaxSize())
    }
}