package com.mentalhealth.eifie.ui.register.psychologist

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mentalhealth.eifie.domain.entities.Psychologist
import com.mentalhealth.eifie.ui.common.button.AcceptButtonView
import com.mentalhealth.eifie.ui.common.button.CancelButtonView
import com.mentalhealth.eifie.ui.common.photo.UserPhotoView
import com.mentalhealth.eifie.ui.theme.Black
import com.mentalhealth.eifie.ui.theme.LightGreen
import com.mentalhealth.eifie.util.getUserName

@Composable
fun PsychologistValidationComponent(
    psychologist: Psychologist,
    onSuccess: () -> Unit,
    onCancel: () -> Unit
) {
    return Column(
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 45.dp, horizontal = 24.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            UserPhotoView(
                uri = psychologist.picture,
                username = getUserName(psychologist.firstName, psychologist.lastName),
                modifier = Modifier.size(160.dp)
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = psychologist.lastName,
                color = Black,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp
            )
            Text(
                text = psychologist.firstName,
                color = Black,
                fontWeight = FontWeight.Normal,
                fontSize = 14.sp
            )
            Spacer(modifier = Modifier.height(5.dp))
            Text(
                text = psychologist.hospital,
                color = LightGreen,
                fontWeight = FontWeight.Normal,
                fontSize = 14.sp
            )
        }
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            CancelButtonView {
                onCancel()
            }
            AcceptButtonView {
                onSuccess()
            }
        }
    }

}