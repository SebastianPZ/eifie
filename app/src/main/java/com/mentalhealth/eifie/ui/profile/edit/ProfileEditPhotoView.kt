package com.mentalhealth.eifie.ui.profile.edit

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.mentalhealth.eifie.R
import com.mentalhealth.eifie.domain.entities.models.UserSession
import com.mentalhealth.eifie.ui.theme.Black
import com.mentalhealth.eifie.ui.theme.Purple

@Composable
fun EditProfilePhoto(
    user: UserSession,
    navController: NavHostController
) {

    val viewModel: ProfileEditPhotoViewModel = hiltViewModel<ProfileEditPhotoViewModel, ProfileEditPhotoViewModel.ProfileEditPhotoViewModelFactory>(
        creationCallback = { factory -> factory.create(user = user) })


    val state by viewModel.state.collectAsStateWithLifecycle()

    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri ->
            uri?.let {
                viewModel.updatePhotoResource(it)
            }
        }
    )

    LaunchedEffect(Unit) {
        galleryLauncher.launch("image/*") //Starts with open gallery
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Black
    ) {
        Column(
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 30.dp, top = 90.dp)
        ) {
            when(state) {
                is ProfileEditViewState.Editing -> (state as ProfileEditViewState.Editing).run {
                    Box(
                        modifier = Modifier
                            .size(250.dp)
                    ) {
                        Image(
                            painter = rememberAsyncImagePainter(model = photo),
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .fillMaxSize()
                                .border(
                                    width = 2.dp,
                                    color = Color.White,
                                    shape = CircleShape
                                )
                                .clip(CircleShape)
                        )
                        IconButton(
                            onClick = { galleryLauncher.launch("image/*") },
                            modifier = Modifier
                                .size(60.dp)
                                .align(Alignment.BottomEnd)
                        ) {
                            Icon(
                                imageVector = Icons.Filled.CameraAlt,
                                tint = Color.White,
                                contentDescription = "",
                                modifier = Modifier
                                    .size(30.dp)
                                    .drawBehind {
                                        drawCircle(
                                            color = Purple,
                                            radius = 200F
                                        )
                                    },
                            )
                        }
                    }
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 50.dp)
                    ) {
                        TextButton(onClick = { navController.popBackStack() }) {
                            Text(text = stringResource(id = R.string.cancel))
                        }
                        TextButton(onClick = { viewModel.saveUserPhoto(photo) {
                            navController.popBackStack()
                        } }) {
                            Text(text = stringResource(id = R.string.ready))
                        }
                    }
                }
                else -> Unit
            }
        }
    }
}

