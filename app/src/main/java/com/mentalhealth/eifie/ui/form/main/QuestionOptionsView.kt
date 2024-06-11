package com.mentalhealth.eifie.ui.form.main

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mentalhealth.eifie.domain.entities.QuestionOption
import com.mentalhealth.eifie.ui.theme.BlackGreen
import com.mentalhealth.eifie.ui.theme.LightGray
import com.mentalhealth.eifie.ui.theme.Purple

@Composable
fun QuestionOptionsView(
    options: List<QuestionOption>,
    onSelected: (Int) -> Unit = {}
) {

    val selectedValue = remember { mutableIntStateOf(-1) }
    val isSelectedOption: (QuestionOption) -> Boolean = { selectedValue.intValue == it.score }
    val onChangeState: (Int) -> Unit = {
        selectedValue.intValue = it
        onSelected(it)
    }

    Column(Modifier.padding(8.dp)) {
        options.forEach { option ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .selectable(
                        selected = isSelectedOption(option),
                        onClick = { onChangeState(option.score) },
                        role = Role.RadioButton
                    )
                    .padding(8.dp)
            ) {
                RadioButton(
                    selected = isSelectedOption(option),
                    colors = RadioButtonColors(
                        selectedColor = Purple,
                        unselectedColor = Color.Black,
                        disabledSelectedColor = LightGray,
                        disabledUnselectedColor = BlackGreen,
                    ),
                    onClick = null
                )
                Text(
                    text = option.text,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Normal,
                    modifier = Modifier.fillMaxWidth().padding(start = 10.dp)
                )
            }
        }
    }

}

@Preview
@Composable
fun QuestionOptionsPreview() {
    QuestionOptionsView(
        options = listOf(
            QuestionOption(-1, "No tengo ningún pensamiento de matarme."),
            QuestionOption(1, "He tenido pensamientos de matarme, pero no lo haría."),
            QuestionOption(2, "Querría matarme."),
            QuestionOption(3, "Me mataría si tuviera la oportunidad de hacerlo."),
        )
    )
}