package kz.market.presentation.screens.settings

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import kz.market.domain.model.ThemeOptions
import kz.market.presentation.screens.settings.viewmodel.SettingsViewModel
import kz.market.ui.icons.MarketIcons
import kz.market.ui.preview.ThemedPreview
import kz.market.ui.theme.MarketTheme
import kz.market.updater.domain.model.UpdateStatus
import kz.market.updater.viewmodel.UpdateViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreenContent(
    themeOption: ThemeOptions = ThemeOptions.SYSTEM,
    updateStatus: UpdateStatus = UpdateStatus.Idle,
    onThemeChange: (ThemeOptions) -> Unit,
    onBackClick: () -> Unit,
    onUpdateClick: () -> Unit
) {
    var themeSelectorExpanded by remember { mutableStateOf(false) }
    val themeName = when (themeOption) {
        ThemeOptions.LIGHT -> "Светлая"
        ThemeOptions.DARK -> "Тёмная"
        ThemeOptions.SYSTEM -> "Системная"
    }

    val rotation by animateFloatAsState(
        targetValue = if (themeSelectorExpanded) 180f else 0f,
        animationSpec = tween(durationMillis = 300)
    )

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                navigationIcon = {
                    IconButton(
                        onClick = onBackClick
                    ) {
                        Icon(
                            painter = MarketIcons.Arrow_Left,
                            contentDescription = null
                        )
                    }
                },

                title = {
                    Text(
                        text = "Настройки",
                        style = MaterialTheme.typography.titleLarge
                    )
                }
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier.padding(innerPadding),
            contentPadding = PaddingValues(
                horizontal = 16.dp,
                vertical = 8.dp
            ),
            verticalArrangement = Arrangement.spacedBy(22.dp)
        ) {
            item {
                Text(
                    text = "Вид",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(start = 10.dp, bottom = 8.dp)
                )

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(MaterialTheme.shapes.medium)
                        .background(MaterialTheme.colorScheme.surfaceContainerLow)
                        .padding(horizontal = 15.dp, vertical = 10.dp),
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 7.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            "Тема приложения",
                            style = MaterialTheme.typography.bodyLarge,
                            fontWeight = FontWeight.SemiBold
                        )



                        Box {
                            Row(
                                modifier = Modifier
                                    .clip(MaterialTheme.shapes.medium)
                                    .clickable(
                                        onClick = {
                                            themeSelectorExpanded = !themeSelectorExpanded
                                        },
                                        indication = null,
                                        interactionSource = null
                                    ),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween,
                            ) {
                                Text(
                                    text = themeName,
                                    style = MaterialTheme.typography.bodyMedium,
                                    fontWeight = FontWeight.SemiBold,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                    modifier = Modifier.padding(end = 10.dp)
                                )

                                Icon(
                                    painter = MarketIcons.Chevron_Down,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                                    modifier = Modifier.rotate(rotation)
                                )
                            }

                            DropdownMenu(
                                expanded = themeSelectorExpanded,
                                onDismissRequest = { themeSelectorExpanded = false },
                                shape = MaterialTheme.shapes.medium,
                                modifier = Modifier
                                    .padding(
                                        horizontal = 10.dp
                                    )
                            ) {
                                DropdownMenuItem(
                                    modifier = Modifier
                                        .clip(MaterialTheme.shapes.medium),
                                    text = {
                                        Text(
                                            text = "Светлая",
                                            style = MaterialTheme.typography.bodyMedium,
                                            fontWeight = FontWeight.SemiBold
                                        )
                                    },
                                    onClick = {
                                        onThemeChange(ThemeOptions.LIGHT)
                                        themeSelectorExpanded = false
                                    }
                                )

                                DropdownMenuItem(
                                    modifier = Modifier
                                        .clip(MaterialTheme.shapes.medium),
                                    text = {
                                        Text(
                                            text = "Тёмная",
                                            style = MaterialTheme.typography.bodyMedium,
                                            fontWeight = FontWeight.SemiBold
                                        )
                                    },
                                    onClick = {
                                        onThemeChange(ThemeOptions.DARK)
                                        themeSelectorExpanded = false
                                    }
                                )

                                DropdownMenuItem(
                                    modifier = Modifier
                                        .clip(MaterialTheme.shapes.medium),
                                    text = {
                                        Text(
                                            text = "Системная",
                                            style = MaterialTheme.typography.bodyMedium,
                                            fontWeight = FontWeight.SemiBold
                                        )
                                    },
                                    onClick = {
                                        onThemeChange(ThemeOptions.SYSTEM)
                                        themeSelectorExpanded = false
                                    }
                                )
                            }
                        }
                    }
                }
            }

            item {
                Text(
                    text = "Система",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(start = 10.dp, bottom = 8.dp)
                )

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(MaterialTheme.shapes.medium)
                        .background(MaterialTheme.colorScheme.surfaceContainerLow)
                        .padding(horizontal = 15.dp, vertical = 10.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 7.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            "О приложении",
                            style = MaterialTheme.typography.bodyLarge,
                            fontWeight = FontWeight.SemiBold
                        )

                        Icon(
                            painter = MarketIcons.Chevron_Right,
                            contentDescription = null,
                        )
                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 7.dp)
                            .clickable {
                                onUpdateClick()
                            },
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            "Обновление приложения",
                            style = MaterialTheme.typography.bodyLarge,
                            fontWeight = FontWeight.SemiBold
                        )

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(10.dp)

                        ) {
                            Box(
                                modifier = Modifier
                                    .width(5.dp)
                                    .height(5.dp)
                                    .clip(CircleShape)
                                    .background(
                                        if (updateStatus is UpdateStatus.Available)
                                            Color.Green
                                        else
                                            Color.Transparent
                                    )
                            )

                            Icon(
                                painter = MarketIcons.Chevron_Right,
                                contentDescription = null,
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun SettingsScreen(
    onBackClick: () -> Unit,
    onUpdateClick: () -> Unit,
    settingsViewModel: SettingsViewModel = hiltViewModel(),
    updatesViewModel: UpdateViewModel = hiltViewModel()
) {
    val themeState by settingsViewModel.themeOption.collectAsState()
    val updateStatus by updatesViewModel.updateStatus.collectAsState()

    if (themeState != null) {
        SettingsScreenContent(
            themeOption = themeState!!,
            updateStatus = updateStatus,
            onThemeChange = settingsViewModel::setTheme,
            onBackClick = onBackClick,
            onUpdateClick = onUpdateClick
        )
    }
}

@ThemedPreview
@Composable
fun SettingsScreenPreview(modifier: Modifier = Modifier) {
    MarketTheme(themeOption = ThemeOptions.SYSTEM) {
        SettingsScreenContent(
            themeOption = ThemeOptions.SYSTEM,
            updateStatus = UpdateStatus.Idle,
            onThemeChange = {},
            onBackClick = {},
            onUpdateClick = {}
        )
    }
}

