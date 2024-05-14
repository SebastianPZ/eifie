package com.mentalhealth.eifie.ui.form.list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mentalhealth.eifie.domain.entities.models.Form
import com.mentalhealth.eifie.ui.theme.DarkGreen
import com.mentalhealth.eifie.ui.theme.LightGreen

@Composable
fun FormItemView(
    form: Form,
    onClick: (form: Form) -> Unit
) {

    val questionsText = buildAnnotatedString {
        withStyle(style = SpanStyle(
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp
        )
        ) {
            append(form.questions)
        }
        withStyle(style = SpanStyle(
            fontWeight = FontWeight.Normal,
            fontSize = 12.sp
        )
        ) {
            append(" preguntas")
        }
    }

    val timeText = buildAnnotatedString {
        withStyle(style = SpanStyle(
            color = DarkGreen,
            fontWeight = FontWeight.Bold,
            fontSize = 12.sp
        )
        ) {
            append(form.time)
        }
        withStyle(style = SpanStyle(
            color = DarkGreen,
            fontWeight = FontWeight.Bold,
            fontSize = 10.sp
        )
        ) {
            append(" ${form.timeUnit}")
        }
    }


    Card(
        onClick = { onClick(form) },
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        Box(
            modifier = Modifier.background(
                brush = Brush.horizontalGradient(
                    colors = listOf(LightGreen, Color.White)
                )
            )
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(15.dp)
            ) {
                Text(text = form.name, fontWeight = FontWeight.Bold,)
                Column(
                    horizontalAlignment = Alignment.End,
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = questionsText)
                    Text(
                        text = timeText,
                        modifier = Modifier.padding(top = 5.dp))
                }
            }
        }
    }
}


@Composable
@Preview
fun FormItemPreview() {
    FormItemView(
        form = Form(
            id = 0,
            name = "Formulario diario",
            description = "",
            questions = "10",
            time = "3 - 5"
        ),
        onClick = {}
    )
}