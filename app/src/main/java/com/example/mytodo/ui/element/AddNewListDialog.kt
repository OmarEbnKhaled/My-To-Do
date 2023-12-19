package com.example.mytodo.ui.element

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.mytodo.R
import com.example.mytodo.data.COLORS
import com.example.mytodo.data.ICONS
import com.example.mytodo.ui.theme.MyToDoTheme

@Composable
fun AddNewListDialog(
    newListName: String,
    newListColorTheme: String,
    newListIcon: String,
    onNewListNameChange: (String) -> Unit,
    onNewListColorThemeChange: (String) -> Unit,
    onNewListIconClicked: (String) -> Unit,
    onCreateListClicked: () -> Unit,
    onCreateListCanceled: () -> Unit,
) {
    var showIconSelection by rememberSaveable { mutableStateOf(false) }

    Dialog(
        onDismissRequest = { onCreateListCanceled() },
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Card(
            shape = MaterialTheme.shapes.medium,
            modifier = Modifier
                .fillMaxWidth(0.9f),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(
                    text = "New List",
                    color = colorResource(id = COLORS[newListColorTheme]!!),
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(bottom = 4.dp)
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = { showIconSelection = !showIconSelection }) {
                        Icon(
                            painter = if (newListIcon == "") 
                                    painterResource(id = R.drawable.ic_add_icon_list)
                                else
                                    painterResource(id = ICONS[newListIcon]!!),
                            contentDescription = "Add icon list",
                            tint = colorResource(id = COLORS[newListColorTheme]!!),
                            modifier = Modifier
                                .size(35.dp)
                                .padding(end = 4.dp)
                        )
                    }
                    BoxWithConstraints(
                        modifier = Modifier
                            .clipToBounds()
                    ) {
                        TextField(
                            value = newListName,
                            onValueChange = { onNewListNameChange(it) },
                            placeholder = {
                                Text(
                                    text = "Enter list title",
                                    style = MaterialTheme.typography.titleLarge,
                                    color = Color.Gray
                                )
                            },
                            textStyle = MaterialTheme.typography.headlineSmall,
                            colors = TextFieldDefaults.colors(
                                focusedContainerColor = MaterialTheme.colorScheme.background,
                                unfocusedContainerColor = MaterialTheme.colorScheme.background,
                                focusedIndicatorColor = colorResource(id = COLORS[newListColorTheme]!!),
                                unfocusedIndicatorColor = colorResource(id = COLORS[newListColorTheme]!!),
                            ),
                            singleLine = true,
                            modifier = Modifier
                                .requiredWidth(maxWidth + 16.dp)
                                .offset(x = (-8).dp)
                        )
                    }
                }
                if (showIconSelection) {
                    LazyIconPlate(
                        onNewListIconClicked = {
                            onNewListIconClicked(it)
                            showIconSelection = !showIconSelection
                        }
                    )
                    Row(
                        horizontalArrangement = Arrangement.End,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        TextButton(
                            onClick = {
                                showIconSelection = !showIconSelection
                                onNewListIconClicked("")
                            }
                        ) {
                            Text(
                                text = "Remove Emoji",
                                color = colorResource(id = COLORS[newListColorTheme]!!),
                                style = MaterialTheme.typography.titleMedium
                            )
                        }
                    }
                }else {
                    LazyColorPlate(onListColorThemeChange = onNewListColorThemeChange)
                    Row(
                        horizontalArrangement = Arrangement.End,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        TextButton(
                            onClick = { onCreateListCanceled() }
                        ) {
                            Text(
                                text = "Cancel",
                                color = colorResource(id = COLORS[newListColorTheme]!!),
                                style = MaterialTheme.typography.titleMedium
                            )
                        }
                        TextButton(
                            onClick = { onCreateListClicked() },
                            enabled = newListName.isNotEmpty(),
                            colors = ButtonDefaults.textButtonColors(contentColor = colorResource(id = COLORS[newListColorTheme]!!))
                        ) {
                            Text(
                                text = "Create List",
                                style = MaterialTheme.typography.titleMedium
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun LazyIconPlate(
    onNewListIconClicked: (String) -> Unit,
    iconList: HashMap<String, Int> = ICONS
) {
    LazyRow(
        contentPadding = PaddingValues(horizontal = 4.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 20.dp, bottom = 12.dp)
            .height(30.dp)
    ) {
        items(iconList.toList()) { icon ->
            IconButton(
                onClick = { onNewListIconClicked(icon.first) },
            ) {
                Icon(
                    painter = painterResource(id = icon.second),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier
                        .size(30.dp)
                )
            }
        }
    }
}

@Composable
fun LazyColorPlate(
    onListColorThemeChange: (String) -> Unit,
    colorList: HashMap<String, Int> = COLORS
) {
    LazyRow(
        contentPadding = PaddingValues(horizontal = 4.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 20.dp, bottom = 12.dp)
            .height(30.dp)
    ) {
        items(colorList.toList()) {color ->
            Box(
                modifier = Modifier.padding(horizontal = 6.dp)
            ) {
                Button(
                    onClick = { onListColorThemeChange(color.first) },
                    colors = ButtonDefaults
                        .buttonColors(containerColor = colorResource(id = color.second)),
                    shape = CircleShape,
                    content = {},
                    modifier = Modifier
                        .size(30.dp)
                )
            }
        }
    }
}

@Preview
@Composable
private fun AddNewListDialogPreview() {
    MyToDoTheme {
        AddNewListDialog(
            newListName = "",
            newListColorTheme = "primary",
            newListIcon = "",
            onNewListNameChange = { _ -> },
            onNewListColorThemeChange = { _ -> },
            onNewListIconClicked = {},
            onCreateListCanceled = { /*TODO*/ },
            onCreateListClicked = {}
        )
    }
}