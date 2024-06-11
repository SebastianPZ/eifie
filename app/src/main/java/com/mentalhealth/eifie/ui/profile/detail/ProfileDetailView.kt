package com.mentalhealth.eifie.ui.profile.detail

import android.content.Context
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LifecycleResumeEffect
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.mentalhealth.eifie.R
import com.mentalhealth.eifie.ui.common.ViewState
import com.mentalhealth.eifie.ui.common.photo.UserPhotoView
import com.mentalhealth.eifie.ui.navigation.Router
import com.mentalhealth.eifie.ui.profile.edit.EditProfilePhoto
import com.mentalhealth.eifie.ui.profile.main.ProfileViewModel
import com.mentalhealth.eifie.ui.theme.CustomRed
import com.mentalhealth.eifie.ui.theme.CustomWhite
import com.mentalhealth.eifie.ui.theme.DarkGray
import com.mentalhealth.eifie.ui.theme.LightGray
import com.mentalhealth.eifie.ui.theme.LightSkyGray
import com.mentalhealth.eifie.ui.theme.Purple
import com.mentalhealth.eifie.ui.theme.WhitePink
import com.mentalhealth.eifie.util.getActivity

@Composable
fun ProfileDetail(
    context: Context = LocalContext.current,
    navController: NavHostController?,
    viewModel: ProfileDetailViewModel?
) {
    val state = viewModel?.state?.collectAsStateWithLifecycle()
    val profileData = viewModel?.profileData?.collectAsStateWithLifecycle()
    val userPhoto = viewModel?.userPhoto?.collectAsStateWithLifecycle()
    
    LaunchedEffect(Unit) {
        viewModel?.initUserInformation()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = CustomWhite)
    ) {
        IconButton(
            onClick = {
                navController?.run {
                    previousBackStackEntry?.savedStateHandle?.set("changed", true)
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
                    uri = userPhoto?.value?.photoUri,
                    username = userPhoto?.value?.username,
                    modifier = Modifier.fillMaxSize()
                )
                IconButton(
                    onClick = {
                        navController?.run {
                            currentBackStackEntry?.run {
                                savedStateHandle.set(
                                    key = "user",
                                    value = viewModel?.user
                                )
                                navigate(Router.PROFILE_EDIT_PHOTO.route)
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
            Spacer(modifier = Modifier.height(50.dp))
            LazyColumn(
                modifier = Modifier
                    .wrapContentSize()
                    .padding(horizontal = 37.dp)
            ) {
                when(state?.value) {
                    is ViewState.Success -> profileData?.value?.run {
                        itemsIndexed(data) { index, item ->
                            FieldValue(
                                icon = item.icon,
                                label = item.label,
                                value = item.value
                            )
                            if(index < data.lastIndex)
                                HorizontalDivider(
                                    color = LightSkyGray,
                                    modifier = Modifier
                                        .padding(top = 10.dp, bottom = 10.dp, start = 50.dp)
                                        .fillMaxWidth()
                                        .width(1.dp)
                                )
                        }

                        itemsIndexed(options) { index, item ->
                            HorizontalDivider(
                                color = LightSkyGray,
                                modifier = Modifier
                                    .padding(top = if (index > 0) 10.dp else 30.dp, bottom = 15.dp)
                                    .fillMaxWidth()
                                    .width(1.dp)
                            )
                            FieldOption(
                                icon = item.icon,
                                label = item.label,
                                onClick = {
                                    navController?.run {
                                        currentBackStackEntry?.savedStateHandle?.apply {
                                            set(key = "user", value = viewModel.user.uid)
                                            set(key = "psychologist", value = viewModel.user.psychologistAssigned)
                                        }

                                        navController.navigate(item.value)
                                    }
                                }
                            )
                        }
                    }
                    else -> Unit //Add loader if required
                }
            }
            Spacer(modifier = Modifier.weight(1f))
            Row(
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.Start,
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(horizontal = 37.dp)
                    .clickable { viewModel?.logoutUser(context.getActivity()!!) }
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.Logout,
                    tint = CustomRed,
                    contentDescription = "",
                    modifier = Modifier
                        .size(20.dp)
                )
                Text(
                    color = CustomRed,
                    fontSize = 14.sp,
                    text = stringResource(id = R.string.logout_button),
                    modifier = Modifier.padding(start = 32.dp)
                )
            }
        }
    }
}

@Composable
fun FieldValue(
    icon: Int,
    label: String,
    value: String
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start,
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        Icon(
            painter = painterResource(id = icon),
            contentDescription = "",
            tint = LightGray)
        Column(
            horizontalAlignment = Alignment.Start,
            modifier = Modifier.padding(start = 30.dp)
        ) {
            Text(
                text = label,
                fontSize = 12.sp,
                color = LightGray,
                modifier = Modifier
                    .padding(bottom = 3.dp)
            )
            Text(
                text = value,
                fontSize = 14.sp,
                color = DarkGray
            )
        }
    }
}

@Composable
fun FieldOption(
    icon: Int,
    label: String,
    onClick: () -> Unit = {}
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .clickable { onClick() }
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .wrapContentHeight()
        ) {
            Icon(
                painter = painterResource(id = icon),
                contentDescription = "",
                tint = LightGray)
            Text(
                text = label,
                fontSize = 14.sp,
                color = DarkGray,
                modifier = Modifier.padding(horizontal = 30.dp)
            )
        }
        Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowForwardIos,
            tint = LightSkyGray,
            contentDescription = ""
        )
    }
}

@Preview
@Composable
fun ProfileDetailPreview() {
    ProfileDetail(navController = null, viewModel = null)
}