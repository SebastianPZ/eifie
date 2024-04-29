package com.mentalhealth.eifie.ui.common.animation

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.mentalhealth.eifie.R

@Composable
fun EAnimation(
    resource: Int,
    iterations: Int = LottieConstants.IterateForever,
    actionOnEnd: () -> Unit = {},
    @SuppressLint("ModifierParameter") animationModifier: Modifier = Modifier,
    @SuppressLint("ModifierParameter") backgroundModifier: Modifier = Modifier
        .wrapContentSize()
        .background(Color.Transparent)
) {
    val composition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(resource)
    )

    val progress by animateLottieCompositionAsState(
        composition,
        iterations = iterations,
        isPlaying = true
    )


    Box(
        modifier = backgroundModifier
    ) {
        LottieAnimation(
            enableMergePaths = true,
            composition = composition,
            progress = { progress },
            modifier = animationModifier
                .align(Alignment.Center)
        )
    }


    LaunchedEffect(key1 = progress) {
        if(progress == 1F) {
            actionOnEnd.invoke()
        }
    }

}


@Preview
@Composable
fun SimpleComposablePreview() {
    EAnimation(
        resource = R.raw.loading_animation
    )
}