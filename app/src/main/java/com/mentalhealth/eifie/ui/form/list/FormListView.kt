package com.mentalhealth.eifie.ui.form.list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mentalhealth.eifie.domain.entities.Form
import com.mentalhealth.eifie.ui.theme.LightSkyGray

@Composable
fun FormListView(
    formList: List<Form>,
    onItemClick: (form: Form) -> Unit,
    modifier: Modifier = Modifier
) {

    LazyColumn(
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Top,
        modifier = modifier.fillMaxSize()
    ) {
        items(formList.size) { index ->
            FormItemView(
                form = formList[index],
                onClick = { form -> onItemClick(form) }
            )
            if(index < formList.lastIndex)
                HorizontalDivider(
                    color = LightSkyGray,
                    modifier = Modifier
                        .padding(vertical = 18.dp, horizontal = 5.dp)
                        .fillMaxWidth()
                        .width(1.dp))
        }
    }

}

@Preview
@Composable
fun FormListPreview() {
    FormListView(
        formList = listOf(
            Form(
                id = 0,
                name = "Formulario diario",
                description = "",
                questions = "10",
                time = "3 - 5"
            ),
            Form(
                id = 0,
                name = "Formulario diario",
                description = "",
                questions = "10",
                time = "3 - 5"
            )
        ),
        onItemClick = {}
    )
}
