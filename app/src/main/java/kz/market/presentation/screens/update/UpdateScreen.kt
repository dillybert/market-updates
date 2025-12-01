package kz.market.presentation.screens.update

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import kz.market.domain.model.ThemeOptions
import kz.market.ui.icons.MarketIcons
import kz.market.ui.preview.ThemedPreview
import kz.market.ui.theme.MarketTheme
import kz.market.updater.domain.model.UpdateMetaData
import kz.market.updater.domain.model.UpdateStatus
import kz.market.updater.viewmodel.UpdateViewModel
import java.io.File

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateScreenContent(
    onBackClick: () -> Unit,
    updateStatus: UpdateStatus = UpdateStatus.Idle,
    startDownload: (UpdateMetaData) -> Unit,
    installUpdate: (File, String) -> Unit,
    resetUpdateStatus: () -> Unit
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                navigationIcon = {
                    IconButton(
                        onClick = onBackClick
                    ) {
                        Icon(
                            MarketIcons.Arrow_Left,
                            contentDescription = null
                        )
                    }
                },
                title = {
                    Text(
                        text = "Обновление приложения",
                        style = MaterialTheme.typography.titleLarge
                    )
                }
            )
        }
    ) { innerPadding ->
        Text(
            text = "UpdateScreenContent",
            modifier = Modifier.padding(innerPadding)
        )
    }
}

@Composable
fun UpdateScreen(
    onBackClick: () -> Unit,
    updateViewModel: UpdateViewModel = hiltViewModel()
) {
    val updateStatus by updateViewModel.updateStatus.collectAsState()

    UpdateScreenContent(
        onBackClick = onBackClick,
        updateStatus = updateStatus,
        startDownload = updateViewModel::startDownload,
        installUpdate = updateViewModel::installUpdate,
        resetUpdateStatus = updateViewModel::resetUpdateStatus
    )
}


@ThemedPreview
@Composable
private fun UpdateScreenPreview() {
    MarketTheme(themeOption = ThemeOptions.SYSTEM) {
        UpdateScreenContent(
            onBackClick = {},
            updateStatus = UpdateStatus.Idle,
            startDownload = {},
            installUpdate = { _, _ -> },
            resetUpdateStatus = {}
        )
    }
}