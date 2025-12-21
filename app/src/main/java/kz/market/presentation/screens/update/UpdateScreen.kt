package kz.market.presentation.screens.update

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import kz.market.domain.model.ThemeOptions
import kz.market.ui.components.snackbar.MarketSnackBar
import kz.market.ui.components.snackbar.rememberMarketSnackBarHostState
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
    updateStatus: UpdateStatus,
    startDownload: (UpdateMetaData) -> Unit,
    installUpdate: (File, String) -> Unit,
    resetUpdateStatus: () -> Unit,
    snackBar: MarketSnackBar
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
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .animateContentSize()
                    .background(Color.DarkGray)
                    .padding(10.dp)
            ) {
                when (updateStatus) {
                    is UpdateStatus.Available -> {
                        Text(
                            text = "Доступно обновление.",
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                    is UpdateStatus.Error -> {

                    }
                    is UpdateStatus.Checking -> {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(20.dp),
                                color = MaterialTheme.colorScheme.primary,
                                strokeWidth = 2.dp,
                            )

                            Text(
                                text = "Проверка обновления...",
                                style = MaterialTheme.typography.bodyLarge
                            )
                        }
                    }
                    is UpdateStatus.NoAvailable -> {
                        Column {
                            Text(
                                text = "Нет доступного обновления.",
                                style = MaterialTheme.typography.bodyLarge
                            )

                            Button(
                                onClick = {
                                    throw RuntimeException("Test Crash") // Force a crash
                                }
                            ) {
                                Text(
                                    text = "Обновить",
                                    style = MaterialTheme.typography.bodyLarge
                                )
                            }
                        }
                    }
                    is UpdateStatus.Downloading -> {

                    }
                    is UpdateStatus.Installing -> {

                    }
                    is UpdateStatus.Downloaded -> {
                    }
                    is UpdateStatus.InstallPending -> {

                    }
                    is UpdateStatus.Installed -> {

                    }
                    is UpdateStatus.Idle -> {

                    }
                }
            }
        }
    }
}

@Composable
fun UpdateScreen(
    onBackClick: () -> Unit,
    snackBar: MarketSnackBar,
    updateViewModel: UpdateViewModel = hiltViewModel()
) {
    val updateStatus by updateViewModel.updateStatus.collectAsState()

    when (updateStatus) {
        is UpdateStatus.Available -> {

        }
        is UpdateStatus.Error -> {

        }
        is UpdateStatus.Checking -> {

        }
        is UpdateStatus.NoAvailable -> {

        }
        is UpdateStatus.Downloading -> {

        }
        is UpdateStatus.Installing -> {

        }
        is UpdateStatus.Downloaded -> {
        }
        is UpdateStatus.InstallPending -> {

        }
        is UpdateStatus.Installed -> {

        }
        is UpdateStatus.Idle -> {

        }
    }

    UpdateScreenContent(
        onBackClick = onBackClick,
        updateStatus = updateStatus,
        startDownload = updateViewModel::startDownload,
        installUpdate = updateViewModel::installUpdate,
        resetUpdateStatus = updateViewModel::resetUpdateStatus,
        snackBar = snackBar
    )
}


@ThemedPreview
@Composable
private fun UpdateScreenPreview() {
    MarketTheme(themeOption = ThemeOptions.SYSTEM) {
        UpdateScreenContent(
            onBackClick = {},
            updateStatus = UpdateStatus.Checking,
            startDownload = {},
            installUpdate = { _, _ -> },
            resetUpdateStatus = {},
            snackBar = MarketSnackBar(
                hostState = rememberMarketSnackBarHostState()
            )
        )
    }
}