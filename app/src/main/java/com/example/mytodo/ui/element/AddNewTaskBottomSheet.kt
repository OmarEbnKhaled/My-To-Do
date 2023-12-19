package com.example.mytodo.ui.element

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ExitToApp
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mytodo.data.COLORS
import com.example.mytodo.ui.theme.MyToDoTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddNewTaskBottomSheet(
    newTaskName: String,
    colorTheme: String,
    onNewTaskNameChange: (String) -> Unit,
    onCreateTaskClicked: () -> Unit,
    onCancel: () -> Unit,
) {
    ModalBottomSheet(
        onDismissRequest = { onCancel() },
        windowInsets = WindowInsets.ime,
        dragHandle = null,
        shape = BottomSheetDefaults.HiddenShape,
        modifier = Modifier
            .fillMaxWidth(),
        content = {
            AddNewTaskBottomSheetContent(
                newTaskName = newTaskName,
                colorTheme = colorTheme,
                onNewTaskNameChange = onNewTaskNameChange,
                onCreateTaskClicked = onCreateTaskClicked
            )
        }
    )
}

@Composable
private fun AddNewTaskBottomSheetContent(
    newTaskName: String,
    colorTheme: String,
    onNewTaskNameChange: (String) -> Unit,
    onCreateTaskClicked: () -> Unit,
) {
    val focusRequester = remember { FocusRequester() }
    LaunchedEffect(key1 = Unit) {
        focusRequester.requestFocus()
    }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .height(65.dp)
            .background(MaterialTheme.colorScheme.background)
    ) {
        BoxWithConstraints(
            modifier = Modifier
                .clipToBounds()
                .weight(1f)
        ) {
            TextField(
                value = newTaskName,
                onValueChange = { onNewTaskNameChange(it) },
                placeholder = {
                    Text(
                        text = "Add a task",
                        style = MaterialTheme.typography.titleLarge,
                        color = Color.Gray
                    )
                },
                textStyle = MaterialTheme.typography.headlineSmall,
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = MaterialTheme.colorScheme.background,
                    unfocusedContainerColor = MaterialTheme.colorScheme.background,
                    focusedIndicatorColor = MaterialTheme.colorScheme.background,
                    unfocusedIndicatorColor = MaterialTheme.colorScheme.background
                ),
                singleLine = true,
                modifier = Modifier
                    .requiredWidth(maxWidth + 16.dp)
                    .offset(x = (-8).dp)
                    .focusRequester(focusRequester)
                    .padding(start = 16.dp)
            )
        }
        IconButton(
            onClick = { onCreateTaskClicked() },
            enabled = newTaskName.isNotEmpty(),
            modifier = Modifier.padding(end = 16.dp)
        ) {
            Icon(
                imageVector = Icons.Rounded.ExitToApp,
                contentDescription = "Add a task button",
                tint = if (newTaskName.isNotEmpty())
                    colorResource(id = COLORS[colorTheme]!!)
                else Color.Gray,
                modifier = Modifier
                    .fillMaxHeight()
                    .width(35.dp)
                    .rotate(-90f)
            )
        }

    }
}


@Preview
@Composable
private fun AddNewTaskBottomSheetContentPreview() {
    MyToDoTheme {
        AddNewTaskBottomSheetContent(
            newTaskName = "c",
            colorTheme = "primary",
            onNewTaskNameChange = {},
            onCreateTaskClicked = {},
        )
    }
}