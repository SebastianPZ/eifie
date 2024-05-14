package com.mentalhealth.eifie.ui.form.main

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.mentalhealth.eifie.R
import com.mentalhealth.eifie.ui.theme.White95
import com.mentalhealth.eifie.ui.theme.cutiveFontFamily
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun FormView(
    id: Int,
    navController: NavHostController
) {

    val viewModel: FormViewModel = hiltViewModel<FormViewModel, FormViewModel.FormViewModelFactory>(
        creationCallback = { factory -> factory.create(id = id) })

    val formName by viewModel.formName.collectAsStateWithLifecycle()
    val formState by viewModel.formState.collectAsStateWithLifecycle()
    val formQuestions by viewModel.formQuestions.collectAsStateWithLifecycle()
    val pagerState = rememberPagerState( pageCount = { formQuestions.size + 1 }) // 1 added bc form preview

    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(formQuestions) {
        if(formQuestions.isNotEmpty()) coroutineScope.launch {
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
            FormHeader(formName)
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = White95,
                ),
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
                                options = formQuestions[question - 1].options,
                                onSelected = { viewModel.saveQuestionAnswer(question - 1) }
                            )
                        }
                    }

                    when(formState) {
                        FormState.Idle -> Button(
                            onClick = { viewModel.initFormQuestions() },
                            modifier = Modifier.align(Alignment.CenterHorizontally)) {
                            Text(
                                stringResource(id = R.string.start)
                            )
                        }
                        FormState.InProgress -> Button(
                            onClick = { viewModel.goToNextQuestion(pagerState.currentPage - 1) {
                                coroutineScope.launch {
                                    pagerState.scrollToPage(pagerState.currentPage + 1)
                                }
                            }},
                            modifier = Modifier.align(Alignment.CenterHorizontally)) {
                            Text(
                                stringResource(id = R.string.carry_on)
                            )
                        }
                        FormState.LastQuestion -> Button(
                            onClick = { viewModel.goToNextQuestion(pagerState.currentPage - 1) {
                                navController.popBackStack()
                            }},
                            modifier = Modifier.align(Alignment.CenterHorizontally)) {
                            Text(
                                stringResource(id = R.string.send)
                            )
                        }
                        else -> Unit
                    }
                }
            }
        }
    }
}

/** name is referred to form name or question text**/
@Composable
fun FormHeader(name: String) {
    Text(
        text = stringResource(id = R.string.form),
        modifier = Modifier.padding(start = 24.dp, top = 50.dp),
        fontSize = 12.sp,
        fontFamily = cutiveFontFamily
    )
    Text(
        text = name,
        modifier = Modifier.padding(start = 24.dp, top = 3.dp),
        fontSize = 24.sp,
        fontWeight = FontWeight.Bold
    )
}

@Composable
@Preview
fun FormPreview() {
    FormView(
        id = 0,
        rememberNavController()
    )
}