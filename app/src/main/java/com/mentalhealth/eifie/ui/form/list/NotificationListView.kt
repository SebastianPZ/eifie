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
import com.mentalhealth.eifie.domain.entities.Notification
import com.mentalhealth.eifie.ui.theme.LightSkyGray

@Composable
fun NotificationListView(
    notifications: List<Notification>,
    onItemClick: (notification: Notification) -> Unit,
    modifier: Modifier = Modifier
) {

    LazyColumn(
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Top,
        modifier = modifier.fillMaxSize()
    ) {
        items(notifications.size) { index ->
            NotificationItem(
                notification = notifications[index],
                onClick = { form -> onItemClick(form) }
            )
            if(index < notifications.lastIndex)
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
    NotificationListView(
        notifications = listOf(
            Notification(
                title = "Formulario diario",
                content = ""
            ),
            Notification(
                title = "Formulario diario",
                content = ""
            )
        ),
        onItemClick = {}
    )
}
