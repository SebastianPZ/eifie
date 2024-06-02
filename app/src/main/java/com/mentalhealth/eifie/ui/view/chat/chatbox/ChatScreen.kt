package com.mentalhealth.eifie.ui.view.chat.chatbox

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.navigation.NavHostController
import com.mentalhealth.eifie.ui.view.chat.MessageBubble
import com.mentalhealth.eifie.ui.theme.CustomWhite
import com.mentalhealth.eifie.ui.viewmodel.ChatViewModel

@Composable
fun ChatScreen(
    navController: NavHostController?,
    viewModel: ChatViewModel?
) {
    val messages = viewModel?.messages?.collectAsState()
    val listState = rememberLazyListState()
    ConstraintLayout(
        modifier = Modifier
            .background(color = CustomWhite)
            .fillMaxSize()
    ) {
        val (chatHeader, chatBox, chatInput) = createRefs()

        LaunchedEffect(messages?.value?.size) {
            listState.animateScrollToItem(messages?.value?.size ?: 0)
        }

        ChatHeader(
            onBack = { navController?.popBackStack() },
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp)
                .constrainAs(chatHeader) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
            }
        )
        LazyColumn(
            state = listState,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
                .constrainAs(chatBox) {
                    top.linkTo(chatHeader.bottom)
                    bottom.linkTo(chatInput.top, 5.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    height = Dimension.fillToConstraints
                },
        ) {
            items(messages?.value ?: listOf()) { message ->
                MessageBubble(
                    message = message
                )
            }
        }
        ChatInput(
            send = { viewModel?.sendMessage(it) },
            modifier = Modifier
                .fillMaxWidth()
                .constrainAs(chatInput) {
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        )
    }
}

@Preview
@Composable
fun ChatScreenPreview() {
    ChatScreen(null, null)
}