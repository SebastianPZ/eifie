package com.mentalhealth.eifie.ui.form.main

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.mentalhealth.eifie.R
import com.mentalhealth.eifie.ui.common.animation.EAnimation
import com.mentalhealth.eifie.ui.common.button.CommonButton
import com.mentalhealth.eifie.ui.common.layout.HeaderComponent
import com.mentalhealth.eifie.ui.theme.CustomWhite
import com.mentalhealth.eifie.ui.theme.White95
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SurveyView(
    navController: NavHostController?,
    viewModel: FormViewModel?
) {

    val formName = viewModel?.formName?.collectAsStateWithLifecycle()
    val formState = viewModel?.formState?.collectAsStateWithLifecycle()
    val formQuestions = viewModel?.formQuestions?.collectAsStateWithLifecycle()
    val pagerState = rememberPagerState( pageCount = { ( formQuestions?.value?.size ?: 0) + 1 }) // 1 added bc form preview

    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(formQuestions?.value) {
        if(!formQuestions?.value.isNullOrEmpty()) coroutineScope.launch {
            pagerState.scrollToPage(pagerState.currentPage + 1)
        }
    }

    Box {
        Image(
            modifier = Modifier.fillMaxWidth(),
            painter = painterResource(id = R.drawable.eifi_background),
            contentDescription = "",
            contentScale = ContentScale.Crop
        )
        Column(
            modifier = Modifier
                .fillMaxHeight(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            HeaderComponent(
                title = formName?.value ?: "",
                subtitle = stringResource(id = R.string.form),
                onBack = { navController?.popBackStack() },
                modifier = Modifier.padding(start = 20.dp, end = 20.dp, top = 50.dp),
            )
            Surface(
                color = CustomWhite,
                shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
                modifier = Modifier
                    .padding(top = 35.dp)
                    .fillMaxSize()
            ) {
                Column(
                    verticalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(vertical = 40.dp, horizontal = 25.dp)
                ) {
                    HorizontalPager(
                        state = pagerState,
                        userScrollEnabled = false
                    ) {question ->
                        when(question) {
                            0 -> Text(
                                text = "Page: $question",
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(100.dp)
                            )
                            else -> QuestionOptionsView(
                                options = formQuestions?.value!![question - 1].options,
                                onSelected = { viewModel.saveQuestionAnswer(question - 1) }
                            )
                        }
                    }

                    when(formState?.value) {
                        FormState.InProgress -> CommonButton(
                            text = stringResource(id = R.string.carry_on),
                            modifier = Modifier.align(Alignment.CenterHorizontally)) {
                            viewModel.goToNextQuestion(pagerState.currentPage - 1) {
                                coroutineScope.launch {
                                    pagerState.scrollToPage(pagerState.currentPage + 1)
                                }
                            }
                        }
                        FormState.LastQuestion -> CommonButton(
                            text = stringResource(id = R.string.send),
                            modifier = Modifier.align(Alignment.CenterHorizontally)) {
                            viewModel.goToNextQuestion(pagerState.currentPage - 1) {
                                navController?.popBackStack()
                            }
                        }
                        else -> CommonButton(
                            modifier = Modifier.align(Alignment.CenterHorizontally)) {
                            viewModel?.initFormQuestions()
                        }
                    }
                }
                when(formState?.value) {
                    FormState.Loading -> EAnimation(
                        resource = R.raw.loading_animation,
                        animationModifier = Modifier
                            .size(150.dp),
                        backgroundModifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight()
                            .background(color = White95)
                    )
                    else -> Unit
                }
            }
        }
    }
}

@Composable
@Preview
fun SurveyPreview() {
    SurveyView(null, null)
}