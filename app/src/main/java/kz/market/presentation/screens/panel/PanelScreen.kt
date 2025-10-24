package kz.market.presentation.screens.panel

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import kz.market.ui.components.snackbar.MarketSnackBar
import kz.market.ui.components.snackbar.MarketSnackBarHostState
import kz.market.ui.components.snackbar.rememberMarketSnackBarHostState
import kz.market.ui.icons.MarketIcons
import kz.market.ui.preview.ThemedPreview

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
        Text(
            modifier = Modifier.padding(innerPadding),
            text = "Panel"
        )
    }
}



@ThemedPreview
@Composable
fun PanelScreenPreview(modifier: Modifier = Modifier) {
    PanelScreen(
        modifier = modifier,
        snackBar = MarketSnackBar(
            hostState = rememberMarketSnackBarHostState()
        )
    )
}