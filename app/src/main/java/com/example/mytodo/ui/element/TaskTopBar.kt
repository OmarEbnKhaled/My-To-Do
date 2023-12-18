package com.example.mytodo.ui.element

import android.content.res.Configuration
import androidx.annotation.ColorRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import com.example.mytodo.R
import com.example.mytodo.data.ICONS
import com.example.mytodo.ui.theme.MyToDoTheme

@Composable
fun TaskTopBar(
    listName: String,
    listIcon: String,
    @ColorRes colorTheme: Int,
    onBackupButtonClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        color = MaterialTheme.colorScheme.background,
    ) {
        Column(
            modifier = Modifier.height(110.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween,
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .height(50.dp)
                    .fillMaxWidth()
            ) {
                IconButton(onClick = { onBackupButtonClicked() }) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back",
                        tint = colorResource(id = colorTheme),
                        modifier = Modifier.size(30.dp)
                    )
                }
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(
                        imageVector = Icons.Default.MoreVert,
                        contentDescription = "Back",
                        tint = colorResource(id = colorTheme),
                        modifier = Modifier.size(30.dp)
                    )
                }
            }
            Row(
                modifier = Modifier.padding(start = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    painter = painterResource(id = ICONS[listIcon]!!),
                    contentDescription = null,
                    tint = colorResource(id = colorTheme),
                    modifier = Modifier.size(40.dp)
                )
                Text(
                    text = listName,
                    style = MaterialTheme.typography.headlineLarge,
                    modifier = Modifier
                        .padding(start = 12.dp)
                        .weight(1f)
                )
            }
            Spacer(modifier = Modifier
                .fillMaxWidth(0.95f)
                .height(1.dp)
                .background(colorResource(id = colorTheme))
            )
        }
    }
}

@Preview(name = "Light Mode")
@Preview(
    name = " Dark Mode",
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun TaskTopBarPreview() {
    MyToDoTheme {
        TaskTopBar(
            listName = "Home",
            listIcon = "list",
            colorTheme = R.color.primary,
            onBackupButtonClicked = { }
        )
    }
}