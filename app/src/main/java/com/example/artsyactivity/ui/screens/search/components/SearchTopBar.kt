@file:OptIn(ExperimentalSharedTransitionApi::class)

package com.example.artsyactivity.ui.screens.search.components

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.sp
import com.example.artsyactivity.ui.screens.search.SearchScreenViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SharedTransitionScope.SearchTopBar(
    modifier: Modifier = Modifier,
    keyword: String,
    uiAction: (SearchScreenViewModel.UiAction) -> Unit,
    animatedContentScope: AnimatedContentScope
) {

    var textFieldValueState by remember(keyword) {
        mutableStateOf(
            TextFieldValue(
                text = keyword,
                selection = TextRange(keyword.length)
            )
        )
    }

    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }

    TopAppBar(
        modifier = modifier,
        title = {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    modifier = Modifier
                        .sharedElement(
                            rememberSharedContentState(key = "search-icon-button"),
                            animatedVisibilityScope = animatedContentScope
                        ),
                    onClick = {}
                ) {
                    Icon(
                        imageVector = Icons.Filled.Search,
                        contentDescription = "Search"
                    )
                }

                TextField(
                    modifier = Modifier
                        .focusRequester(focusRequester),
                    value = textFieldValueState,
                    onValueChange = {
                        textFieldValueState = it
                        uiAction(SearchScreenViewModel.UiAction.OnKeyWordChange(it.text))
                    },
                    textStyle = TextStyle(
                        fontSize = 18.sp
                    ),
                    placeholder = {
                        Text("Search artists..")
                    },
                    colors = TextFieldDefaults.colors(
                        unfocusedContainerColor = MaterialTheme.colorScheme.primaryContainer,
                        focusedContainerColor = MaterialTheme.colorScheme.primaryContainer,
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        cursorColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    ),
                )
            }

        },

        actions = {
            IconButton(
                onClick = {
                    uiAction(SearchScreenViewModel.UiAction.ClearKeyWord)
                }
            ) {
                Icon(
                    imageVector = Icons.Filled.Close,
                    contentDescription = "Close"
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        )
    )
}