package com.mentalhealth.eifie.ui.profile

import android.content.Context
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.mentalhealth.eifie.R
import com.mentalhealth.eifie.ui.theme.DarkGray
import com.mentalhealth.eifie.ui.theme.LightGray
import com.mentalhealth.eifie.ui.theme.Pink
import com.mentalhealth.eifie.ui.theme.Purple
import com.mentalhealth.eifie.ui.theme.WhitePink
import com.mentalhealth.eifie.util.getActivity

@Composable
fun ProfileDetail(
    context: Context = LocalContext.current,
    navController: NavHostController,
    viewModel: ProfileDetailViewModel = hiltViewModel<ProfileDetailViewModel>(),
    profileViewModel: ProfileViewModel = hiltViewModel<ProfileViewModel>(
        viewModelStoreOwner = context.getActivity()!!
    ),
) {

    val state by viewModel.state.collectAsStateWithLifecycle()
    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri ->
            uri?.let {
                viewModel.registerPostUpdate { profileViewModel.getUserInformation() }
                viewModel.updatePhotoResource(it)
            }
        }
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = WhitePink)
    ) {
        IconButton(
            onClick = {
                navController.previousBackStackEntry?.savedStateHandle?.set("changed", true)
                navController.popBackStack()
            },
            modifier = Modifier
                .align(Alignment.TopStart)
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
        ) {
            Spacer(modifier = Modifier.height(40.dp))
            Box(
                modifier = Modifier
                    .size(125.dp)
            ) {
                ProfilePhoto(state)
                IconButton(
                    onClick = { galleryLauncher.launch("image/*") },
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
            when(state) {
                is ProfileViewState.Success -> (state as ProfileViewState.Success).run {
                    LazyColumn(
                        modifier = Modifier
                            .wrapContentSize()
                            .padding(horizontal = 37.dp)
                    ){
                        itemsIndexed(profile.detailItems) { index, item ->
                            FieldValue(
                                icon = item.icon,
                                label = item.label,
                                value = item.value
                            )
                            if(index < profile.detailItems.lastIndex)
                                HorizontalDivider(modifier = Modifier
                                    .padding(top = 10.dp, bottom = 12.dp)
                                    .fillMaxWidth()
                                    .width(1.dp)
                                )
                        }
                    }
                }
                else -> Unit
            }
            Button(
                onClick = { viewModel.logoutUser(context.getActivity()!!) },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent
                ),
                shape = RoundedCornerShape(0),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 35.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.Bottom,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(bottom = 10.dp)
                ) {
                    Text(
                        color = Pink,
                        fontSize = 16.sp,
                        text = stringResource(id = R.string.logout_button)
                    )
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                        tint = Pink,
                        contentDescription = "",
                        modifier = Modifier
                            .size(25.dp)
                    )
                }
            }
        }
    }

    EditProfilePhoto(viewModel = viewModel)
}

@Composable
fun FieldValue(
    icon: Int,
    label: String,
    value: String
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        Text(
            text = label,
            fontSize = 14.sp,
            color = LightGray,
            modifier = Modifier
                .padding(top = 6.dp, bottom = 10.dp)
        )
        Text(
            text = value,
            fontSize = 14.sp,
            color = DarkGray
        )
    }
}