package com.example.mytodo.ui.element

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Create
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mytodo.ui.theme.MyToDoTheme

@Composable
fun ListBottomBar(
    onAddNewListButtonClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .height(50.dp),
        color = MaterialTheme.colorScheme.background,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .clickable { onAddNewListButtonClicked() }
                    .padding(start = 8.dp)
                    .weight(1f)
                    .height(50.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                        .size(30.dp)
                )
                Text(
                    text = "New list",
                    modifier = Modifier
                        .padding(start = 12.dp),
                    style = MaterialTheme.typography.titleMedium
                )
            }
            IconButton(onClick = { /*TODO*/ }) {
                Icon(
                    imageVector = Icons.Default.Create,
                    contentDescription = "Create Group",
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(30.dp),
                )
            }
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
fun ListBottomBarPreview() {
    MyToDoTheme {
        ListBottomBar(
            onAddNewListButtonClicked = { }
        )
    }
}