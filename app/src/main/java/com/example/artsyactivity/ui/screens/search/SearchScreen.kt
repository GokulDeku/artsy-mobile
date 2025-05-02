@file:OptIn(ExperimentalSharedTransitionApi::class)

package com.example.artsyactivity.ui.screens.search

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.artsyactivity.ui.screens.search.components.SearchTopBar

@Composable
fun SharedTransitionScope.SearchScreen(
    modifier: Modifier = Modifier,
    animatedContentScope: AnimatedContentScope
) {
    with(this) {
        Scaffold(
            topBar = {
                SearchTopBar(
                    animatedContentScope = animatedContentScope
                )
            }
        ) { innerPadding ->
            Column(modifier = modifier.padding(innerPadding)) {

            }
        }
    }
}



@SuppressLint("UnusedContentLambdaTargetStateParameter")
@Preview
@Composable
private fun PreviewSearchScreen(modifier: Modifier = Modifier) {
    SharedTransitionLayout {
        AnimatedContent(true) {
            SearchScreen(
                animatedContentScope = this
            )
        }
    }
}