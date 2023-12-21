package com.example.mytodo.ui.element

import androidx.compose.foundation.background
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Create
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.example.mytodo.R

@Composable
fun ListDropdownMenu(
    expanded: Boolean,
    onExpandedChange: () -> Unit,
    onRenameListClicked: () -> Unit,
    onChangeThemeClicked: () -> Unit,
    onDeleteListClicked: () -> Unit
) {
    DropdownMenu(
        expanded = expanded,
        onDismissRequest = { onExpandedChange() },
        modifier = Modifier
            .background(MaterialTheme.colorScheme.surfaceVariant)
    ) {
        DropdownMenuItem(
            text = { Text(text = "Rename list") },
            onClick = {
                onRenameListClicked()
                onExpandedChange()
            },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Outlined.Create,
                    contentDescription = null,
                )
            }
        )
        DropdownMenuItem(
            text = { Text(text = "Change theme") },
            onClick = {
                onChangeThemeClicked()
                onExpandedChange()
            },
            leadingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_theme),
                    contentDescription = null,
                )
            }
        )
        DropdownMenuItem(
            text = { Text(text = "Delete list") },
            onClick = {
                onDeleteListClicked()
                onExpandedChange()
            },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Outlined.Delete,
                    contentDescription = null,
                )
            }
        )
    }
}