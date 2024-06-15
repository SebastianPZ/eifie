package com.mentalhealth.eifie.ui.view.support

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.PersonOutline
import androidx.compose.material.icons.outlined.Backup
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.mentalhealth.eifie.R
import com.mentalhealth.eifie.ui.common.ViewState
import com.mentalhealth.eifie.ui.common.animation.EAnimation
import com.mentalhealth.eifie.ui.common.photo.UserPhotoView
import com.mentalhealth.eifie.ui.common.textfield.EIcon
import com.mentalhealth.eifie.ui.common.textfield.ETextField
import com.mentalhealth.eifie.ui.common.textfield.TextFieldType
import com.mentalhealth.eifie.ui.common.textfield.TextFieldValues
import com.mentalhealth.eifie.ui.navigation.Router
import com.mentalhealth.eifie.ui.theme.BlackGreen
import com.mentalhealth.eifie.ui.theme.CustomGray
import com.mentalhealth.eifie.ui.theme.CustomWhite
import com.mentalhealth.eifie.ui.theme.DarkGreen
import com.mentalhealth.eifie.ui.theme.LightGray
import com.mentalhealth.eifie.ui.theme.Purple
import com.mentalhealth.eifie.ui.viewmodel.SupportSettingsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SupportSettingsView(
    navController: NavHostController?,
    viewModel: SupportSettingsViewModel?
) {

    val state = viewModel?.state?.collectAsStateWithLifecycle()
    val supportPhoto = viewModel?.supportPhoto?.collectAsStateWithLifecycle()
    val supportName = viewModel?.supportName?.collectAsStateWithLifecycle()

    val sheetState = rememberModalBottomSheetState()
    var showBottomSheet by remember { mutableStateOf(false) }

    var name = ""

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = CustomWhite)
    ) {
        IconButton(
            onClick = {
                navController?.run {
                    previousBackStackEntry?.savedStateHandle?.set("supporter", viewModel?.supporter)
                    navController.popBackStack()
                }
            },
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(start = 10.dp, top = 15.dp)
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "",
                modifier = Modifier.size(30.dp)
            )
        }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth()
                .padding(top = 15.dp, bottom = 25.dp)
        ) {
            Spacer(modifier = Modifier.height(40.dp))
            Box(
                modifier = Modifier
                    .size(125.dp)
            ) {
                UserPhotoView(
                    uri = supportPhoto?.value?.photoUri,
                    username = supportPhoto?.value?.username,
                    modifier = Modifier.fillMaxSize()
                )
                IconButton(
                    onClick = {
                        navController?.run {
                            currentBackStackEntry?.run {
                                savedStateHandle.set(
                                    key = "supporter",
                                    value = viewModel?.supporter
                                )
                                navigate(Router.SUPPORT_EDIT_PHOTO.route)
                            }
                        }
                    },
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                ) {
                    Icon(
                        imageVector = Icons.Filled.CameraAlt,
                        tint = Color.White,
                        contentDescription = "",
                        modifier = Modifier
                            .size(25.dp)
                            .drawBehind {
                                drawCircle(
                                    color = Purple,
                                    radius = this.size.maxDimension
                                )
                            },
                    )
                }
            }
            Spacer(modifier = Modifier.height(30.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(horizontal = 37.dp, vertical = 25.dp)
                    .clickable { showBottomSheet = true }
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start,
                    modifier = Modifier
                        .wrapContentHeight()
                )  {
                    Icon(
                        imageVector = Icons.Filled.PersonOutline,
                        tint = CustomGray,
                        contentDescription = "",
                        modifier = Modifier
                            .size(20.dp)
                    )
                    Column {
                        Text(
                            color = LightGray,
                            fontSize = 12.sp,
                            text = stringResource(id = R.string.name_support),
                            modifier = Modifier.padding(start = 25.dp)
                        )
                        Text(
                            color = CustomGray,
                            fontSize = 14.sp,
                            text = supportName?.value ?: "Eifi",
                            modifier = Modifier.padding(start = 25.dp)
                        )
                    }
                }
                Icon(
                    imageVector = Icons.Outlined.Edit,
                    tint = DarkGreen,
                    contentDescription = "",
                    modifier = Modifier
                        .size(20.dp)
                )
            }
            HorizontalDivider(
                thickness = 0.8.dp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 24.dp, start = 40.dp, end = 40.dp)
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start,
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(horizontal = 37.dp)
                    .clickable {
                        viewModel?.backUpChats()
                    }
            ) {
                Icon(
                    imageVector = Icons.Outlined.Backup,
                    tint = CustomGray,
                    contentDescription = "",
                    modifier = Modifier
                        .size(20.dp)
                )
                Text(
                    color = CustomGray,
                    fontSize = 14.sp,
                    text = stringResource(id = R.string.back_up_chats),
                    modifier = Modifier.padding(start = 25.dp)
                )
            }
        }
    }

    when(state?.value) {
        ViewState.Loading -> {
            EAnimation(
                resource = R.raw.loading_animation,
                text = "Enviando...",
                animationModifier = Modifier
                    .size(250.dp),
                backgroundModifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .background(color = CustomWhite)
            )
        }
        is ViewState.Success -> {
            EAnimation(
                resource = R.raw.success_animation,
                iterations = 1,
                actionOnEnd = { viewModel.restartState() },
                animationModifier = Modifier
                    .size(250.dp),
                backgroundModifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .background(color = Color.Transparent)
            )
        }
        else -> Unit
    }

    if (showBottomSheet) {
        ModalBottomSheet(
            containerColor = CustomWhite,
            onDismissRequest = {
                showBottomSheet = false
            },
            sheetState = sheetState
        ) {
            Column(
                modifier = Modifier.padding(horizontal = 37.dp)
            ) {
                Text(fontSize = 12.sp, text = "Ingrese un nuevo nombre")
                ETextField(
                    TextFieldValues(
                        initialValue = supportName?.value ?: "",
                        label = stringResource(id = R.string.email),
                        icon = EIcon(icon = Icons.Default.Lock),
                        onValueChange = {
                            name = it.text
                        },
                        type = TextFieldType.NO_LABELED,
                        borderColor = BlackGreen,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 10.dp)
                    )
                )
                Row (
                    horizontalArrangement = Arrangement.End,
                    modifier = Modifier
                        .padding(bottom = 20.dp)
                        .fillMaxWidth()
                ) {
                    Button(
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Transparent
                        ),
                        onClick = {
                            viewModel?.updateName(name)
                            showBottomSheet = false
                        }
                    ) {
                        Text(
                            text = "Guardar",
                            fontSize = 14.sp,
                            color = DarkGreen
                        )
                    }
                }
            }
        }
    }
}


@Preview
@Composable
fun SupportConfigurationPreview() {
    SupportSettingsView(null, null)
}