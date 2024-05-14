package com.mentalhealth.eifie.ui.psychologist.accesscode

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.mentalhealth.eifie.R
import com.mentalhealth.eifie.ui.theme.BlackGreen
import com.mentalhealth.eifie.ui.theme.CustomWhite
import com.mentalhealth.eifie.ui.theme.DarkGreen

@Composable
fun PsychologistCodeView(
    psychologist: Long,
    navController: NavHostController
) {

    val viewModel: PsychologistCodeViewModel = hiltViewModel<PsychologistCodeViewModel, PsychologistCodeViewModel.PsychologistCodeViewModelFactory>(
        creationCallback = { factory -> factory.create(psychologist = psychologist) })

    val accessCode by viewModel.accessCode.collectAsStateWithLifecycle()
    val time by viewModel.time.collectAsStateWithLifecycle()

    val instructionText = buildAnnotatedString {
        withStyle(style = SpanStyle(
            fontWeight = FontWeight.Normal
        )
        ) {
            append(stringResource(id = R.string.share_the))
        }
        withStyle(style = SpanStyle(
            color = DarkGreen,
            fontWeight = FontWeight.Bold
        )
        ) {
            append(stringResource(id = R.string.code))
        }
        withStyle(style = SpanStyle(
            fontWeight = FontWeight.Normal
        )
        ) {
            append(stringResource(id = R.string.with_your))
        }
        withStyle(style = SpanStyle(
            color = DarkGreen,
            fontWeight = FontWeight.Bold
        )
        ) {
            append(stringResource(id = R.string.patient).lowercase())
        }
        withStyle(style = SpanStyle(
            fontWeight = FontWeight.Normal
        )
        ) {
            append(stringResource(id = R.string.link_patient))
        }
    }

    Surface(
        color = CustomWhite,
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 30.dp, horizontal = 20.dp)
        ) {
            IconButton(
                onClick = { navController.popBackStack() },
                modifier = Modifier.align(Alignment.End)
            ) {
                Icon(
                    imageVector = Icons.Filled.Clear,
                    tint = BlackGreen,
                    contentDescription = "",
                    modifier = Modifier.size(25.dp)
                )
            }

            Text(
                modifier = Modifier.padding(top = 30.dp),
                text = instructionText,
                textAlign = TextAlign.Center
            )

            CountDownIndicator(
                Modifier.padding(top = 30.dp),
                progress = time.progress,
                time = time.timeLeft,
                code = accessCode,
                size = 300,
                stroke = 12
            )
        }
    }
}