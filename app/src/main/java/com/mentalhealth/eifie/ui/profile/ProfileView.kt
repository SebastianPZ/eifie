package com.mentalhealth.eifie.ui.profile

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.mentalhealth.eifie.R
import com.mentalhealth.eifie.ui.common.photo.UserPhotoView
import com.mentalhealth.eifie.ui.navigation.Router
import com.mentalhealth.eifie.ui.theme.Black10
import com.mentalhealth.eifie.ui.theme.BlackGreen
import com.mentalhealth.eifie.ui.theme.WhitePink
import com.mentalhealth.eifie.util.getActivity

@Composable
fun Profile(
    context: Context = LocalContext.current,
    mainNavController: NavHostController,
    viewModel: ProfileViewModel = hiltViewModel<ProfileViewModel>(
        viewModelStoreOwner = context.getActivity()!!
    )
) {

    val state by viewModel.state.collectAsStateWithLifecycle()
    
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .paint(
                painter = painterResource(id = R.drawable.eifi_background),
                contentScale = ContentScale.Crop
            )
    ) {
        IconButton(
            modifier = Modifier
                .align(Alignment.TopEnd),
            onClick = { mainNavController.navigate(Router.MAIN_PROFILE.route) }) {
            Icon(
                imageVector = Icons.Filled.Menu,
                tint = Color.White,
                contentDescription = "",
                modifier = Modifier
                    .size(30.dp)
            )
        }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth()
        ) {
            Spacer(modifier = Modifier.height(40.dp))
            when(state) {
                is ProfileViewState.Success -> (state as ProfileViewState.Success).run {
                    UserPhotoView(
                        uri = profile.uri,
                        username = profile.username,
                        modifier = Modifier.size(160.dp)
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = profile.username,
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )
                }
                else -> {
                    UserPhotoView(
                        modifier = Modifier.size(160.dp)
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = "Larry Lapiedra",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )
                }
            }
            Spacer(modifier = Modifier.height(30.dp))
            Box(
                contentAlignment = Alignment.TopCenter,
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .background(color = WhitePink)
            ) {
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .defaultMinSize(minHeight = 112.dp)
                        .offset(y = (-20).dp)
                        .padding(start = 24.dp, end = 24.dp)
                        .shadow(
                            elevation = 8.dp,
                            spotColor = Black10,
                            shape = RoundedCornerShape(15.dp)
                        )
                ) {
                    when(state) {
                        is ProfileViewState.Success -> (state as ProfileViewState.Success).run {
                            LazyRow(
                                horizontalArrangement = Arrangement.SpaceEvenly,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 25.dp)
                            ){
                                items(profile.items) { item ->
                                    DataItem(
                                        icon = item.icon,
                                        label = item.label,
                                        value = item.value
                                    )
                                }
                            }
                        }
                        else -> Unit
                    }
                }
            }
        }
    }


}


@Composable
fun DataItem(
    icon: Int,
    label: String,
    value: String
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            painter = painterResource(id = icon),
            contentDescription = label,
            tint = BlackGreen,
            modifier = Modifier.defaultMinSize(minWidth = 25.dp, minHeight = 25.dp)
        )
        Text(
            text = label,
            fontSize = 10.sp,
            color = BlackGreen,
            modifier = Modifier
                .padding(top = 6.dp, bottom = 10.dp)
        )
        Text(
            text = value,
            fontSize = 12.sp,
            color = Color.Black
        )
    }
}