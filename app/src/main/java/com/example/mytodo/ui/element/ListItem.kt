package com.example.mytodo.ui.element

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mytodo.data.COLORS
import com.example.mytodo.data.ICONS
import com.example.mytodo.ui.theme.MyToDoTheme

@Composable
fun ListItem(
    listIcon: String,
    listName: String,
    numberOfTask: Int,
    colorTheme: String,
    onListItemClicked: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onListItemClicked() }
            .height(45.dp),
        color = MaterialTheme.colorScheme.background
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(4.dp)
        ) {
            Icon(
                painter = painterResource(id = ICONS[listIcon]!!),
                contentDescription = null,
                tint = colorResource(id = COLORS[colorTheme]!!),
                modifier = Modifier.height(30.dp)
            )
            Text(
                text = listName,
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 8.dp)
            )
            Text(
                text = numberOfTask.toString(),
                modifier = Modifier
                    .padding(end = 8.dp)
            )
        }
    }
}

@Preview(
    name = "Light Mode",
)
@Preview(
    name = " Dark Mode",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
)
@Composable
fun ListItemPreview() {
    MyToDoTheme {
        ListItem(
            listIcon = "list",
            listName = "Tasks",
            numberOfTask = 2,
            colorTheme = "primary",
            onListItemClicked = {}
        )
    }
}