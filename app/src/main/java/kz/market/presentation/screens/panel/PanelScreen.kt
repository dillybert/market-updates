package kz.market.presentation.screens.panel

import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kz.market.domain.model.ThemeOptions
import kz.market.ui.components.snackbar.MarketSnackBar
import kz.market.ui.components.snackbar.MarketSnackBarHostState
import kz.market.ui.components.snackbar.SnackBarType
import kz.market.ui.components.snackbar.rememberMarketSnackBarHostState
import kz.market.ui.icons.MarketIcons
import kz.market.ui.preview.ThemedPreview
import kz.market.ui.theme.MarketTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PanelScreen(
    modifier: Modifier = Modifier,
    snackBar: MarketSnackBar,
    onActionClick: () -> Unit = {},
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Панель",
                        style = MaterialTheme.typography.titleLarge
                    )
                },
                actions = {
                    IconButton(
                        onClick = onActionClick,
                    ) {
                        Icon(
                            MarketIcons.Gear,
                            contentDescription = null
                        )
                    }
                }
            )
        },
    ) { innerPadding ->
        FlowRow(
            modifier = Modifier.padding(innerPadding),
            maxItemsInEachRow = 2,
        ) {
            Button(
                onClick = {
                    snackBar.showSnackBar(
                        message = "Hello",
                        type = SnackBarType.Success
                    )
                }
            ) {
                Text("Success")
            }

            Button(
                onClick = {
                    snackBar.showSnackBar(
                        message = "Hello",
                        type = SnackBarType.Error
                    )
                }
            ) {
                Text("Error")
            }

            Button(
                onClick = {
                    snackBar.showSnackBar(
                        message = "Hello",
                        type = SnackBarType.Warning
                    )
                }
            ) {
                Text("Warning")
            }

            Button(
                onClick = {
                    snackBar.showSnackBar(
                        message = "Hello",
                        type = SnackBarType.Info,
                        actionLabel = "Repeat",
                        withDismissAction = true
                    )
                }
            ) {
                Text("Info")
            }
        }
    }
}



@ThemedPreview
@Composable
fun PanelScreenPreview(modifier: Modifier = Modifier) {
    MarketTheme(themeOption = ThemeOptions.SYSTEM) {
        PanelScreen(
            modifier = modifier,
            snackBar = MarketSnackBar(
                hostState = rememberMarketSnackBarHostState()
            )
        )
    }
}