package com.mentalhealth.eifie.ui.register.role

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.mentalhealth.eifie.R
import com.mentalhealth.eifie.ui.common.button.AcceptButtonView
import com.mentalhealth.eifie.ui.common.button.CancelButtonView
import com.mentalhealth.eifie.ui.theme.BlackGreen
import com.mentalhealth.eifie.ui.theme.LightGreen
import com.mentalhealth.eifie.ui.theme.LightSkyGray
import com.mentalhealth.eifie.ui.theme.Purple

@Composable
fun RegisterRoleComponent(
    roles: List<RoleOption>,
    onRoleChange: (RoleOption) -> Unit,
    onBack: () -> Unit = {},
    onContinue: () -> Unit = {}
) {

    val instructionText = buildAnnotatedString {
        withStyle(style = SpanStyle(
            fontWeight = FontWeight.Normal
        )
        ) {
            append(stringResource(id = R.string.select_a))
        }
        withStyle(style = SpanStyle(
            color = Purple,
            fontWeight = FontWeight.Bold
        )
        ) {
            append(stringResource(id = R.string.option))
        }
        withStyle(style = SpanStyle(
            fontWeight = FontWeight.Normal
        )
        ) {
            append(stringResource(id = R.string.according_to))
        }
        withStyle(style = SpanStyle(
            color = Purple,
            fontWeight = FontWeight.Bold
        )
        ) {
            append(stringResource(id = R.string.role))
        }
    }


    return Column(
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 45.dp, horizontal = 25.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(
                modifier = Modifier.padding(bottom = 30.dp),
                text = instructionText,
                textAlign = TextAlign.Center
            )
            RoleSelector(
                roles = roles,
                onSelect = onRoleChange
            )
        }
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            CancelButtonView {
                onBack()
            }
            AcceptButtonView {
                onContinue()
            }
        }
    }
}

@Composable
fun RoleSelector(
    roles: List<RoleOption>,
    onSelect: (RoleOption) -> Unit
) {

    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(20.dp)
    ){
        items(roles) { role ->
            RoleOption(
                text = role.text,
                icon = role.icon,
                selected = role.selected,
                modifier = Modifier
                    .clickable {
                        onSelect(role)
                    }
            )
        }
    }
}


@Composable
fun RoleOption(
    text: String,
    icon: Int,
    selected: Boolean,
    @SuppressLint("ModifierParameter") modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        Card(
            colors = CardDefaults.cardColors(
                containerColor = if(selected) LightGreen else LightSkyGray,
            ),
            shape = RoundedCornerShape(15.dp),
            modifier = Modifier
                .wrapContentHeight()
        ) {
            Icon(
                painter = painterResource(id = icon),
                contentDescription = "Localized description",
                tint = Color.White,
                modifier = Modifier
                    .defaultMinSize(minWidth = 160.dp, minHeight = 300.dp)
                    .padding(vertical = 43.dp, horizontal = 21.dp)
            )
        }
        Text(
            modifier = Modifier.padding(top = 15.dp, bottom = 30.dp),
            text = text,
            textAlign = TextAlign.Center,
            fontWeight = if(selected) FontWeight.Bold else FontWeight.Normal
        )
    }
}