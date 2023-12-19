package com.example.mytodo.ui.element

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mytodo.ui.theme.MyToDoTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChangeThemeBottomSheet(
    onListColorThemeChange: (String) -> Unit,
    onDismiss: () -> Unit
) {
    ModalBottomSheet(
        onDismissRequest = { onDismiss() },
        windowInsets = WindowInsets.ime,
        shape = BottomSheetDefaults.ExpandedShape,
        modifier = Modifier
            .fillMaxWidth(),
        content = {
            ChangeThemeBottomSheetContent(
                onListColorThemeChange = onListColorThemeChange
            )
        }
    )
}

@Composable
private fun ChangeThemeBottomSheetContent(
    onListColorThemeChange: (String) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp)
            .padding(bottom = 75.dp)
    ) {
        Text(
            text = "Pick a theme",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(bottom = 4.dp)
        )
        LazyColorPlate(onListColorThemeChange = onListColorThemeChange )
    }
}

@Preview
@Composable
private fun ChangeThemeBottomSheetContentPreview() {
    MyToDoTheme {
        ChangeThemeBottomSheetContent(
            onListColorThemeChange = {}
        )
    }
}