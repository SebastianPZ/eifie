package com.mentalhealth.eifie.ui.form.main

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mentalhealth.eifie.domain.entities.Survey
import com.mentalhealth.eifie.ui.theme.DarkGreen

@Composable
fun SurveyDataView(
    survey: Survey?
) {

    val questionsText = buildAnnotatedString {
        withStyle(style = SpanStyle(
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp
        )
        ) {
            append("${survey?.questions ?: "0"}")
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
            append("${survey?.minimumTime ?: 0} - ${survey?.maxTime ?: 0}")
        }
        withStyle(style = SpanStyle(
            color = DarkGreen,
            fontWeight = FontWeight.Bold,
            fontSize = 10.sp
        )
        ) {
            append(" min")
        }
    }

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = survey?.description ?: "Sin descripción",
            fontSize = 14.sp
        )
        Column(
            horizontalAlignment = Alignment.End,
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth().padding(top = 35.dp)
        ) {
            Text(text = questionsText)
            Text(
                text = timeText,
                modifier = Modifier.padding(top = 5.dp))
        }
    }
}

@Preview
@Composable
fun SurveyDataPreview() {
    SurveyDataView(null)
}