package com.example.mytodo.ui.element

import android.content.res.Configuration
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mytodo.data.COLORS
import com.example.mytodo.ui.theme.MyToDoTheme

@Composable
fun TaskItem(
    title: String,
    colorTheme: String,
    isFinished: Boolean,
    onIsFinishedChange: (Boolean) -> Unit,
    isImportant: Boolean,
    onIsImportantChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onIsFinishedChange(!isFinished) },
        color = MaterialTheme.colorScheme.onSecondary,
        border = BorderStroke(
            width = 2.dp,
            color = if (isFinished)
                        colorResource(id = COLORS[colorTheme]!!)
                    else
                        MaterialTheme.colorScheme.outlineVariant
        ),
        shape = MaterialTheme.shapes.small
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = isFinished,
                onCheckedChange = { onIsFinishedChange(it) },
                colors = CheckboxDefaults.colors(
                    checkedColor = colorResource(id = COLORS[colorTheme]!!),
                    uncheckedColor = Color.LightGray
                )
            )
            Text(
                text = title,
                style = TextStyle(
                    textDecoration = if (isFinished) TextDecoration.LineThrough
                    else TextDecoration.None,
                    fontSize = 24.sp
                ),
                color = if (isFinished)
                    Color.Gray
                else
                    MaterialTheme.colorScheme.onSecondaryContainer,
                modifier = Modifier
                    .padding(start = 8.dp, bottom = 4.dp)
                    .weight(1f)
            )
            IconButton(onClick = { onIsImportantChange(!isImportant) }) {
                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = null,
                    tint = if (isImportant) colorResource(id = COLORS[colorTheme]!!) else Color.LightGray,
                    modifier = Modifier.size(30.dp).weight(1f),
                )
            }
        }
    }
}

@Preview(name = "Light Mode")
@Preview(name = " Dark Mode", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun TaskItemPreview() {
    MyToDoTheme {
        TaskItem(
            title = "Title",
            colorTheme = "primary",
            isFinished = true,
            onIsFinishedChange = { },
            isImportant = true,
            onIsImportantChange = { }
        )
    }
}