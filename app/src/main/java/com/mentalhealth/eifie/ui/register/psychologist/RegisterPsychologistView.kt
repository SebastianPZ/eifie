package com.mentalhealth.eifie.ui.register.psychologist

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.mentalhealth.eifie.domain.entities.models.Psychologist
import com.mentalhealth.eifie.ui.common.layout.HeaderComponent
import com.mentalhealth.eifie.ui.register.Step
import com.mentalhealth.eifie.ui.theme.CustomWhite
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun RegisterPsychologistView(
    patient: Long,
    navController: NavHostController
) {

    val viewModel: RegisterPsychologistViewModel = hiltViewModel<RegisterPsychologistViewModel,
            RegisterPsychologistViewModel.RegisterPsychologistViewModelFactory>(
        creationCallback = { factory -> factory.create(patientId = patient) })

    val state by viewModel.state.collectAsStateWithLifecycle()
    val step by viewModel.actualStep.collectAsStateWithLifecycle()
    val error by viewModel.codeError.collectAsStateWithLifecycle()

    val coroutineScope = rememberCoroutineScope()
    val pagerState = rememberPagerState( pageCount = { viewModel.stepsSize })

    Surface(
        color = CustomWhite,
        modifier = Modifier.fillMaxSize()
    ) {
        Column {
            HeaderComponent(
                title = step.title,
                onBack = { navController.popBackStack() },
                modifier = Modifier.padding(vertical = 42.dp, horizontal = 24.dp)
            )

            HorizontalPager(
                state = pagerState,
                userScrollEnabled = false
            ) {actualStep ->
                when(actualStep) {
                    Step.FIRST -> PsychologistCodeComponent(
                        state = state,
                        errorMessage = error,
                        validateCode = { viewModel.validateCode(it) {
                            coroutineScope.launch {
                                viewModel.updateStep(Step.SECOND)
                                pagerState.animateScrollToPage(Step.SECOND)
                            }
                        } }
                    )
                    Step.SECOND -> PsychologistValidationComponent(
                        psychologist = Psychologist(),
                        onSuccess = {}
                    )
                }
            }
        }
    }
}